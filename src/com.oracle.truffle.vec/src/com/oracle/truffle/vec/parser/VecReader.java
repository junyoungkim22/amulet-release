package com.oracle.truffle.vec.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.parser.generated.VecBaseVisitor;
import com.oracle.truffle.parser.generated.VecParser;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import com.oracle.truffle.vec.nodes.controlflow.VecBlockNode;
import com.oracle.truffle.vec.nodes.controlflow.VecExecutionPlanNode;
import com.oracle.truffle.vec.nodes.expression.VecAddNode;
import com.oracle.truffle.vec.nodes.expression.VecAddNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecAndNode;
import com.oracle.truffle.vec.nodes.expression.VecGreaterThanNode;
import com.oracle.truffle.vec.nodes.expression.VecGreaterThanNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecGreaterThanOrEqualNode;
import com.oracle.truffle.vec.nodes.expression.VecGreaterThanOrEqualNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecIntLiteralNode;
import com.oracle.truffle.vec.nodes.expression.VecLessThanNode;
import com.oracle.truffle.vec.nodes.expression.VecLessThanNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecLessThanOrEqualNode;
import com.oracle.truffle.vec.nodes.expression.VecLessThanOrEqualNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecModuloNode;
import com.oracle.truffle.vec.nodes.expression.VecModuloNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecMulNode;
import com.oracle.truffle.vec.nodes.expression.VecMulNodeGen;
import com.oracle.truffle.vec.nodes.expression.VecSubNode;
import com.oracle.truffle.vec.nodes.expression.VecSubNodeGen;
import com.oracle.truffle.vec.nodes.local.VecIndexDoubleArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecReadDoubleArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecReadIntArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecReadLocalVariableNodeGen;
import com.oracle.truffle.vec.nodes.local.VecReadObjectArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecWriteDoubleArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecWriteIntArrayVariableNode;
import com.oracle.truffle.vec.nodes.local.VecWriteLocalVariableNode;
import com.oracle.truffle.vec.nodes.local.VecWriteLocalVariableNodeGen;
import com.oracle.truffle.vec.nodes.simd.VecConvKernelNode;
import com.oracle.truffle.vec.nodes.simd.VecGotoKernel8x8Node;
import com.oracle.truffle.vec.nodes.simd.VecMatmulKernel1D2x8Node;
import com.oracle.truffle.vec.nodes.simd.VecMatmulKernel2x8Node;
import com.oracle.truffle.vec.nodes.simd.VecMatmulKernel8x16Node;
import com.oracle.truffle.vec.nodes.simd.VecSimdDoubleFmaddNode;
import com.oracle.truffle.vec.parser.gotoParser.GotoStmtReader;
import com.oracle.truffle.vec.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;

public class VecReader extends VecBaseVisitor<VecStatementNode> {

  private HashMap<String, Object> arrMap;

  private FrameDescriptor frameDescriptor;
  private HashMap<String, FrameSlot> locals;

  private ParseResult parseResult;

  public VecReader(
      HashMap<String, Object> arrMap, FrameDescriptor frameDescriptor, ParseResult parseResult) {
    this.arrMap = arrMap;
    this.frameDescriptor = frameDescriptor;
    this.locals = new HashMap<String, FrameSlot>();
    this.parseResult = parseResult;
    this.parseResult.locals = this.locals;
  }

  public FrameDescriptor getFrameDescriptor() {
    return this.frameDescriptor;
  }

  public HashMap<String, FrameSlot> getLocals() {
    return this.locals;
  }

  @Override
  public VecStatementNode visitBlockParse(VecParser.BlockParseContext ctx) {
    return this.visit(ctx.block());
  }

  @Override
  public VecStatementNode visitForParse(VecParser.ForParseContext ctx) {
    return this.visit(ctx.forStmt());
  }

