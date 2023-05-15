package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecAddNode extends VecBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int add(int left, int right) {
    return left + right;
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double add(double left, int right) {
    return left + (double) right;
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double add(int left, double right) {
    return (double) left + right;
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double add(double left, double right) {
    return left + right;
  }
}
