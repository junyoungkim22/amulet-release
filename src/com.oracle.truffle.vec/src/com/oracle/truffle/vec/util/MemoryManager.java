package com.oracle.truffle.vec.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.SplittableRandom;

public class MemoryManager {
  // TODO: Use aligned memory for this data loader.
  static SplittableRandom rand = new SplittableRandom(100);
  static int initializedArrayNum = 0;
  static final int randRange = 100;

  public static void prepareAggregateData(String inputFilePath, int[] input, int[] output) {
    final int numKeys = output.length;
    for (int i = 0; i < numKeys; ++i) {
      output[i] = 0;
    }

    // readBinaryData(inputFilePath, input);
    // Test-only
    for (int i = 0; i < input.length; ++i) input[i] = i % numKeys;
  }

  public static void prepareMaterializeData(
      String inputFilePath, int[] input, int[] data, int[] output) {
    final int numKeys = data.length;
    final int numTuples = input.length;
    assert numTuples == output.length;

    // Exact content of data[] does not matter.
    for (int i = 0; i < numKeys; ++i) {
      data[i] = i;
    }

    readBinaryData(inputFilePath, input);
  }

  public static void prepareSelectData(
      String inputFilePath, int[] input, String bitmapFilePath, int[] bitmap) {
    readBinaryData(inputFilePath, input);
    readBinaryData(bitmapFilePath, bitmap);
  }

  // selectAST
  public static void prepareSelectData(int[] input0, int[] input1, int[] input2, int[] input3) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      input0[i] = rand.nextInt(randRange);
      input1[i] = rand.nextInt(randRange);
      input2[i] = rand.nextInt(randRange);
      input3[i] = rand.nextInt(randRange);
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(int[] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      input0[i] = rand.nextInt(randRange);
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(int[][] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      for (int j = 0; j < input0[i].length; j++) {
        input0[i][j] = rand.nextInt(randRange);
      }
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(int[][][] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      for (int j = 0; j < input0[i].length; j++) {
        for (int k = 0; k < input0[i][j].length; k++) {
          input0[i][j][k] = rand.nextInt(randRange);
        }
      }
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(int[] input, int range) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input.length; ++i) {
      input[i] = rand.nextInt(range);
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(double[] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      input0[i] = rand.nextDouble(randRange);
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(double[][] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      for (int j = 0; j < input0[i].length; j++) {
        input0[i][j] = rand.nextDouble(randRange);
      }
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(double[][][] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      for (int j = 0; j < input0[i].length; j++) {
        for (int k = 0; k < input0[i][j].length; k++) {
          input0[i][j][k] = rand.nextDouble(randRange);
        }
      }
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  public static void prepareSelectData(double[][][][] input0) {
    long startTime = System.nanoTime();

    // SplittableRandom rand = new SplittableRandom();
    for (int i = 0; i < input0.length; ++i) {
      for (int j = 0; j < input0[i].length; j++) {
        for (int k = 0; k < input0[i][j].length; k++) {
          for (int l = 0; l < input0[i][j][k].length; l++) {
            input0[i][j][k][l] = rand.nextDouble(randRange);
          }
        }
      }
    }
    System.out.println("Input array init time: " + (System.nanoTime() - startTime));
  }

  private static void readBinaryData(String dataFilePath, int[] data) {
    try (FileChannel fc =
        (FileChannel) Files.newByteChannel(Paths.get(dataFilePath), StandardOpenOption.READ)) {
      ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      fc.read(byteBuffer);
      byteBuffer.flip();

      assert data.length * 4 == fc.size();
      byteBuffer.asIntBuffer().get(data);
      byteBuffer.clear();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