  @Override
  public VecStatementNode visitForStmt(VecParser.ForStmtContext ctx) {
    VecExpressionNode loopCondition = (VecExpressionNode) this.visit(ctx.expr());
    VecStatementNode initStatement = this.visit(ctx.assignment(0));
    this.parseResult.initStatements.add(initStatement);
    VecStatementNode updateStatement = this.visit(ctx.assignment(1));
    this.parseResult.updateStatements.add(updateStatement);

    if (ctx.assignment(0) instanceof VecParser.EqualAssnContext) {
      VecParser.EqualAssnContext equalAssn = (VecParser.EqualAssnContext) ctx.assignment(0);
      this.parseResult.loopStarts.add((VecExpressionNode) this.visit(equalAssn.expr()));
      if (equalAssn.var() instanceof VecParser.IdVarContext) {
        this.parseResult.loopVariableStrs.add(equalAssn.var().getText());
      } else {
        System.out.println("Loop init not form of assignment");
        return null;
      }
    } else {
      System.out.println("Loop init not form of assignment");
      return null;
    }

    if (ctx.expr() instanceof VecParser.RelationalExprContext) {
      VecParser.RelationalExprContext relExpr = (VecParser.RelationalExprContext) ctx.expr();
      this.parseResult.loopEnds.add((VecExpressionNode) this.visit(relExpr.expr(1)));
      this.parseResult.compareOpStrings.add((relExpr.op.getText()));
    } else {
      System.out.println("Loop condition not form of relational expr");
      return null;
    }

    if (ctx.assignment(1) instanceof VecParser.UnaryAssnFrontContext) {
      VecParser.UnaryAssnFrontContext assnContext =
          (VecParser.UnaryAssnFrontContext) ctx.assignment(1);
      this.parseResult.updates.add(getIncrementExpression(assnContext.UNARY_OP().getText()));
      this.parseResult.negates.add(false);
    } else if (ctx.assignment(1) instanceof VecParser.UnaryAssnBackContext) {
      VecParser.UnaryAssnBackContext assnContext =
          (VecParser.UnaryAssnBackContext) ctx.assignment(1);
      this.parseResult.updates.add(getIncrementExpression(assnContext.UNARY_OP().getText()));
      this.parseResult.negates.add(false);
    } else if (ctx.assignment(1) instanceof VecParser.EqualAssnContext) {
      VecParser.EqualAssnContext assnContext = (VecParser.EqualAssnContext) ctx.assignment(1);
      VecExpressionNode updateExpr = (VecExpressionNode) this.visit(assnContext.expr());
      switch (assnContext.assignop().getText()) {
        case "+=":
          this.parseResult.updates.add(updateExpr);
          this.parseResult.negates.add(false);
          break;
        case "-=":
          this.parseResult.updates.add(updateExpr);
          this.parseResult.negates.add(true);
          break;
        default:
          System.out.println("Invalid assign update!");
          return null;
      }
    }
    for (VecParser.StmtContext stmtContext : ctx.block().stmt()) {
      if (stmtContext instanceof VecParser.AssignStatementContext
          || stmtContext instanceof VecParser.SimdDoubleFmaddStatementContext
          || stmtContext instanceof VecParser.MatmulKernel8x16StatementContext
          || stmtContext instanceof VecParser.MatmulKernel1D2x8StatementContext
          || stmtContext instanceof VecParser.MatmulKernel2x8StatementContext
          || stmtContext instanceof VecParser.GotoKernel8x8StatementContext
          || stmtContext instanceof VecParser.ConvKernelStatementContext) {
        final VecStatementNode loopBodyStatement = this.visit(stmtContext);
        this.parseResult.loopBodyStatements.add(loopBodyStatement);
        this.parseResult.loopBodyStatementStrings.add(stmtContext.getText());
      } else {
        this.visit(stmtContext);
        // No need to add anything to parseResult.loopBodyStatements, info is added while visiting
        // if statement
      }
    }
    // this.visit(ctx.block());

    return null;
  }

