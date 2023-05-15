package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecGreaterThanNode extends VecBinaryNode {

  @Specialization
  protected boolean compare(int left, int right) {
    return left > right;
  }

  @Specialization
  protected boolean compare(double left, double right) {
    return left > right;
  }

  @Specialization
  protected boolean compare(double left, int right) {
    return left > ((double) right);
  }

  @Specialization
  protected boolean compare(int left, double right) {
    return ((double) left) > right;
  }
}
