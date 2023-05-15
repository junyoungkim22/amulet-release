package com.oracle.truffle.vec.nodes.local;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

public final class VecWriteDoubleArrayVariableNode extends VecExpressionNode {
  @Children private final VecExpressionNode[] indexNodes;
  private final int dimensions;
  @Child VecExpressionNode valueNode;
  private final Object doubleArr;

  public VecWriteDoubleArrayVariableNode(
      VecExpressionNode[] indexNodes, VecExpressionNode valueNode, Object doubleArr) {
    this.indexNodes = indexNodes;
    this.valueNode = valueNode;
    this.doubleArr = doubleArr;
    this.dimensions = indexNodes.length;
  }

  @ExplodeLoop
  @Override
  public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
    final double writeValue = this.valueNode.executeDouble(frame);

    CompilerAsserts.compilationConstant(this.dimensions);
    switch (this.dimensions) {
      case 1:
        ((double[]) this.doubleArr)[indexNodes[0].executeInt(frame)] = writeValue;
        break;
      default:
        Object value = ((Object[]) this.doubleArr)[indexNodes[0].executeInt(frame)];
        for (int i = 1; i < this.dimensions - 1; i++) {
          value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
        }
        ((double[]) value)[indexNodes[dimensions - 1].executeInt(frame)] = writeValue;
        break;
    }
    return writeValue;
  }

  @ExplodeLoop
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    try {
      final double writeValue = this.valueNode.executeDouble(frame);

      CompilerAsserts.compilationConstant(this.dimensions);
      switch (this.dimensions) {
        case 1:
          ((double[]) this.doubleArr)[indexNodes[0].executeInt(frame)] = writeValue;
          break;
        default:
          Object value = ((Object[]) this.doubleArr)[indexNodes[0].executeInt(frame)];
          for (int i = 1; i < this.dimensions - 1; i++) {
            value = ((Object[]) value)[indexNodes[i].executeInt(frame)];
          }
          ((double[]) value)[indexNodes[dimensions - 1].executeInt(frame)] = writeValue;
          break;
      }
      return writeValue;
    } catch (UnexpectedResultException e) {
      System.out.println("Exception in writing array");
      return false;
    }
  }

  public VecExpressionNode[] getIndexNodes() {
    return this.indexNodes;
  }

  public VecExpressionNode getValueNode() {
    return this.valueNode;
  }

  public Object getArr() {
    return this.doubleArr;
  }
}
