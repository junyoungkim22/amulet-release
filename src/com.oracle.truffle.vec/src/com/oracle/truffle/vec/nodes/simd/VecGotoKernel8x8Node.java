package com.oracle.truffle.vec.nodes.simd;

import static com.oracle.truffle.vec.parser.gotoParser.GotoOpCode.*;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import com.oracle.truffle.vec.nodes.simd.dag.ExprDag;
import com.oracle.truffle.vec.parser.gotoParser.GotoOpCode;
import com.oracle.truffle.vec.parser.gotoParser.GotoStmtReader;
import com.oracle.truffle.vec.utils.Utils;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import sun.misc.Unsafe;

public final class VecGotoKernel8x8Node extends VecStatementNode {
  public static int kernelHeight;
  public static int kernelWidth;

  public static int mLength;
  public static int kLength;
  public static int nLength;

  @Child private VecExpressionNode aArrExpr;
  @Child private VecExpressionNode bArrExpr;
  @Child private VecExpressionNode resultArrExpr;
  @Child private VecExpressionNode kPanelSizeExpr;
  @Child private VecExpressionNode iPosExpr;
  @Child private VecExpressionNode kPosExpr;
  @Child private VecExpressionNode jPosExpr;

  private final long[] constArgs;
  public final Object[] arrs;
  public final boolean packMatrix;
  public final String arch;

