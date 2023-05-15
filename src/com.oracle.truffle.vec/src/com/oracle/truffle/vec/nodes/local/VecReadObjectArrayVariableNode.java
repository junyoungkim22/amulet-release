package com.oracle.truffle.vec.nodes.local;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

public final class VecReadObjectArrayVariableNode extends VecExpressionNode {
  @Children private final VecExpressionNode[] indexNodes;
  private final int dimensions;
  private final Object arr;

  public VecReadObjectArrayVariableNode(VecExpressionNode[] indexNodes, Object arr) {
    this.indexNodes = indexNodes;
    this.arr = arr;
    this.dimensions = indexNodes.length;
  }

  /*
  @ExplodeLoop
  @Override
  public int executeObject(VirtualFrame frame) throws UnexpectedResultException {
      CompilerAsserts.compilationConstant(this.dimensions);
      switch(this.dimensions) {
          case 1:
              return ((int[]) this.intArr)[indexNodes[0].executeInt(frame)];
          default:
              Object value = ((Object[]) this.intArr)[indexNodes[0].executeInt(frame)];
              for(int i = 1; i < this.dimensions-1; i++) {
                  value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
              }
              return ((int[]) value)[indexNodes[dimensions-1].executeInt(frame)];
      }
  }
  */

  @ExplodeLoop
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    try {
      CompilerAsserts.compilationConstant(this.dimensions);
      switch (this.dimensions) {
        case 0:
          return this.arr;
        case 1:
          return ((Object[]) this.arr)[indexNodes[0].executeInt(frame)];
        default:
          Object value = ((Object[]) this.arr)[indexNodes[0].executeInt(frame)];
          for (int i = 1; i < this.dimensions - 1; i++) {
            value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
          }
          return ((Object[]) value)[indexNodes[dimensions - 1].executeInt(frame)];
      }
    } catch (UnexpectedResultException e) {
      System.out.println("Exception in reading array");
      return false;
    }
  }
}