  @Override
  public VecStatementNode visitDeclarativeLoop(VecParser.DeclarativeLoopContext ctx) {
    parseResult.isDeclarativeLoop = true;
    int numberOfVars = ctx.var().size();
    for (int i = 0; i < numberOfVars; i++) {
      VecExpressionNode oneLiteralNode = new VecIntLiteralNode(1);
      parseResult.updates.add(oneLiteralNode);
      VecExpressionNode idVarNode = (VecExpressionNode) this.visit(ctx.var().get(i));
      VecExpressionNode addNode = VecAddNodeGen.create(idVarNode, oneLiteralNode);
      parseResult.updateStatements.add(
          createWriteVarNode((VecParser.IdVarContext) ctx.var().get(i), addNode));
      parseResult.initStatements.add(
          createWriteVarNode(
              (VecParser.IdVarContext) ctx.var().get(i),
              (VecExpressionNode) this.visit(ctx.expr().get(i * 2))));
      parseResult.negates.add(false);
      parseResult.compareOpStrings.add("<");
      parseResult.loopVariableStrs.add(ctx.var().get(i).getText());
      parseResult.loopStarts.add((VecExpressionNode) this.visit(ctx.expr().get(i * 2)));
      parseResult.loopEnds.add((VecExpressionNode) this.visit(ctx.expr().get((i * 2) + 1)));
    }

    for (VecParser.StmtContext stmtContext : ctx.block().stmt()) {
      if (stmtContext instanceof VecParser.AssignStatementContext
          || stmtContext instanceof VecParser.SimdDoubleFmaddStatementContext
          || stmtContext instanceof VecParser.MatmulKernel8x16StatementContext
          || stmtContext instanceof VecParser.MatmulKernel1D2x8StatementContext
          || stmtContext instanceof VecParser.MatmulKernel2x8StatementContext
          || stmtContext instanceof VecParser.GotoKernel8x8StatementContext
          || stmtContext instanceof VecParser.ConvKernelStatementContext) {
        final VecStatementNode loopBodyStatement = this.visit(stmtContext);
        this.parseResult.loopBodyStatements.add(loopBodyStatement);
        this.parseResult.loopBodyStatementStrings.add(stmtContext.getText());
      } else {
        this.visit(stmtContext);
        // No need to add anything to parseResult.loopBodyStatements, info is added while visiting
        // if statement
      }
    }

    return null;
  }

  @Override
  public VecStatementNode visitIfStmt(VecParser.IfStmtContext ctx) {
    VecExpressionNode condition = (VecExpressionNode) this.visit(ctx.expr());
    VecStatementNode body = this.visit(ctx.block());
    VecExpressionNode[] conditions;
    if (condition instanceof VecAndNode) {
      VecAndNode andCond = (VecAndNode) condition;
      ArrayList<VecExpressionNode> andCondArrayList = andCond.getConditionList();
      conditions = new VecExpressionNode[andCondArrayList.size()];
      conditions = andCondArrayList.toArray(conditions);
    } else {
      conditions = new VecExpressionNode[1];
      conditions[0] = condition;
    }
    // this.parseResult.conditions = conditions;
    // this.parseResult.ifBody = body;
    final IfStatementInfo ifStatementInfo = new IfStatementInfo(conditions, body);
    this.parseResult.loopBodyStatements.add(ifStatementInfo);

    final VecExecutionPlanNode result =
        new VecExecutionPlanNode(conditions, body, "LOGICAL_AND", null);
    return result;
  }

  @Override
  public VecStatementNode visitBlock(VecParser.BlockContext ctx) {
    VecStatementNode[] stmtArr = new VecStatementNode[ctx.stmt().size()];
    int i = 0;
    for (VecParser.StmtContext stmtContext : ctx.stmt()) {
      VecStatementNode stmt = this.visit(stmtContext);
      stmtArr[i] = stmt;
      i++;
    }
    final VecBlockNode result = new VecBlockNode(stmtArr);
    return result;
  }

  @Override
  public VecStatementNode visitAssignStatement(VecParser.AssignStatementContext ctx) {
    return this.visit(ctx.assignment());
  }

  @Override
  public VecStatementNode visitEqualAssn(VecParser.EqualAssnContext ctx) {
    VecExpressionNode exprNode = (VecExpressionNode) this.visit(ctx.expr());
    VecExpressionNode assignNode = null;
    VecExpressionNode varNode = null;
    switch (ctx.assignop().getText()) {
      case "=":
        assignNode = exprNode;
        break;
      case "+=":
        varNode = (VecExpressionNode) this.visit(ctx.var());
        assignNode = VecAddNodeGen.create(varNode, exprNode);
        break;
      case "-=":
        varNode = (VecExpressionNode) this.visit(ctx.var());
        assignNode = VecSubNodeGen.create(varNode, exprNode);
        break;
      case "*=":
        varNode = (VecExpressionNode) this.visit(ctx.var());
        assignNode = VecMulNodeGen.create(varNode, exprNode);
        break;
    }
    if (ctx.var() instanceof VecParser.ArrayVarContext) {
      VecParser.ArrayVarContext arrayVar = (VecParser.ArrayVarContext) ctx.var();
      return createWriteArrayNode(arrayVar, assignNode);
    } else if (ctx.var() instanceof VecParser.IdVarContext) {
      VecParser.IdVarContext idVar = (VecParser.IdVarContext) ctx.var();
      return createWriteVarNode(idVar, assignNode);
    }
    return null;
  }

