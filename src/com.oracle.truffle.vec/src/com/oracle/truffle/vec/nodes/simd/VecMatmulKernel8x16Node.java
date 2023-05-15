package com.oracle.truffle.vec.nodes.simd;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;

public final class VecMatmulKernel8x16Node extends VecStatementNode {
  @Child private VecExpressionNode aArrExpr;
  @Child private VecExpressionNode bArrExpr;
  @Child private VecExpressionNode resultArrExpr;
  @Child private VecExpressionNode kPanelSizeExpr;
  @Child private VecExpressionNode iPosExpr;
  @Child private VecExpressionNode kPosExpr;
  @Child private VecExpressionNode jPosExpr;

  public VecMatmulKernel8x16Node(
      VecExpressionNode a,
      VecExpressionNode b,
      VecExpressionNode result,
      VecExpressionNode kPanelSizeExpr,
      VecExpressionNode i,
      VecExpressionNode k,
      VecExpressionNode j) {
    this.aArrExpr = a;
    this.bArrExpr = b;
    this.resultArrExpr = result;
    this.kPanelSizeExpr = kPanelSizeExpr;
    this.iPosExpr = i;
    this.kPosExpr = k;
    this.jPosExpr = j;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    double[][] a = null;
    double[][] b = null;
    double[][] result = null;
    int kPanelSize = 0;
    int iPos = 0;
    int kPos = 0;
    int jPos = 0;
    // System.out.println("Exec!!");
    try {
      a = (double[][]) aArrExpr.executeGeneric(frame);
      b = (double[][]) bArrExpr.executeGeneric(frame);
      result = (double[][]) resultArrExpr.executeGeneric(frame);

      kPanelSize = kPanelSizeExpr.executeInt(frame);
      iPos = iPosExpr.executeInt(frame);
      kPos = kPosExpr.executeInt(frame);
      jPos = jPosExpr.executeInt(frame);
    } catch (UnexpectedResultException e) {
      System.out.println("Error while executing MATMUL KERNEL 8x16!");
      return;
    }
    CompilerDirectives.matmulKernel8x16(a, b, result, kPanelSize, iPos, kPos, jPos);
  }
}
