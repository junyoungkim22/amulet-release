package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecMaxNode extends VecBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int max(int left, int right) {
    return Math.max(left, right);
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double max(double left, int right) {
    return Math.max(left, (double) right);
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double max(int left, double right) {
    return Math.max((double) left, right);
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double max(double left, double right) {
    return Math.max(left, right);
  }
}