  @Override
  public VecStatementNode visitIdVar(VecParser.IdVarContext ctx) {
    String name = ctx.ID().getText();
    if (this.arrMap.containsKey(name)) { // When referencing an array variable without indexing
      String arrType = arrMap.get(name).getClass().getName();
      // System.out.println(arrType);
      if (arrType.contains("[")) {
        // System.out.println(name);
        // System.out.println("ARRAY address");
        final VecExpressionNode[] indexNodes = new VecExpressionNode[0];
        final VecStatementNode result =
            new VecReadObjectArrayVariableNode(indexNodes, this.arrMap.get(name));
        return result;
      }
    }
    FrameSlot frameSlot = this.frameDescriptor.findOrAddFrameSlot(name, FrameSlotKind.Int);
    this.locals.put(name, frameSlot);
    final VecStatementNode result = VecReadLocalVariableNodeGen.create(frameSlot);
    return result;
  }

  @Override
  public VecStatementNode visitArrayVar(VecParser.ArrayVarContext ctx) {
    String name = ctx.ID().getText();
    if (this.arrMap.containsKey(name)) {
      final VecExpressionNode[] indexNodes = new VecExpressionNode[ctx.expr().size()];
      int dimension = 0;
      for (VecParser.ExprContext exprCtx : ctx.expr()) {
        indexNodes[dimension] = (VecExpressionNode) this.visit(exprCtx);
        dimension++;
      }
      String arrType = arrMap.get(name).getClass().getName();
      if (arrType.contains("I")) {
        final VecReadIntArrayVariableNode result =
            new VecReadIntArrayVariableNode(indexNodes, arrMap.get(name));
        return result;
      } else if (arrType.contains("D")) {
        if (StringUtils.countMatches(arrType, "[") > dimension) {
          final VecIndexDoubleArrayVariableNode result =
              new VecIndexDoubleArrayVariableNode(indexNodes, arrMap.get(name));
          return result;
        } else {
          final VecReadDoubleArrayVariableNode result =
              new VecReadDoubleArrayVariableNode(indexNodes, arrMap.get(name));
          return result;
        }
      } else {
        System.out.println("Unknown array type encountered while parsing!");
        return null;
      }
    } else {
      System.out.println("Use of undefined Array variable!");
      return null;
    }
  }

  @Override
  public VecStatementNode visitIntAtom(VecParser.IntAtomContext ctx) {
    final VecIntLiteralNode result = new VecIntLiteralNode(Integer.parseInt(ctx.getText()));
    return result;
  }

  @Override
  public VecStatementNode visitParenExpr(VecParser.ParenExprContext ctx) {
    return (VecExpressionNode) this.visit(ctx.expr());
  }

  @Override
  public VecStatementNode visitRelationalExpr(VecParser.RelationalExprContext ctx) {
    VecExpressionNode lhs = (VecExpressionNode) this.visit(ctx.expr(0));
    VecExpressionNode rhs = (VecExpressionNode) this.visit(ctx.expr(1));
    if (ctx.op.getText().equals("<")) {
      final VecLessThanNode result = VecLessThanNodeGen.create(lhs, rhs);
      return result;
    } else if (ctx.op.getText().equals(">")) {
      final VecGreaterThanNode result = VecGreaterThanNodeGen.create(lhs, rhs);
      return result;
    } else if (ctx.op.getText().equals(">=")) {
      final VecGreaterThanOrEqualNode result = VecGreaterThanOrEqualNodeGen.create(lhs, rhs);
      return result;

    } else if (ctx.op.getText().equals("<=")) {
      final VecLessThanOrEqualNode result = VecLessThanOrEqualNodeGen.create(lhs, rhs);
      return result;
    } else if (ctx.op.getText().equals("+")) {
      final VecAddNode result = VecAddNodeGen.create(lhs, rhs);
      return result;
    } else if (ctx.op.getText().equals("-")) {
      final VecSubNode result = VecSubNodeGen.create(lhs, rhs);
      return result;
    } else if (ctx.op.getText().equals("*")) {
      final VecMulNode result = VecMulNodeGen.create(lhs, rhs);
      return result;
    } else if (ctx.op.getText().equals("%")) {
      final VecModuloNode result = VecModuloNodeGen.create(lhs, rhs);
      return result;
    }
    System.out.println("Invalid binary operator!!");
    return null;
  }

