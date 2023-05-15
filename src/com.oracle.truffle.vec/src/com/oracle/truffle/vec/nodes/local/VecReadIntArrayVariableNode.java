package com.oracle.truffle.vec.nodes.local;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

public final class VecReadIntArrayVariableNode extends VecExpressionNode {
  @Children private final VecExpressionNode[] indexNodes;
  private final int dimensions;
  private final Object intArr;

  public VecReadIntArrayVariableNode(VecExpressionNode[] indexNodes, Object intArr) {
    this.indexNodes = indexNodes;
    this.intArr = intArr;
    this.dimensions = indexNodes.length;
  }

  @ExplodeLoop
  @Override
  public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
    CompilerAsserts.compilationConstant(this.dimensions);
    switch (this.dimensions) {
      case 1:
        return ((int[]) this.intArr)[indexNodes[0].executeInt(frame)];
      default:
        Object value = ((Object[]) this.intArr)[indexNodes[0].executeInt(frame)];
        for (int i = 1; i < this.dimensions - 1; i++) {
          value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
        }
        return ((int[]) value)[indexNodes[dimensions - 1].executeInt(frame)];
    }
  }

  @ExplodeLoop
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    try {
      CompilerAsserts.compilationConstant(this.dimensions);
      switch (this.dimensions) {
        case 1:
          return ((int[]) this.intArr)[indexNodes[0].executeInt(frame)];
        default:
          Object value = ((Object[]) this.intArr)[indexNodes[0].executeInt(frame)];
          for (int i = 1; i < this.dimensions - 1; i++) {
            value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
          }
          return ((int[]) value)[indexNodes[dimensions - 1].executeInt(frame)];
      }
    } catch (UnexpectedResultException e) {
      System.out.println("Exception in reading array");
      return false;
    }
  }
}
