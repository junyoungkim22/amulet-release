package com.oracle.truffle.vec.nodes.simd;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;

public final class VecSimdDoubleFmaddNode extends VecStatementNode {
  @Child private VecExpressionNode multVal;
  @Child private VecExpressionNode input;
  @Child private VecExpressionNode output;

  public VecSimdDoubleFmaddNode(
      VecExpressionNode multVal, VecExpressionNode input, VecExpressionNode output) {
    this.multVal = multVal;
    this.input = input;
    this.output = output;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    double multiplyValue = 0;
    double[] inputArr = null;
    double[] outputArr = null;
    // System.out.println("Exec!!");
    try {
      multiplyValue = multVal.executeDouble(frame);
      inputArr = (double[]) input.executeGeneric(frame);
      outputArr = (double[]) output.executeGeneric(frame);
    } catch (UnexpectedResultException e) {
      System.out.println("Error while executing SIMD FMADD!");
      return;
    }
    final int inputArrLength = inputArr.length;
    assert (inputArrLength == outputArr.length);
    assert (inputArrLength % 8 == 0);
    CompilerDirectives.simdDoubleFmadd(inputArrLength, multiplyValue, inputArr, outputArr);
  }
}