  @Override
  public VecStatementNode visitAndExpr(VecParser.AndExprContext ctx) {
    VecExpressionNode lhs = (VecExpressionNode) this.visit(ctx.expr(0));
    VecExpressionNode rhs = (VecExpressionNode) this.visit(ctx.expr(1));
    final VecAndNode result = new VecAndNode(lhs, rhs);
    return result;
  }

  private VecExpressionNode getIncrementExpression(String unaryOp) {
    switch (unaryOp) {
      case "++":
        return new VecIntLiteralNode(1);
      case "--":
        return new VecIntLiteralNode(-1);
      default:
        System.out.println("Invalid unary operator");
        return null;
    }
  }

  private VecStatementNode createWriteArrayNode(
      VecParser.ArrayVarContext ctx, VecExpressionNode exprNode) {
    String arrayName = ctx.ID().getText();
    final VecExpressionNode[] indexNodes = new VecExpressionNode[ctx.expr().size()];
    int i = 0;
    for (VecParser.ExprContext exprCtx : ctx.expr()) {
      indexNodes[i] = (VecExpressionNode) this.visit(exprCtx);
      i++;
    }
    String arrType = this.arrMap.get(arrayName).getClass().getName();
    if (arrType.contains("I")) {
      final VecWriteIntArrayVariableNode result =
          new VecWriteIntArrayVariableNode(indexNodes, exprNode, arrMap.get(arrayName));
      return result;
    } else if (arrType.contains("D")) {
      final VecWriteDoubleArrayVariableNode result =
          new VecWriteDoubleArrayVariableNode(indexNodes, exprNode, arrMap.get(arrayName));
      return result;
    } else {
      System.out.println("Unknown array type encountered while parsing!");
      return null;
    }
  }

  private VecStatementNode createWriteVarNode(
      VecParser.IdVarContext ctx, VecExpressionNode exprNode) {
    String name = ctx.ID().getText();
    FrameSlot frameSlot = this.frameDescriptor.findOrAddFrameSlot(name, FrameSlotKind.Int);
    this.locals.put(name, frameSlot);
    VecWriteLocalVariableNode result = VecWriteLocalVariableNodeGen.create(exprNode, frameSlot);
    return result;
  }

  @Override
  public VecStatementNode visitUnaryAssnBack(VecParser.UnaryAssnBackContext ctx) {
    VecExpressionNode varExpr = (VecExpressionNode) this.visit(ctx.var());
    VecExpressionNode incrementExpr = getIncrementExpression(ctx.UNARY_OP().getText());
    VecAddNode addExpr = VecAddNodeGen.create(varExpr, incrementExpr);
    if (ctx.var() instanceof VecParser.ArrayVarContext) {
      VecParser.ArrayVarContext arrayVar = (VecParser.ArrayVarContext) ctx.var();
      return createWriteArrayNode(arrayVar, addExpr);
    } else if (ctx.var() instanceof VecParser.IdVarContext) {
      VecParser.IdVarContext idVar = (VecParser.IdVarContext) ctx.var();
      return createWriteVarNode(idVar, addExpr);
    }
    return null;
  }

  @Override
  public VecStatementNode visitUnaryAssnFront(VecParser.UnaryAssnFrontContext ctx) {
    VecExpressionNode varExpr = (VecExpressionNode) this.visit(ctx.var());
    VecExpressionNode incrementExpr = getIncrementExpression(ctx.UNARY_OP().getText());
    VecAddNode addExpr = VecAddNodeGen.create(varExpr, incrementExpr);
    if (ctx.var() instanceof VecParser.ArrayVarContext) {
      VecParser.ArrayVarContext arrayVar = (VecParser.ArrayVarContext) ctx.var();
      return createWriteArrayNode(arrayVar, addExpr);
    } else if (ctx.var() instanceof VecParser.IdVarContext) {
      VecParser.IdVarContext idVar = (VecParser.IdVarContext) ctx.var();
      return createWriteVarNode(idVar, addExpr);
    }
    return null;
  }

  @Override
  public VecStatementNode visitSimdDoubleFmaddStatement(
      VecParser.SimdDoubleFmaddStatementContext ctx) {
    return this.visit(ctx.simdDoubleFmadd());
  }

