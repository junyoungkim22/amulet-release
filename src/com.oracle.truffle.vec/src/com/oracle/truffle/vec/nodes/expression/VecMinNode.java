package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecMinNode extends VecBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int min(int left, int right) {
    return Math.min(left, right);
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double min(double left, int right) {
    return Math.min(left, (double) right);
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double min(int left, double right) {
    return Math.min((double) left, right);
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double min(double left, double right) {
    return Math.min(left, right);
  }
}
