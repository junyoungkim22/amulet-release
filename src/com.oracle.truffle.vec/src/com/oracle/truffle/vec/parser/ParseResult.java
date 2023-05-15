package com.oracle.truffle.vec.parser;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseResult {
  public HashMap<String, FrameSlot> locals;

  public ArrayList<VecExpressionNode> updates;
  public ArrayList<VecStatementNode> updateStatements;
  public ArrayList<VecStatementNode> initStatements;
  public ArrayList<Boolean> negates;
  public ArrayList<String> compareOpStrings;
  public ArrayList<String> loopVariableStrs;
  public ArrayList<VecExpressionNode> loopStarts;
  public ArrayList<VecExpressionNode> loopEnds;

  public ArrayList<Object> loopBodyStatements;

  // For checking if loop body statements are of Goto form
  public ArrayList<String> loopBodyStatementStrings;

  public Boolean isDeclarativeLoop;

  // public VecExpressionNode[] conditions;
  // public VecStatementNode ifBody;

  public ParseResult() {
    this.updates = new ArrayList<VecExpressionNode>();
    this.updateStatements = new ArrayList<VecStatementNode>();
    this.initStatements = new ArrayList<VecStatementNode>();
    this.negates = new ArrayList<Boolean>();
    this.compareOpStrings = new ArrayList<String>();
    this.loopVariableStrs = new ArrayList<String>();
    this.loopStarts = new ArrayList<VecExpressionNode>();
    this.loopEnds = new ArrayList<VecExpressionNode>();
    this.loopBodyStatements = new ArrayList<Object>();
    this.loopBodyStatementStrings = new ArrayList<String>();
    this.isDeclarativeLoop = false;
  }
}
