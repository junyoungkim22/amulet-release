package com.oracle.truffle.vec.utils;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.parser.generated.VecLexer;
import com.oracle.truffle.parser.generated.VecParser;
import com.oracle.truffle.vec.execution.LoopExecutionInfo;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import com.oracle.truffle.vec.nodes.controlflow.VecBlockNode;
import com.oracle.truffle.vec.nodes.controlflow.VecExecutionPlanNode;
import com.oracle.truffle.vec.nodes.controlflow.VecWhileNode;
import com.oracle.truffle.vec.nodes.expression.VecAddNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecBitwiseAndNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecGreaterThanNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecGreaterThanOrEqualNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecIntLiteralNode;
import com.oracle.truffle.vec.nodes.expression.VecLessThanNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecLessThanOrEqualNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecMaxNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecMinNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecXORNodeGen;
import com.oracle.truffle.vec.nodes.local.VecReadIntArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecReadLocalVariableNodeGen;
import com.oracle.truffle.vec.nodes.local.VecWriteIntArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecWriteLocalVariableNode;
import com.oracle.truffle.vec.nodes.local.VecWriteLocalVariableNodeGen;
import com.oracle.truffle.vec.nodes.simd.VecGotoKernel8x8Node;
import com.oracle.truffle.vec.parser.IfStatementInfo;
import com.oracle.truffle.vec.parser.ParseResult;
import com.oracle.truffle.vec.parser.gotoParser.GotoStmtReader;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Utils {
  static {
    System.loadLibrary("Utils");
  }

  public static native void nativePackMatrices(
      double[][] a,
      double[][] b,
      int kc,
      int mLength,
      int kLength,
      int nLength,
      int kernelHeight,
      int kernelWidth,
      int aAlignmentOffset,
      int bAlignmentOffset,
      double[] packedA,
      double[] packedB,
      int matrixForm);

  public static VecStatementNode createPlanNode(
      ParseResult parseResult,
      String planType,
      FrameDescriptor fd,
      LoopExecutionInfo loopExecutionInfo,
      int[] direction,
      int[] blockDimensions) {
    VecStatementNode[] loopBodyStatements =
        new VecStatementNode[parseResult.loopBodyStatements.size()];
    for (int i = 0; i < parseResult.loopBodyStatements.size(); i++) {
      Object statement = parseResult.loopBodyStatements.get(i);
      if (statement instanceof IfStatementInfo) {
        IfStatementInfo ifStatementInfo = (IfStatementInfo) statement;
        VecExecutionPlanNode ifStmt =
            createExecutionPlan(ifStatementInfo.conditions, ifStatementInfo.ifBody, planType, fd);
        loopBodyStatements[i] = ifStmt;
      } else {
        VecStatementNode loopBodyStatement = (VecStatementNode) statement;
        loopBodyStatements[i] = loopBodyStatement;
      }
    }
    VecStatementNode ret = new VecBlockNode(loopBodyStatements);
    final int nestDepth = parseResult.loopVariableStrs.size();
    int[] incrementSizes = loopExecutionInfo.incrementSizes;
    for (int i = nestDepth - 1; i >= 0; i--) {
      int loopIndex = direction[i];
      VecExpressionNode initExpr = null;
      VecExpressionNode compExpr = null;
      int incrementSize = 0;
      if (blockDimensions == null) {
        initExpr =
            (VecExpressionNode)
                VecReadLocalVariableNodeGen.create(
                    fd.findOrAddFrameSlot(
                        "PHASE_START" + Integer.toString(loopIndex), FrameSlotKind.Int));
        compExpr =
            (VecExpressionNode)
                VecReadLocalVariableNodeGen.create(
                    fd.findOrAddFrameSlot(
                        "PHASE_END" + Integer.toString(loopIndex), FrameSlotKind.Int));
        incrementSize = incrementSizes[loopIndex];
        ret =
            createWhileNode(
                parseResult,
                loopIndex,
                fd,
                ret,
                parseResult.loopVariableStrs.get(loopIndex),
                initExpr,
                compExpr,
                incrementSize,
                true);
      } else {
        initExpr =
            (VecExpressionNode)
                VecReadLocalVariableNodeGen.create(
                    fd.findOrAddFrameSlot(
                        parseResult.loopVariableStrs.get(loopIndex) + "_BLOCK", FrameSlotKind.Int));
        incrementSize = incrementSizes[loopIndex];
        VecExpressionNode loopEndNode =
            new VecIntLiteralNode(loopExecutionInfo.loopEnds[loopIndex]);
        if (incrementSize > 0) {
          compExpr =
              VecMinNodeGen.create(
                  VecAddNodeGen.create(
                      initExpr, new VecIntLiteralNode(incrementSize * blockDimensions[loopIndex])),
                  loopEndNode);
        } else {
          compExpr =
              VecMaxNodeGen.create(
                  VecAddNodeGen.create(
                      initExpr, new VecIntLiteralNode(incrementSize * blockDimensions[loopIndex])),
                  loopEndNode);
        }
        ret =
            createWhileNode(
                parseResult,
                loopIndex,
                fd,
                ret,
                parseResult.loopVariableStrs.get(loopIndex),
                initExpr,
                compExpr,
                incrementSize,
                false);
      }
      // ret = createWhileNode(parseResult, loopIndex, fd, ret,
      // parseResult.loopVariableStrs.get(loopIndex), initExpr, compExpr, incrementSize);
    }

    if (blockDimensions != null) {
      for (int i = nestDepth - 1; i >= 0; i--) {
        int loopIndex = direction[i];
        VecExpressionNode initExpr =
            (VecExpressionNode)
                VecReadLocalVariableNodeGen.create(
                    fd.findOrAddFrameSlot(
                        "PHASE_START" + Integer.toString(loopIndex), FrameSlotKind.Int));
        VecExpressionNode compExpr =
            (VecExpressionNode)
                VecReadLocalVariableNodeGen.create(
                    fd.findOrAddFrameSlot(
                        "PHASE_END" + Integer.toString(loopIndex), FrameSlotKind.Int));
        int incrementSize = incrementSizes[loopIndex] * blockDimensions[loopIndex];
        // FrameSlot loopVarFrameSlot =
        // fd.findOrAddFrameSlot(parseResult.loopVariableStrs.get(loopIndex) + "_BLOCK",
        // FrameSlotKind.Int);
        ret =
            createWhileNode(
                parseResult,
                loopIndex,
                fd,
                ret,
                parseResult.loopVariableStrs.get(loopIndex) + "_BLOCK",
                initExpr,
                compExpr,
                incrementSize,
                true);
      }
    }
    return ret;
  }

  public static VecStatementNode createWhileNode(
      ParseResult parseResult,
      int loopIndex,
      FrameDescriptor fd,
      VecStatementNode loopBody,
      String frameSlotName,
      VecExpressionNode initExpr,
      VecExpressionNode compExpr,
      int incrementSize,
      Boolean setLoopVarName) {
    FrameSlot loopVarFrameSlot = fd.findOrAddFrameSlot(frameSlotName, FrameSlotKind.Int);
    VecExpressionNode readLoopVarNode =
        (VecExpressionNode) VecReadLocalVariableNodeGen.create(loopVarFrameSlot);
    VecExpressionNode loopCondition =
        Utils.createLoopCondition(
            readLoopVarNode, compExpr, parseResult.compareOpStrings.get(loopIndex));

    VecStatementNode initStatement =
        VecWriteLocalVariableNodeGen.create(initExpr, loopVarFrameSlot);
    VecStatementNode updateStatement =
        VecWriteLocalVariableNodeGen.create(
            VecAddNodeGen.create(readLoopVarNode, new VecIntLiteralNode(incrementSize)),
            loopVarFrameSlot);
    VecStatementNode newLoopBody = mergeStatements(loopBody, updateStatement);
    VecWhileNode loopNode = new VecWhileNode(loopCondition, newLoopBody);
    if (setLoopVarName) {
      loopNode.loopVarName = frameSlotName;
    } else {
      loopNode.loopVarName = null;
    }
    return mergeStatements(initStatement, loopNode);
  }

  public static VecStatementNode createGotoPlanNode(
      ParseResult parseResult,
      FrameDescriptor fd,
      LoopExecutionInfo loopExecutionInfo,
      HashMap<String, Object> varMap) {
    // Todo support Goto operations for more than 1 statement!
    VecStatementNode[] loopBodyStatements = new VecStatementNode[1];
    String stmtString = parseResult.loopBodyStatementStrings.get(0);
    GotoStmtReader reader = stringToGotoOpCode(stmtString, varMap);

    // Check if computation statement is a matrix multiplication like task
    if (reader.aArr == null || reader.bArr == null || reader.cArr == null) {
      return null;
    }
    // Check if there are three nested loops, with the iteration variables used to index matrices
    if (parseResult.loopVariableStrs.size() != 3) {
      return null;
    }
    if (parseResult.loopVariableStrs.indexOf(reader.iString) == -1) {
      return null;
    }
    if (parseResult.loopVariableStrs.indexOf(reader.kString) == -1) {
      return null;
    }
    if (parseResult.loopVariableStrs.indexOf(reader.jString) == -1) {
      return null;
    }

    VecExpressionNode kPanelSizeExpr =
        (VecExpressionNode)
            VecReadLocalVariableNodeGen.create(
                fd.findOrAddFrameSlot("K_PANEL_SIZE", FrameSlotKind.Int));
    // VecExpressionNode kPanelSizeExpr = (VecExpressionNode)
    // VecReadLocalVariableNodeGen.create(fd.findOrAddFrameSlot("INCR_SIZE0", FrameSlotKind.Int));
    VecExpressionNode iExpr =
        (VecExpressionNode)
            VecReadLocalVariableNodeGen.create(
                fd.findOrAddFrameSlot(reader.iString, FrameSlotKind.Int));
    VecExpressionNode kExpr =
        (VecExpressionNode)
            VecReadLocalVariableNodeGen.create(
                fd.findOrAddFrameSlot(reader.kString, FrameSlotKind.Int));
    VecExpressionNode jExpr =
        (VecExpressionNode)
            VecReadLocalVariableNodeGen.create(
                fd.findOrAddFrameSlot(reader.jString, FrameSlotKind.Int));
    String jjString = reader.jString + "_2";
    VecExpressionNode jjExpr =
        (VecExpressionNode)
            VecReadLocalVariableNodeGen.create(fd.findOrAddFrameSlot(jjString, FrameSlotKind.Int));
    VecStatementNode gotoNode =
        new VecGotoKernel8x8Node(
            reader, kPanelSizeExpr, iExpr, kExpr, VecAddNodeGen.create(jExpr, jjExpr));
    loopBodyStatements[0] = gotoNode;

    String[] loopVarStrings = new String[4];
    loopVarStrings[0] = reader.kString;
    loopVarStrings[1] = reader.jString;
    loopVarStrings[2] = reader.iString;
    loopVarStrings[3] = jjString;

    VecStatementNode ret = loopBodyStatements[0];

    for (int i = 3; i >= 0; i--) {
      VecExpressionNode initExpr =
          (VecExpressionNode)
              VecReadLocalVariableNodeGen.create(
                  fd.findOrAddFrameSlot("PHASE_START" + Integer.toString(i), FrameSlotKind.Int));
      VecExpressionNode compExpr =
          (VecExpressionNode)
              VecReadLocalVariableNodeGen.create(
                  fd.findOrAddFrameSlot("PHASE_END" + Integer.toString(i), FrameSlotKind.Int));
      VecExpressionNode incrExpr =
          (VecExpressionNode)
              VecReadLocalVariableNodeGen.create(
                  fd.findOrAddFrameSlot("INCR_SIZE" + Integer.toString(i), FrameSlotKind.Int));
      FrameSlot loopVarFrameSlot = fd.findOrAddFrameSlot(loopVarStrings[i], FrameSlotKind.Int);
      VecExpressionNode readLoopVarNode =
          (VecExpressionNode) VecReadLocalVariableNodeGen.create(loopVarFrameSlot);
      VecExpressionNode loopCondition = Utils.createLoopCondition(readLoopVarNode, compExpr, "<");
      VecStatementNode initStatement =
          VecWriteLocalVariableNodeGen.create(initExpr, loopVarFrameSlot);
      VecStatementNode updateStatement =
          VecWriteLocalVariableNodeGen.create(
              VecAddNodeGen.create(readLoopVarNode, incrExpr), loopVarFrameSlot);
      ret = mergeStatements(ret, updateStatement);
      VecWhileNode loopNode = new VecWhileNode(loopCondition, ret);
      // loopNode.loopVarName = loopVarStrings[i];
      ret = mergeStatements(initStatement, loopNode);
    }
    return ret;
  }

  public static GotoStmtReader stringToGotoOpCode(String str, Map<String, Object> arrMap) {
    CharStream strCharStream = CharStreams.fromString(str + "\n");
    VecLexer lexer = new VecLexer(strCharStream);
    VecParser parser = new VecParser(new CommonTokenStream(lexer));

    ParseTree tree = parser.parse();
    GotoStmtReader reader = new GotoStmtReader(arrMap);
    reader.visit(tree);
    return reader;
  }

  public static void writeToStack(
      HashMap<String, Object> map, HashMap<String, FrameSlot> locals, VirtualFrame frame) {
    for (Map.Entry<String, Object> set : map.entrySet()) {
      FrameSlot frameSlot =
          frame.getFrameDescriptor().findOrAddFrameSlot(set.getKey(), FrameSlotKind.Int);
      frame.getFrameDescriptor().setFrameSlotKind(frameSlot, FrameSlotKind.Int);
      writeToFrame(set.getValue(), frame, frameSlot);
    }
  }

  public static void writeToFrame(Object value, VirtualFrame frame, FrameSlot slot) {
    if (value instanceof Integer) {
      frame.getFrameDescriptor().setFrameSlotKind(slot, FrameSlotKind.Int);
      frame.setInt(slot, (Integer) value);
      return;
    }
    if (value instanceof Double) {
      frame.getFrameDescriptor().setFrameSlotKind(slot, FrameSlotKind.Double);
      frame.setDouble(slot, (Double) value);
      return;
    }
  }

  public static VecExecutionPlanNode createExecutionPlan(
      VecExpressionNode[] conditions,
      VecStatementNode body,
      String mode,
      FrameDescriptor frameDescriptor) {
    if (mode.equals("NO_BRANCH")) {
      FrameSlot maskFrameSlot =
          frameDescriptor.findOrAddFrameSlot("NOBRANCH_MASK", FrameSlotKind.Int);
      VecStatementNode noBranchIfBody = convertToNoBranchStatement(body, maskFrameSlot);
      return new VecExecutionPlanNode(conditions, noBranchIfBody, mode, maskFrameSlot);
    }
    return new VecExecutionPlanNode(conditions, body, mode, null);
  }

  public static VecStatementNode convertToNoBranchStatement(
      VecStatementNode stmt, FrameSlot maskFrameSlot) {
    if (stmt instanceof VecWriteLocalVariableNode) {
      VecWriteLocalVariableNode writeNode = (VecWriteLocalVariableNode) stmt;
      FrameSlot varFrameSlot = writeNode.getSlot();
      VecExpressionNode originalValueNode = VecReadLocalVariableNodeGen.create(varFrameSlot);
      VecExpressionNode exprNode = writeNode.getValueNode();
      VecExpressionNode originalValueXORExprNode =
          VecXORNodeGen.create(originalValueNode, exprNode);

      VecExpressionNode maskNode = VecReadLocalVariableNodeGen.create(maskFrameSlot);
      VecExpressionNode applyNode = VecBitwiseAndNodeGen.create(maskNode, originalValueXORExprNode);
      VecExpressionNode finalExprNode = VecXORNodeGen.create(originalValueNode, applyNode);
      VecWriteLocalVariableNode ret =
          VecWriteLocalVariableNodeGen.create(finalExprNode, varFrameSlot);
      return ret;
    } else if (stmt instanceof VecWriteIntArrayVariableNode) {
      VecWriteIntArrayVariableNode writeNode = (VecWriteIntArrayVariableNode) stmt;
      Object arr = writeNode.getArr();
      VecExpressionNode[] indexNodes = writeNode.getIndexNodes();
      VecExpressionNode originalValueNode = new VecReadIntArrayVariableNode(indexNodes, arr);
      VecExpressionNode exprNode = writeNode.getValueNode();
      VecExpressionNode originalValueXORExprNode =
          VecXORNodeGen.create(originalValueNode, exprNode);

      VecExpressionNode maskNode = VecReadLocalVariableNodeGen.create(maskFrameSlot);
      VecExpressionNode applyNode = VecBitwiseAndNodeGen.create(maskNode, originalValueXORExprNode);
      VecExpressionNode finalExprNode = VecXORNodeGen.create(originalValueNode, applyNode);
      VecWriteIntArrayVariableNode ret =
          new VecWriteIntArrayVariableNode(indexNodes, finalExprNode, arr);
      return ret;
    } else if (stmt instanceof VecBlockNode) {
      VecBlockNode blockNode = (VecBlockNode) stmt;
      VecStatementNode[] stmts = blockNode.getBlockNode().getElements();
      VecStatementNode[] newStmts = new VecStatementNode[stmts.length];
      for (int i = 0; i < stmts.length; i++) {
        newStmts[i] = convertToNoBranchStatement(stmts[i], maskFrameSlot);
      }
      return new VecBlockNode(newStmts);
    }
    System.out.println("No branch version of statement not supported yet!");
    return null;
  }

  public static VecStatementNode mergeStatements(
      VecStatementNode statement0, VecStatementNode statement1) {
    VecStatementNode[] stmtArr = new VecStatementNode[2];
    stmtArr[0] = statement0;
    stmtArr[1] = statement1;
    return new VecBlockNode(stmtArr);
  }

  public static VecExpressionNode createLoopCondition(
      VecExpressionNode lhs, VecExpressionNode rhs, String binopStr) {
    CompilerAsserts.compilationConstant(binopStr);

    switch (binopStr) {
      case "<":
        return VecLessThanNodeGen.create(lhs, rhs);
      case ">":
        return VecGreaterThanNodeGen.create(lhs, rhs);
      case "<=":
        return VecLessThanOrEqualNodeGen.create(lhs, rhs);
      case ">=":
        return VecGreaterThanOrEqualNodeGen.create(lhs, rhs);
      default:
        System.out.println("Invalid operator for comparison");
        return null;
    }
  }

  public static boolean evaluateLoopCondition(int i, int end, String binopStr) {
    CompilerAsserts.compilationConstant(binopStr);

    switch (binopStr) {
      case "<":
        return i < end;
      case ">":
        return i > end;
      case "<=":
        return i <= end;
      case ">=":
        return i >= end;
      default:
        System.out.println("Invalid operator for comparison");
        return false;
    }
  }

  public static int[] createOrderArray(int[] partialOrder, int numberOfLoopVars) {
    int[] ret = new int[numberOfLoopVars];
    int otherOrdersStartIndex = partialOrder.length;
    int currIndex = numberOfLoopVars - 1;
    for (int i = partialOrder.length - 1; i >= 0; i--) {
      ret[currIndex] = partialOrder[i];
      currIndex--;
    }
    for (int i = 0; i < numberOfLoopVars; i++) {
      Boolean found = false;
      for (int j = 0; j < partialOrder.length; j++) {
        if (partialOrder[j] == i) {
          found = true;
          break;
        }
      }
      if (!found) {
        ret[currIndex] = i;
        currIndex--;
      }
    }
    return ret;
  }

  public static int[] permuteArray(int[] array, int[] direction) {
    int[] ret = new int[array.length];
    for (int i = 0; i < array.length; i++) {
      ret[i] = array[direction[i]];
    }
    return ret;
  }

  public static String[] permuteStringArray(String[] array, int[] direction) {
    String[] ret = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      ret[i] = array[direction[i]];
    }
    return ret;
  }

  public static int findIndexIntArray(int[] arr, int value) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == value) {
        return i;
      }
    }
    System.out.println("Index not found!");
    return 0;
  }
}
