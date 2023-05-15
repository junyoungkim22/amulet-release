package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

public final class VecIntLiteralNode extends VecExpressionNode {
  private final int value;

  public VecIntLiteralNode(int value) {
    this.value = value;
  }

  @Override
  public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
    return value;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }
}
