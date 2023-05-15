package com.oracle.truffle.vec.nodes.local;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

public final class VecIndexDoubleArrayVariableNode extends VecExpressionNode {
  @Children private final VecExpressionNode[] indexNodes;
  private final int dimensions;
  private final Object doubleArr;

  public VecIndexDoubleArrayVariableNode(VecExpressionNode[] indexNodes, Object doubleArr) {
    this.indexNodes = indexNodes;
    this.doubleArr = doubleArr;
    this.dimensions = indexNodes.length;
  }

  /*
  @Override
  public double[] executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      return ((double[][]) doubleArr)[indexNode.executeInt(frame)];
  }
  */

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    try {
      CompilerAsserts.compilationConstant(this.dimensions);
      Object value = ((Object[]) this.doubleArr)[indexNodes[0].executeInt(frame)];
      for (int i = 1; i < this.dimensions; i++) {
        value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
      }
      return value;
      // return (Object) ((Object[]) value)[indexNodes[dimensions-1].executeInt(frame)];
    } catch (UnexpectedResultException e) {
      System.out.println("Exception in reading array");
      return false;
    }
  }
}
