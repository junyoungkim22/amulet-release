package com.oracle.truffle.vec.nodes.simd.dag;

import static com.oracle.truffle.vec.parser.gotoParser.GotoOpCode.*;

import com.oracle.truffle.vec.parser.gotoParser.GotoOpCode;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class ExprDag {
  public final class ChangeableString {
    private String str;

    public ChangeableString(String str) {
      this.str = str;
    }

    public String cutOff(int length) {
      String ret = str.substring(0, length);
      this.str = str.substring(length, str.length());
      return ret;
    }

    public String toString() {
      return str;
    }
  }

  private ExprNode rootNode;

  private final int opLength = GotoOpCode.INDEXLENGTH;

  private int idCount;
  private int registerCount;

  private HashMap<String, ExprNode> subExprMap;
  private HashMap<Integer, String> locationTracker; // id -> location (register or address)
  private HashMap<String, Integer> leafToId;
  private HashMap<Integer, Integer> referenceCount;
  private HashMap<String, Integer> nameToRegNum;
  private Set<Integer> usedRegisters;
  private Set<Integer> availableRegisters;
  private Set<String> toLoad;

  private PrintWriter debugLog;

  private ArrayList<Inst> instructions;

  public ExprDag(
      String inString, /*HashMap<String, Integer> availableValues, int[] tempRegs,*/
      PrintWriter debugLog,
      Set<String> toLoad) {
    ChangeableString opString = new ChangeableString(inString);
    this.debugLog = debugLog;
    this.subExprMap = new HashMap<>();
    this.leafToId = new HashMap<>();
    this.idCount = 0;
    this.referenceCount = new HashMap<>();
    this.toLoad = toLoad;
    this.rootNode = createDAG(opString);

    createSethiUllmanLabels(this.rootNode);
  }

  public String createCode(/*HashMap<String, Integer> availableValues, int[] tempRegs*/ ) {
    this.registerCount = 0;
    this.locationTracker = new HashMap<>();
    this.usedRegisters = new HashSet<Integer>();
    this.availableRegisters = new HashSet<Integer>();
    this.instructions = new ArrayList<>();
    generateCode(this.rootNode);
    if (debugLog != null) {
      debugLog.print("\n");
      for (Inst inst : instructions) {
        debugLog.println(inst.toDebugString());
      }
    }
    String codeString = "";
    for (Inst inst : instructions) {
      codeString += inst.toString();
    }
    return codeString;
  }

  private class Inst {
    public String op;
    public String dst;
    public String mask;
    public String src0;
    public String src1;

    public Inst() {
      op = "";
      dst = "";
      mask = "";
      src0 = "";
      src1 = "";
    }

    public String toString() {
      return op + mask + dst + src0 + src1;
    }

    public String toDebugString() {
      HashMap<String, String> debugMap = new HashMap<String, String>();
      debugMap.put(MUL, "MUL");
      debugMap.put(ADD, "ADD");
      debugMap.put(FMADD, "FMADD");
      debugMap.put(SUB, "SUB");
      debugMap.put(DIV, "DIV");
      debugMap.put(LOAD, "LOAD");
      debugMap.put(MASKMUL, "MASKMUL");
      debugMap.put(MASKADD, "MASKADD");
      debugMap.put(MASKFMADD, "MASKFMADD");
      debugMap.put(MASKSUB, "MASKSUB");
      debugMap.put(MASKDIV, "MASKDIV");
      debugMap.put(GT, "GT");
      debugMap.put(GE, "GE");
      debugMap.put(LT, "LT");
      debugMap.put(LE, "LE");
      debugMap.put(EQ, "EQ");
      debugMap.put(NEQ, "NEQ");
      debugMap.put(A, "A");
      debugMap.put(B, "B");
      debugMap.put(C, "C");
      debugMap.put(CONSTARG, "CONSTARG");
      debugMap.put(VARIABLEARG, "VARIABLEARG");
      debugMap.put(REG, "REG");
      debugMap.put(MASKREG, "MASKREG");
      String[] stringArr = new String[] {op, mask, dst, src0, src1};
      String retString = "";
      for (String str : stringArr) {
        if (str.length() == 0) {
          continue;
        } else {
          retString += debugMap.get(str.substring(0, opLength));
          if (str.substring(opLength, str.length()).length() > 0) {
            retString += " " + str.substring(opLength, str.length());
          }
          retString += " ";
        }
      }
      return retString;
    }
  }

  public ExprNode getRootNode() {
    return this.rootNode;
  }

  public int getRegister() {
    int newRegister = 0;
    if (availableRegisters.isEmpty()) {
      newRegister = registerCount++;
    } else {
      newRegister = (Integer) availableRegisters.toArray()[0];
      availableRegisters.remove(newRegister);
    }
    usedRegisters.add(newRegister);
    return newRegister;
  }

  public void generateCode(ExprNode currNode) {
    if (locationTracker.containsKey(currNode.getId())) {
      return;
    }
    String op = currNode.getOp().substring(0, opLength);
    String opType = op.substring(0, 2);
    int argIndex = 0;
    if (opType.equals(GotoOpCode.ARGOP)) {
      if (op.equals(GotoOpCode.CONSTARG) || op.equals(GotoOpCode.VARIABLEARG)) {
        op = op + currNode.getOp().substring(opLength, opLength + opLength);
      }

      if (toLoad.contains(op)) {
        Inst newInst = new Inst();
        newInst.op = GotoOpCode.LOAD;
        newInst.dst = GotoOpCode.REG + toOpLengthBinaryString(getRegister());
        newInst.src0 = op;
        instructions.add(newInst);
        locationTracker.put(currNode.getId(), newInst.dst);
      } else {
        locationTracker.put(currNode.getId(), op);
      }
    } else if (opType.equals(GotoOpCode.OP) || opType.equals(GotoOpCode.CMPOP)) {
      if (!op.equals(GotoOpCode.FMADD)) {
        if (currNode.getChildren()[0].getLabel() >= currNode.getChildren()[1].getLabel()) {
          generateCode(currNode.getChildren()[0]);
          generateCode(currNode.getChildren()[1]);
        } else {
          generateCode(currNode.getChildren()[1]);
          generateCode(currNode.getChildren()[0]);
        }

        // Free registers
        for (ExprNode child : currNode.getChildren()) {
          freeRegister(child);
        }

        Inst newInst = new Inst();
        if (currNode.getId() == rootNode.getId()) {
          newInst.dst = GotoOpCode.C;
        } else if (opType.equals(GotoOpCode.CMPOP)) {
          newInst.dst = GotoOpCode.MASKREG + "00010";
        } else {
          newInst.dst = GotoOpCode.REG + toOpLengthBinaryString(getRegister());
        }
        newInst.src0 = locationTracker.get(currNode.getChildren()[0].getId());
        newInst.src1 = locationTracker.get(currNode.getChildren()[1].getId());
        newInst.op = op;

        locationTracker.put(currNode.getId(), newInst.dst);

        if (newInst.dst.substring(0, opLength).equals(GotoOpCode.REG)) {
          usedRegisters.add(Integer.parseInt(newInst.dst.substring(opLength, opLength + opLength)));
        }
        instructions.add(newInst);
      } else {
        // Todo: Add FMADD
        ExprNode[] evalList = sortNodesByLabel(currNode.getChildren());
        for (int i = 0; i < evalList.length; i++) {
          generateCode(evalList[i]);
        }

        // Free registers
        for (ExprNode child : currNode.getChildren()) {
          freeRegister(child);
        }

        Inst newInst = new Inst();
        newInst.dst = locationTracker.get(currNode.getChildren()[0].getId());
        newInst.src0 = locationTracker.get(currNode.getChildren()[1].getId());
        newInst.src1 = locationTracker.get(currNode.getChildren()[2].getId());
        newInst.op = op;
        instructions.add(newInst);
        return;
      }
    } else if (opType.equals(GotoOpCode.MASKOP)) {
      ExprNode[] evalList = sortNodesByLabel(currNode.getChildren());
      for (int i = 0; i < evalList.length; i++) {
        generateCode(evalList[i]);
      }

      // Free registers
      for (ExprNode child : currNode.getChildren()) {
        freeRegister(child);
      }

      Inst newInst = new Inst();
      newInst.mask = locationTracker.get(currNode.getChildren()[0].getId()); // MASK
      newInst.src0 = locationTracker.get(currNode.getChildren()[1].getId());
      newInst.src1 = locationTracker.get(currNode.getChildren()[2].getId());
      newInst.op = op;

      // Todo fix logic to cover all edge cases later

      if (currNode.getId() == rootNode.getId()) {
        newInst.dst = GotoOpCode.C;
      } else {
        newInst.dst = GotoOpCode.REG + toOpLengthBinaryString(getRegister());
        // Fix THIS!!
        /*
        if(newInst.src0.substring(0, 5).equals(GotoOpCode.REG) &&
           availableRegisters.contains(Integer.parseInt(newInst.src0.substring(opLength, opLength+opLength)))) {
            int src0Reg = Integer.parseInt(newInst.src0.substring(opLength, opLength+opLength));
            availableRegisters.remove(src0Reg);
            usedRegisters.add(src0Reg);
            newInst.dst = newInst.src0;
        }
        else {
            // Todo
        }
        */
      }
      locationTracker.put(currNode.getId(), newInst.dst);
      instructions.add(newInst);
    }
  }

  // Free the register a node is using
  public void freeRegister(ExprNode node) {
    referenceCount.put(
        node.getId(), referenceCount.get(node.getId()) + 1); // increment reference count of child
    if (referenceCount.get(node.getId())
        == node.getNumberOfParents()) { // check if child does not need to be referenced again
      String location = locationTracker.get(node.getId());
      if (location.substring(0, 5).equals(GotoOpCode.REG)) { // check if child is in register
        int registerNum = Integer.parseInt(location.substring(opLength, opLength + opLength));
        if (usedRegisters.contains(registerNum)) { // check if child is using a temporary register
          usedRegisters.remove(registerNum);
          availableRegisters.add(registerNum);
        }
      }
    }
  }

  private ExprNode[] sortNodesByLabel(ExprNode[] arr) {
    ExprNode[] ret = new ExprNode[arr.length];
    ArrayList<ExprNode> arrList = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) {
      arrList.add(arr[i]);
    }
    for (int i = 0; i < arr.length; i++) {
      int min = 9999999;
      int minIndex = 0;
      for (int j = 0; j < arrList.size(); j++) {
        if (arrList.get(j).getLabel() < min) {
          min = arrList.get(j).getLabel();
          minIndex = j;
        }
      }
      ret[i] = arrList.get(minIndex);
      arrList.remove(minIndex);
    }
    return ret;
  }

  public void createSethiUllmanLabels(ExprNode currNode) {
    String op = currNode.getOp();
    String opType = op.substring(0, 2);

    ExprNode[] children = currNode.getChildren();
    int[] childLabels = null;
    if (children != null) {
      childLabels = new int[children.length];
      for (int i = 0; i < children.length; i++) {
        createSethiUllmanLabels(children[i]);
        childLabels[i] = children[i].getLabel();
      }
      int max = -1;
      int prev = -1;
      Boolean isSame = true;
      for (int i = 0; i < children.length; i++) {
        int childLabel = children[i].getLabel();
        if (childLabel > max) {
          max = childLabel;
        }
        if (prev != -1 && childLabel != prev) {
          isSame = false;
        }
        prev = childLabel;
      }
      if (isSame) {
        currNode.setLabel(max + 1);
      } else {
        currNode.setLabel(max);
      }
      return;
    } else { // currNode is leaf
      currNode.setLabel(1);
      return;
    }
  }

  public ExprNode createDAG(ChangeableString opString) {
    String op = opString.cutOff(opLength);
    String opType = op.substring(0, 2);
    int id = idCount++;
    referenceCount.put(id, 0);
    ExprNode[] children = null;

    if (op.equals(GotoOpCode.FMADD) || opType.equals(GotoOpCode.MASKOP)) { // Node with 3 children
      children = new ExprNode[3];
      children[0] = createDAG(opString);
      children[1] = createDAG(opString);
      children[2] = createDAG(opString);
    } else if (opType.equals(GotoOpCode.OP) || opType.equals(GotoOpCode.CMPOP)) { // binary node
      children = new ExprNode[2];
      children[0] = createDAG(opString);
      children[1] = createDAG(opString);
    } else { // leaf node (ARGOP)
      if (op.equals(GotoOpCode.CONSTARG) || op.equals(GotoOpCode.VARIABLEARG)) {
        op += opString.cutOff(opLength);
      }
      if (!leafToId.containsKey(op)) {
        leafToId.put(op, id);
      }
    }

    if (children != null) {
      for (int i = 0; i < children.length; i++) {
        String subExprStr = children[i].toString();
        if (subExprMap.containsKey(subExprStr)) {
          children[i] = subExprMap.get(subExprStr);
        }
        // children[i].incrementNumberOfParents();
      }
    }
    ExprNode ret = new ExprNode(op, id, children);
    if (!subExprMap.containsKey(ret.toString())) {
      subExprMap.put(ret.toString(), ret);
      if (children != null) {
        for (ExprNode child : children) {
          child.incrementNumberOfParents();
        }
      }
    }
    return ret;
  }

  public int getNumberOfRegistersUsed() {
    return registerCount;
  }

  public static void printDAG(PrintWriter writer, ExprNode rootNode) {
    HashMap<String, String> debugMap = new HashMap<String, String>();
    debugMap.put(MUL, "MUL");
    debugMap.put(ADD, "ADD");
    debugMap.put(FMADD, "FMADD");
    debugMap.put(SUB, "SUB");
    debugMap.put(DIV, "DIV");
    debugMap.put(MASKMUL, "MASKMUL");
    debugMap.put(MASKADD, "MASKADD");
    debugMap.put(MASKFMADD, "MASKFMADD");
    debugMap.put(MASKSUB, "MASKSUB");
    debugMap.put(MASKDIV, "MASKDIV");
    debugMap.put(GT, "GT");
    debugMap.put(GE, "GE");
    debugMap.put(LT, "LT");
    debugMap.put(LE, "LE");
    debugMap.put(EQ, "EQ");
    debugMap.put(NEQ, "NEQ");
    debugMap.put(A, "A");
    debugMap.put(B, "B");
    debugMap.put(C, "C");
    debugMap.put(CONSTARG, "CONSTARG");
    debugMap.put(VARIABLEARG, "VARIABLEARG");
    String op = rootNode.getOp().substring(0, 5);
    writer.print(debugMap.get(op));
    if (op.equals(GotoOpCode.CONSTARG) || op.equals(GotoOpCode.VARIABLEARG)) {
      writer.print(" " + rootNode.getOp().substring(5, 10));
    }
    writer.print(
        "("
            + rootNode.getId()
            + ", "
            + rootNode.getNumberOfParents()
            + ", "
            + rootNode.getLabel()
            + ") ");
    if (rootNode.getChildren() != null) {
      for (ExprNode child : rootNode.getChildren()) {
        printDAG(writer, child);
      }
    }
  }

  public static String toOpLengthBinaryString(int value) {
    String indexString = Integer.toBinaryString(value);
    indexString =
        String.format("%" + String.valueOf(GotoOpCode.INDEXLENGTH) + "s", indexString)
            .replaceAll(" ", "0");
    return indexString;
  }
}
