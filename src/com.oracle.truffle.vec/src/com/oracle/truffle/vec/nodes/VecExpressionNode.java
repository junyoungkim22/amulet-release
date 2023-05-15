package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@TypeSystemReference(VecTypes.class)
public abstract class VecExpressionNode extends VecStatementNode {
  public abstract Object executeGeneric(VirtualFrame frame);

  @Override
  public void executeVoid(VirtualFrame frame) {
    executeGeneric(frame);
  }

  public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
    return VecTypesGen.expectInteger(executeGeneric(frame));
  }

  public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
    return VecTypesGen.expectBoolean(executeGeneric(frame));
  }

  public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
    return VecTypesGen.expectDouble(executeGeneric(frame));
  }
}