  @Override
  public VecStatementNode visitSimdDoubleFmadd(VecParser.SimdDoubleFmaddContext ctx) {
    VecExpressionNode multVarExpr = (VecExpressionNode) this.visit(ctx.expr(0));
    VecExpressionNode inputArrExpr = (VecExpressionNode) this.visit(ctx.expr(1));
    VecExpressionNode outputArrExpr = (VecExpressionNode) this.visit(ctx.expr(2));
    return new VecSimdDoubleFmaddNode(multVarExpr, inputArrExpr, outputArrExpr);
  }

  @Override
  public VecStatementNode visitMatmulKernel8x16Statement(
      VecParser.MatmulKernel8x16StatementContext ctx) {
    return this.visit(ctx.matmulKernel8x16());
  }

  @Override
  public VecStatementNode visitMatmulKernel8x16(VecParser.MatmulKernel8x16Context ctx) {
    VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
    VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
    VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
    VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
    VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
    VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
    VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
    return new VecMatmulKernel8x16Node(
        aExpr, bExpr, resultExpr, kPanelSizeExpr, iExpr, kExpr, jExpr);
  }

  @Override
  public VecStatementNode visitMatmulKernel1D2x8Statement(
      VecParser.MatmulKernel1D2x8StatementContext ctx) {
    return this.visit(ctx.matmulKernel1D2x8());
  }

  @Override
  public VecStatementNode visitMatmulKernel1D2x8(VecParser.MatmulKernel1D2x8Context ctx) {
    VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
    VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
    VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
    VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
    VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
    VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
    VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
    return new VecMatmulKernel1D2x8Node(
        aExpr, bExpr, resultExpr, kPanelSizeExpr, iExpr, kExpr, jExpr);
  }

  @Override
  public VecStatementNode visitMatmulKernel2x8Statement(
      VecParser.MatmulKernel2x8StatementContext ctx) {
    return this.visit(ctx.matmulKernel2x8());
  }

  @Override
  public VecStatementNode visitMatmulKernel2x8(VecParser.MatmulKernel2x8Context ctx) {
    VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
    VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
    VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
    VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
    VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
    VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
    VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
    return new VecMatmulKernel2x8Node(
        aExpr, bExpr, resultExpr, kPanelSizeExpr, iExpr, kExpr, jExpr);
  }

  @Override
  public VecStatementNode visitGotoKernel8x8Statement(VecParser.GotoKernel8x8StatementContext ctx) {
    return this.visit(ctx.gotoKernel8x8());
  }

  @Override
  public VecStatementNode visitGotoKernel8x8(VecParser.GotoKernel8x8Context ctx) {
    final VecExpressionNode[] posNodes = new VecExpressionNode[ctx.expr().size()];
    int dimension = 0;
    for (VecParser.ExprContext exprCtx : ctx.expr()) {
      posNodes[dimension] = (VecExpressionNode) this.visit(exprCtx);
      dimension++;
    }
    String stmtString = ctx.STRING().getText();
    stmtString = stmtString.substring(1, stmtString.length() - 1); // Remove double quotes
    GotoStmtReader reader = Utils.stringToGotoOpCode(stmtString, arrMap);
    VecGotoKernel8x8Node ret =
        new VecGotoKernel8x8Node(reader, posNodes[0], posNodes[1], posNodes[2], posNodes[3]);
    // ret.arrs[3] = arrMap.get("threshold");
    // return ret;
    return ret;
  }

  @Override
  public VecStatementNode visitConvKernelStatement(VecParser.ConvKernelStatementContext ctx) {
    return this.visit(ctx.convKernel());
  }

  @Override
  public VecStatementNode visitConvKernel(VecParser.ConvKernelContext ctx) {
    final VecExpressionNode[] posNodes = new VecExpressionNode[ctx.expr().size()];
    int dimension = 0;
    for (VecParser.ExprContext exprCtx : ctx.expr()) {
      posNodes[dimension] = (VecExpressionNode) this.visit(exprCtx);
      dimension++;
    }
    String stmtString = ctx.STRING().getText();
    stmtString = stmtString.substring(1, stmtString.length() - 1); // Remove double quotes
    // GotoStmtReader reader = Utils.stringToGotoOpCode(stmtString, arrMap);
    VecConvKernelNode ret =
        new VecConvKernelNode(
            arrMap,
            posNodes[0] /*kPanelSize*/,
            posNodes[1] /*i*/,
            posNodes[2] /*k*/,
            posNodes[3] /*j*/);
    return ret;
  }
}