  public VecGotoKernel8x8Node(
      GotoStmtReader reader,
      VecExpressionNode kPanelSizeExpr,
      VecExpressionNode i,
      VecExpressionNode k,
      VecExpressionNode j) {

    Properties prop = new Properties();
    String fileName = "app.config";
    try (FileInputStream fis = new FileInputStream(fileName)) {
      prop.load(fis);
    } catch (Exception ex) {
      System.out.println("Error with app.config!");
    }
    if (prop.getProperty("pack").equals("true")) {
      this.packMatrix = true;
    } else {
      this.packMatrix = false;
    }
    this.arch = prop.getProperty("arch");

    reader.changeFormArgs(packMatrix);

    this.arrs = new Object[3 + reader.varArgs.size() + 1];
    arrs[0] = reader.aArr;
    arrs[1] = reader.bArr;
    arrs[2] = reader.cArr;
    for (int index = 3; index < arrs.length - 1; index++) {
      arrs[index] = reader.varArgs.get(index - 3);
    }
    if (reader.getArrMap().containsKey("debugarray")) {
      arrs[arrs.length - 1] = reader.getArrMap().get("debugarray");
    }

    this.kPanelSizeExpr = kPanelSizeExpr;
    this.iPosExpr = i;
    this.kPosExpr = k;
    this.jPosExpr = j;
    String argStringRaw = reader.argString;

    Set<String> toLoad = new HashSet<String>();

    // toLoad.add(GotoOpCode.A);
    // toLoad.add(GotoOpCode.B);
    // toLoad.add(GotoOpCode.VARIABLEARG + "00000");

    PrintWriter debugLog = null;
    try {
      debugLog =
          new PrintWriter(
              System.getenv("PROJECT_HOME") + "/amulet-release/log.txt", "UTF-8");
    } catch (Exception e) {
      System.out.println(e);
    }
    ExprDag exprDag = new ExprDag(argStringRaw, debugLog, toLoad);
    debugPrintOpString(argStringRaw);
    ExprDag.printDAG(debugLog, exprDag.getRootNode());
    argStringRaw = exprDag.createCode();
    System.out.println("Code string: " + argStringRaw);
    debugLog.close();
    int numberOfLongsNeeded = (argStringRaw.length() / 63) + 1;
    if (packMatrix) {
      this.constArgs =
          new long
              [3
                  + 1
                  + numberOfLongsNeeded
                  + 1
                  + reader.constArgs.size()
                  + 1
                  + reader.varArgs.size()
                  + 3
                  + 1
                  + 3];
    } else {
      this.constArgs =
          new long
              [3
                  + 1
                  + numberOfLongsNeeded
                  + 1
                  + reader.constArgs.size()
                  + 1
                  + reader.varArgs.size()
                  + 3
                  + 1];
    }

    int[] kernelDimensions =
        calculateKernelSize(
            (int) reader.getMatrixForm(),
            toLoad,
            exprDag.getNumberOfRegistersUsed(),
            reader.constArgs,
            reader.varArgProperties);
    System.out.println(
        "KERNEL DIM "
            + String.valueOf(kernelDimensions[0])
            + " "
            + String.valueOf(kernelDimensions[1]));

    kernelHeight = kernelDimensions[0];
    kernelWidth = kernelDimensions[1];

    // kernelHeight = 8;
    // kernelWidth = 16;
    switch (this.arch) {
      case "avx":
        constArgs[0] = 0;
        break;
      case "avx2":
        constArgs[0] = 1;
        break;
      case "avx512":
        constArgs[0] = 2;
        break;
      default:
        constArgs[0] = 0;
    }
    constArgs[1] = kernelHeight; // aLength
    constArgs[2] = kernelWidth; // bLength
    constArgs[3] = numberOfLongsNeeded; // number of longs consisting operation string
    int dimension = 4;
    int curr = 0;
    // debugPrintOpString(argStringRaw);
    for (int index = 0; index < numberOfLongsNeeded; index++) {
      int end = (curr + 60) < argStringRaw.length() ? curr + 60 : argStringRaw.length();
      String argStr = "1" + argStringRaw.substring(curr, end);
      constArgs[dimension++] = Long.parseLong(argStr, 2);
      curr = end;
    }
    constArgs[dimension++] = reader.constArgs.size();
    System.out.println("CONSTARGS num: " + reader.constArgs.size());
    for (int index = 0; index < reader.constArgs.size(); index++) {
      double arg = reader.constArgs.get(index);
      System.out.println("CONSTARG: " + reader.constArgs.get(index));
      constArgs[dimension++] = Double.doubleToRawLongBits(arg);
    }
    System.out.println("VARARGS num: " + reader.varArgs.size());
    System.out.println("VARARGS properties length: " + reader.varArgProperties.size());
    constArgs[dimension++] = reader.varArgProperties.size();
    for (int index = 0; index < reader.varArgProperties.size(); index++) {
      constArgs[dimension++] = reader.varArgProperties.get(index);
    }

    int matrixForm = (int) reader.getMatrixForm();
    double[][] castArr = (double[][]) arrs[0];
    if (matrixForm != 1) {
      mLength = castArr.length;
      kLength = castArr[0].length;
    } else {
      mLength = castArr[0].length;
      kLength = castArr.length;
    }

    castArr = (double[][]) arrs[1];
    if (matrixForm != 2) {
      nLength = castArr[0].length;
    } else {
      nLength = castArr.length;
    }
    /*
    if(matrixForm == 0 || matrixForm == 2) {
        mLength = castArr.length;     // m
        kLength = castArr[0].length;  // k
    }
    else {
        mLength = castArr[0].length;  // m
        kLength = castArr.length;     // k
    }
    castArr = (double[][]) arrs[2];
    nLength = castArr[0].length;             // n
    */

    /*
    double[][] castArr = (double[][]) arrs[0];
    constArgs[dimension++] = (long) castArr.length;  // m
    constArgs[dimension++] = (long) castArr[0].length;  // k
    castArr = (double[][]) arrs[1];
    constArgs[dimension++] = (long) castArr[0].length;  // n
    */
    constArgs[dimension++] = (long) mLength;
    constArgs[dimension++] = (long) kLength;
    constArgs[dimension++] = (long) nLength;

    // Set kernel type: 0 : AB, 1 : A^TB, 2 : AB^T, 3 : A^TB^T, else : packed
    if (this.packMatrix) {
      int kPack = kLength;
      int[] alignOffsets = packMatrices(kPack, kernelHeight, kernelWidth, matrixForm);
      // constArgs[dimension++] = (long) kPack;
      constArgs[dimension++] = (long) 4;
      constArgs[dimension++] = (long) kPack;

      // aAlignmentOffset
      constArgs[dimension++] = alignOffsets[0];
      // bAlignmentOffset
      constArgs[dimension++] = alignOffsets[1];
    } else {
      constArgs[dimension++] = reader.getMatrixForm();
    }
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
      System.out.println("Error while executing GOTO KERNEL!");
      return;
    }
    /*
    System.out.println(kPanelSize);
    System.out.println(iPos);
    System.out.println(kPos);
    System.out.println(jPos);
    System.out.println();
    */
    CompilerDirectives.gotoKernel(arrs, kPanelSize, iPos, kPos, jPos, constArgs);
  }

  private int[] packMatrices(int kc, int kernelHeight, int kernelWidth, int matrixForm) {
    long packingStartTime = System.nanoTime();

    int[] ret = {0, 0};

    boolean align = true;

    double[] packedA = new double[mLength * kLength + 8];
    double[] packedB = new double[kLength * (nLength + kernelWidth - (nLength % kernelWidth)) + 8];
    int aAlignmentOffset = 0;
    int bAlignmentOffset = 0;
    if (align) {
      aAlignmentOffset = calculateAlignmentOffset(packedA);
      bAlignmentOffset = calculateAlignmentOffset(packedB);
    }

    boolean useJni = true;
    if (useJni) {
      Utils.nativePackMatrices(
          (double[][]) arrs[0],
          (double[][]) arrs[1],
          mLength,
          kLength,
          nLength,
          kc,
          kernelHeight,
          kernelWidth,
          aAlignmentOffset / 8,
          bAlignmentOffset / 8,
          packedA,
          packedB,
          matrixForm);
    } else {
      double[][] a = (double[][]) arrs[0];
      int dstIndex = aAlignmentOffset / 8;
      for (int kPos = 0; kPos < kLength; kPos += kc) {
        for (int iSlice = 0; iSlice < mLength; iSlice += kernelHeight) {
          for (int k = kPos; k < kPos + kc; k++) {
            for (int i = iSlice; i < Math.min(iSlice + kernelHeight, mLength); i++) {
              packedA[dstIndex++] = a[i][k];
            }
          }
        }
      }

      // System.out.println(Arrays.toString(packedA));

      double[][] b = (double[][]) arrs[1];
      dstIndex = bAlignmentOffset / 8;
      for (int kPos = 0; kPos < kLength; kPos += kc) {
        for (int jSlice = 0; jSlice < nLength; jSlice += kernelWidth) {
          for (int k = kPos; k < kPos + kc; k++) {
            for (int j = 0; j < kernelWidth; j++) {
              packedB[dstIndex++] = b[k][jSlice + j];
            }
            /*
            System.arraycopy(b[k], jSlice, packedB, dstIndex, kernelWidth);
            dstIndex += kernelWidth;
            */
          }
        }
      }
    }

    ret[0] = aAlignmentOffset;
    ret[1] = bAlignmentOffset;
    arrs[0] = packedA;
    arrs[1] = packedB;
    long packingEndTime = System.nanoTime() - packingStartTime;
    System.out.println("Packing time: " + ((float) packingEndTime / 1000000000));
    return ret;
  }

  long getArrayAddress(double[] arr) {
    Object helperArray[] = new Object[1];
    helperArray[0] = arr;
    Unsafe unsafe = getUnsafe();
    long addressOfObject = unsafe.getLong(helperArray, unsafe.arrayBaseOffset(Object[].class));
    return addressOfObject;
  }

  int calculateAlignmentOffset(double[] arr) {
    long arrAddress = getArrayAddress(arr);
    long offset = 64 - ((arrAddress + 24) % 64);
    System.out.println(offset);
    return (int) offset;
  }

  private void debugPrintOpString(String opString) {
    HashMap<String, String> debugMap = new HashMap<String, String>();
    debugMap.put(MUL, "MUL");
    debugMap.put(ADD, "ADD");
    debugMap.put(FMADD, "FMADD");
    debugMap.put(SUB, "SUB");
    debugMap.put(DIV, "DIV");
    debugMap.put(LOAD, "LOAD");
    debugMap.put(MASKMUL, "MASKMUL");
    debugMap.put(MASKADD, "MASKADD");
    debugMap.put(MASKFMADD, "MASKFMADD");
    debugMap.put(MASKSUB, "MASKSUB");
    debugMap.put(MASKDIV, "MASKDIV");
    debugMap.put(GT, "GT");
    debugMap.put(GE, "GE");
    debugMap.put(LT, "LT");
    debugMap.put(LE, "LE");
    debugMap.put(EQ, "EQ");
    debugMap.put(NEQ, "NEQ");
    debugMap.put(A, "A");
    debugMap.put(B, "B");
    debugMap.put(C, "C");
    debugMap.put(CONSTARG, "CONSTARG");
    debugMap.put(VARIABLEARG, "VARIABLEARG");
    // String newOpString = opString.substring(1, opString.length());
    String newOpString = opString;
    String partialOpString = "";
    String outputString = "";
    String prev = "";
    for (int i = 0; i < newOpString.length(); i++) {
      partialOpString += newOpString.charAt(i);
      if (partialOpString.length() == GotoOpCode.INDEXLENGTH) {
        // outputString += partialOpString;
        if (prev.equals("CONSTARG") || prev.equals("VARIABLEARG")) {
          outputString += partialOpString;
          prev = partialOpString;
        } else {
          outputString += debugMap.get(partialOpString);
          prev = debugMap.get(partialOpString);
        }
        outputString += " ";
        partialOpString = "";
      }
    }
    System.out.println(outputString);
  }

  private int[] calculateKernelSize(
      int matrixForm,
      Set<String> toLoad,
      int registersUsed,
      List<Double> constArgs,
      List<Integer> varArgProperties) {
    int[] ret = {0, 0};
    int kernelWidth;
    int kernelHeight;
    int simdSize;
    int totalSimdRegisterNum;
    switch (this.arch) {
      case "avx":
        kernelWidth = 8;
        kernelHeight = 4;
        simdSize = 4;
        totalSimdRegisterNum = 15;
        break;
      case "avx2":
        kernelWidth = 12;
        kernelHeight = 4;
        simdSize = 4;
        totalSimdRegisterNum = 16;
        break;
      case "avx512":
        kernelWidth = 16;
        kernelHeight = 12;
        simdSize = 8;
        totalSimdRegisterNum = 32;
        break;
      default:
        kernelWidth = 8;
        kernelHeight = 4;
        simdSize = 4;
        totalSimdRegisterNum = 15;
    }
    for (; kernelHeight > 0; kernelHeight -= 1) {
      if (numberOfFreeReigsters(
              matrixForm,
              toLoad,
              kernelHeight,
              kernelWidth,
              simdSize,
              totalSimdRegisterNum,
              constArgs,
              varArgProperties)
          >= registersUsed) {
        break;
      }
    }
    ret[0] = kernelHeight;
    ret[1] = kernelWidth;
    return ret;
  }

  private int numberOfFreeReigsters(
      int matrixForm,
      Set<String> toLoad,
      int kernelHeight,
      int kernelWidth,
      int simdSize,
      int totalSimdRegisterNum,
      List<Double> constArgs,
      List<Integer> varArgProperties) {
    int registersNeeded = 0;

    if (packMatrix) {
      // A
      registersNeeded += 1;
      // B
      if (this.arch.equals("avx512")) {
        registersNeeded += (kernelWidth / simdSize) * 2;
      } else {
        registersNeeded += kernelWidth / simdSize;
      }
      // C
      registersNeeded += (kernelHeight * (kernelWidth / simdSize));
      // constArgs
      for (int i = 0; i < constArgs.size(); i++) {
        if (!toLoad.contains(GotoOpCode.CONSTARG + ExprDag.toOpLengthBinaryString(i))) {
          registersNeeded += 1;
        }
      }
      // varArgs
      for (int i = 0; i < varArgProperties.size(); i++) {
        int property = varArgProperties.get(i);
        if (property == 1 || property == 3) { // v[i] || v[i][j]
          registersNeeded += 1; // Not sure
        } else if (property == 2) { // v[j]
          if (this.arch.equals("avx512")) {
            registersNeeded += (kernelWidth / simdSize) * 2;
          } else {
            registersNeeded += kernelWidth / simdSize;
          }
        }
      }
      return totalSimdRegisterNum - registersNeeded;
    }

    if (!toLoad.contains(GotoOpCode.A)) {
      registersNeeded += 1; // A
    }

    // B
    if (!toLoad.contains(GotoOpCode.B)) {
      if (matrixForm == 1 /*AtB*/) {
        registersNeeded += (kernelWidth / simdSize) * 2;
      } else {
        registersNeeded += kernelWidth / simdSize;
      }
    }
    if (matrixForm == 2 /*ABt*/) {
      registersNeeded += kernelWidth / simdSize; // B address
    }

    // C
    registersNeeded += (kernelHeight * (kernelWidth / simdSize));

    // constArgs
    for (int i = 0; i < constArgs.size(); i++) {
      if (!toLoad.contains(GotoOpCode.CONSTARG + ExprDag.toOpLengthBinaryString(i))) {
        registersNeeded += 1;
      }
    }

    // varArgs
    for (int i = 0; i < varArgProperties.size(); i++) {
      if (!toLoad.contains(GotoOpCode.VARIABLEARG + ExprDag.toOpLengthBinaryString(i))) {
        int property = varArgProperties.get(i);
        if (property == 1 || property == 3) { // v[i] || v[i][j]
          registersNeeded += 1; // Not sure
        } else if (property == 2) { // v[j]
          if (matrixForm == 1 /*AtB*/) {
            registersNeeded += (kernelWidth / simdSize) * 2;
          } else {
            registersNeeded += (kernelWidth / simdSize);
          }
        }
      }
    }
    return totalSimdRegisterNum - registersNeeded;
  }

  public static Unsafe getUnsafe() {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      return (Unsafe) f.get(null);
    } catch (Exception e) {
      /* ... */
    }
    return null;
  }
}
