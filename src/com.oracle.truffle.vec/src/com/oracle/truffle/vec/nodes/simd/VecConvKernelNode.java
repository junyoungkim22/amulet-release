package com.oracle.truffle.vec.nodes.simd;

import static com.oracle.truffle.vec.parser.gotoParser.GotoOpCode.*;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import java.io.PrintWriter;
import java.util.HashMap;

public final class VecConvKernelNode extends VecStatementNode {
  public static int kernelHeight;
  public static int kernelWidth;

  public static int outChannels;
  public static int inChannels;
  public static int imgLength;
  public static int kernelLength;

  @Child private VecExpressionNode kPanelSizeExpr;
  @Child private VecExpressionNode iPosExpr;
  @Child private VecExpressionNode kPosExpr;
  @Child private VecExpressionNode jPosExpr;

  private final long[] constArgs;
  public final Object[] arrs;

  public VecConvKernelNode(
      HashMap<String, Object> arrMap,
      VecExpressionNode kPanelSizeExpr,
      VecExpressionNode i,
      VecExpressionNode k,
      VecExpressionNode j) {
    this.arrs = new Object[3];

    double[][][] imgArr = (double[][][]) arrMap.get("img");
    double[][][][] kernelArr = (double[][][][]) arrMap.get("kernel");
    ;
    // double[][][] resultArr = (double[][][]) arrMap.get("result");;

    outChannels = kernelArr.length;
    inChannels = kernelArr[0].length;
    imgLength = imgArr[0].length;
    kernelLength = kernelArr[0][0].length;

    arrs[0] = imgArr;
    arrs[1] = kernelArr;
    arrs[2] = arrMap.get("result");

    this.kPanelSizeExpr = kPanelSizeExpr;
    this.iPosExpr = i;
    this.kPosExpr = k;
    this.jPosExpr = j;

    this.constArgs = new long[2 + 1 + 0 + 1 + 0 + 1 + 0 + 4 + 1];

    kernelHeight = 12;
    kernelWidth = 16;

    constArgs[0] = kernelHeight; // aLength
    constArgs[1] = kernelWidth; // bLength
    constArgs[2] = 0; // number of longs consisting operation string
    constArgs[3] = 0; // number of constant args
    constArgs[4] = 0; // number of variable args
    constArgs[5] = outChannels;
    constArgs[6] = inChannels;
    constArgs[7] = imgLength;
    constArgs[8] = kernelLength;
    constArgs[9] = 0; // kernel type
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    int kPanelSize = 0;
    int iPos = 0;
    int kPos = 0;
    int jPos = 0;
    try {
      kPanelSize = kPanelSizeExpr.executeInt(frame);
      iPos = iPosExpr.executeInt(frame);
      kPos = kPosExpr.executeInt(frame);
      jPos = jPosExpr.executeInt(frame);
    } catch (UnexpectedResultException e) {
      System.out.println("Error while executing CONV KERNEL!");
      return;
    }
    // double[][][] resultArr = (double[][][]) arrs[2];
    // resultArr[0][0][1] = 54;
    // debugPrint("adfaf");
    CompilerDirectives.convKernel(arrs, kPanelSize, iPos, kPos, jPos, constArgs);
  }

  private void debugPrint(String str) {
    PrintWriter debugLog = null;

    try {
      debugLog =
          new PrintWriter(
              "/home/junyoung2/project/project2/adaptive-code-generation/log.txt", "UTF-8");
    } catch (Exception e) {
      System.out.println(e);
    }

    debugLog.println(str);

    debugLog.close();
  }
}
