package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecMulNode extends VecBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int mul(int left, int right) {
    return left * right;
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double mul(double left, double right) {
    return left * right;
  }
}
