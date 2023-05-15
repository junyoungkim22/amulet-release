package com.oracle.truffle.vec.parser;

import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;

public class IfStatementInfo {
  public VecExpressionNode[] conditions;
  public VecStatementNode ifBody;

  public IfStatementInfo(VecExpressionNode[] conditions, VecStatementNode ifBody) {
    this.conditions = conditions;
    this.ifBody = ifBody;
  }
}
