package com.oracle.truffle.vec.execution;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.vec.parallelism.ExecutionTask;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Execution {
  public static long warmupSize = Integer.valueOf(System.getProperty("warmupSize", "1"));
  public static String innermostLoopVarName;

  public static long callPlan(
      DirectCallNode plan,
      PhaseExecutionInfo phaseExecutionInfo,
      long iterations,
      Object[] otherVarValues,
      int executionMode,
      CallTarget callTarget) {
    // System.out.println("CURR");
    // System.out.println(phaseExecutionInfo.curr[0]);
    final long numIterations =
        phaseExecutionInfo.executed + iterations < phaseExecutionInfo.totalIterations
            ? iterations
            : phaseExecutionInfo.totalIterations - phaseExecutionInfo.executed;
    phaseExecutionInfo.executed += numIterations;
    if (phaseExecutionInfo.executed >= phaseExecutionInfo.totalIterations) {
      phaseExecutionInfo.continueExecution = false;
    }

    final int numberOfVars = phaseExecutionInfo.phaseLoopStarts.length;
    int[] loopEndValues = new int[numberOfVars];
    phaseExecutionInfo.calculateNextStart(numIterations, loopEndValues);

    int[][][] loopValues = phaseExecutionInfo.createPhaseLoopEnds(loopEndValues);
    try {
      long startTime = System.nanoTime();
      plan.call(loopValues, otherVarValues, executionMode);
      return System.nanoTime() - startTime;
    } catch (InterpreterException e) {
      int[] curr = new int[numberOfVars];
      for (int i = 0; i < numberOfVars; i++) {
        curr[i] = e.loopVarList.get(i);
      }
      try {
        Compilation.compileMethod.invoke(callTarget, true);
      } catch (Exception ex) {
        System.out.println(ex);
      }
      phaseExecutionInfo.curr = curr;
      phaseExecutionInfo.executed -=
          (phaseExecutionInfo.getNumberOfIterations(loopEndValues)
              - phaseExecutionInfo.getNumberOfIterations(curr));
      if (phaseExecutionInfo.executed < phaseExecutionInfo.totalIterations) {
        phaseExecutionInfo.continueExecution = true;
      }
      return 0;
    }
  }

  public static void adaptiveExecution(PhaseExecutionInfo phaseExecutionInfo) {
    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    final DirectCallNode[] plans = loopExecutionInfo.plans;
    final CallTarget[] callTargets = loopExecutionInfo.callTargets;
    final Object[] otherVarValues = phaseExecutionInfo.otherVarValues;
    final long taskStartTime = System.nanoTime();
    final long chunkSize = Integer.valueOf(System.getProperty("chunkSize", "10000"));
    final long exploitPeriod =
        Integer.valueOf(System.getProperty("exploitPeriod", "1000")) * chunkSize;
    final int numberOfPlans = plans.length;

    final int executionMode = Integer.valueOf(System.getProperty("executionMode", "1"));

    long startTime, endTime;
    long elapsedTime, shortestTime;
    int minIndex;
    int[][][] loopValues;
    Boolean compiledPlan;

    while (phaseExecutionInfo.continueExecution) {
      do {
        compiledPlan = false;
        shortestTime = Long.MAX_VALUE;
        minIndex = 0;
        for (int i = 0; i < numberOfPlans; i++) {
          elapsedTime =
              callPlan(
                  plans[i],
                  phaseExecutionInfo,
                  chunkSize,
                  otherVarValues,
                  executionMode,
                  callTargets[i]);
          if (elapsedTime == 0) {
            compiledPlan = true; // Do exploration phase again if a plan was compiled
          } else {
            if (executionMode == 1) {
              System.out.println(elapsedTime);
            }
            if (elapsedTime < shortestTime) {
              minIndex = i;
              shortestTime = elapsedTime;
            }
          }
        }
      } while (compiledPlan);
      elapsedTime =
          callPlan(
              plans[minIndex],
              phaseExecutionInfo,
              exploitPeriod,
              otherVarValues,
              executionMode,
              callTargets[minIndex]);
      if (executionMode == 1) {
        System.out.println(minIndex);
        System.out.println(elapsedTime);
        System.out.println("\n");
      }
    }
  }

  public static void parallelAdaptiveExecution(
      PhaseExecutionInfo phaseExecutionInfo, int numberOfThreads) {
    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    ThreadPoolExecutor executor =
        (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);

    final int loadSize = Integer.valueOf(System.getProperty("loadSize", "100000000"));
    int taskNum = 0;
    int taskEnd = 0;
    ArrayList<Future<Integer>> resultList = new ArrayList<Future<Integer>>();

    while (phaseExecutionInfo.continueExecution) {
      PhaseExecutionInfo subPhaseExecutionInfo =
          phaseExecutionInfo.createSubPhaseExecutionInfo(loadSize);
      ExecutionTask task =
          new ExecutionTask(subPhaseExecutionInfo, "TASK " + Integer.toString(taskNum));
      Future<Integer> result = executor.submit(task);
      resultList.add(result);
      taskNum++;
    }

    try {
      for (Future<Integer> future : resultList) {
        future.get().intValue();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    executor.shutdown();
  }

  public static void adaptiveParallelAdaptiveExecution(PhaseExecutionInfo phaseExecutionInfo) {
    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    final long loadSize = Integer.valueOf(System.getProperty("loadSize", "100000000"));
    int taskNum = 0;
    int taskEnd = 0;
    final int cores = Runtime.getRuntime().availableProcessors();
    final long parallelExplore = Integer.valueOf(System.getProperty("parallelExplore", "32"));
    final long parallelExploit = Integer.valueOf(System.getProperty("parallelExploit", "1000"));
    long startTime, endTime;
    long elapsedTime, shortestTime;
    int minIndex;

    startTime = System.nanoTime();
    PhaseExecutionInfo warmupPhaseExecutionInfo = phaseExecutionInfo.createSubPhaseExecutionInfo(0);
    parallelAdaptiveExecution(warmupPhaseExecutionInfo, 1);
    elapsedTime = System.nanoTime() - startTime;
    System.out.println("Warmup!");
    System.out.println(elapsedTime);

    while (phaseExecutionInfo.continueExecution) {
      shortestTime = Long.MAX_VALUE;
      minIndex = 0;
      for (int currCores = cores; currCores >= 1; currCores /= 2) {
        startTime = System.nanoTime();
        PhaseExecutionInfo subPhaseExecutionInfo =
            phaseExecutionInfo.createSubPhaseExecutionInfo(parallelExplore * loadSize);
        parallelAdaptiveExecution(subPhaseExecutionInfo, currCores);
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("# of cores: " + currCores);
        System.out.println(elapsedTime);
        if (elapsedTime < shortestTime) {
          minIndex = currCores;
          shortestTime = elapsedTime;
        } else {
          break;
        }
      }

      startTime = System.nanoTime();
      PhaseExecutionInfo subPhaseExecutionInfo =
          phaseExecutionInfo.createSubPhaseExecutionInfo(parallelExploit * loadSize);
      parallelAdaptiveExecution(subPhaseExecutionInfo, minIndex);
      elapsedTime = System.nanoTime() - startTime;
      System.out.println("Exploit # of cores: " + minIndex);
      System.out.println(elapsedTime + "\n\n");
    }
  }

  public static int[] findOptimalLoopOrderExecution(PhaseExecutionInfo phaseExecutionInfo) {
    System.out.println("Not implemented yet..");
    return null;
    /*
    if(!(phaseExecutionInfo instanceof CubePhaseExecutionInfo)) {
        System.out.println("Not using cube partitioning method.");
        return null;
    }
    final int numberOfVars = phaseExecutionInfo.phaseLoopEnds.length;
    CubePhaseExecutionInfo cubePhaseExecutionInfo = (CubePhaseExecutionInfo) phaseExecutionInfo;
    int[] possibleDirections = cubePhaseExecutionInfo.getPossibleDirections();
    Boolean debug = false;
    if(debug) {
        System.out.println("POSSIBLE DIRECTIONS");
        for(int i = 0; i < possibleDirections.length; i++) {
            System.out.println(possibleDirections[i]);
        }
    }
    if(possibleDirections.length == 1) {
        int[] direction = Utils.createOrderArray(possibleDirections, numberOfVars);
        cubePhaseExecutionInfo.direction = direction;
        //executeLoop(cubePhaseExecutionInfo);
        return possibleDirections;
    }
    else {
        long shortestTime = Long.MAX_VALUE;
        int[] bestOrder = null;
        for(int i = 0; i < possibleDirections.length; i++) {
            CubePhaseExecutionInfo slice = cubePhaseExecutionInfo.getSlice(possibleDirections[i], cubePhaseExecutionInfo.cubeSize);
            long startTime = System.nanoTime();
            long startExecutionUnits = slice.executed;
            int[] sliceBestOrder = findOptimalLoopOrderExecution(slice);
            long executed = slice.executed - startExecutionUnits;
            long elapsedTime = (System.nanoTime() - startTime) / executed;
            System.out.println("---");
            for(int j = 0; j < sliceBestOrder.length; j++) {
                System.out.println(sliceBestOrder[j]);
            }
            System.out.println("***");
            System.out.println(executed);
            System.out.println(elapsedTime);

            if(elapsedTime < shortestTime) {
                bestOrder = sliceBestOrder;
                shortestTime = elapsedTime;
            }
        }

        int[] partialOrder = new int[possibleDirections.length];
        for(int i = 0; i < possibleDirections.length; i++) {
            Boolean found = false;
            for(int j = 0; j < bestOrder.length; j++) {
                if(bestOrder[j] == possibleDirections[i]) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                partialOrder[0] = possibleDirections[i];
                break;
            }
        }
        for(int i = 0; i < bestOrder.length; i++) {
            partialOrder[i+1] = bestOrder[i];
        }

        System.out.println("BEST ORDER");
        for(int i = 0; i < partialOrder.length; i++) {
            System.out.println(partialOrder[i]);
        }

        int[] direction = Utils.createOrderArray(partialOrder, numberOfVars);
        cubePhaseExecutionInfo.direction = direction;
        //executeLoop(cubePhaseExecutionInfo);

        return partialOrder;
    }
    */
  }

  public static void executeLoop(PhaseExecutionInfo phaseExecutionInfo) {
    final int executionMode = Integer.valueOf(System.getProperty("executionMode", "1"));
    switch (executionMode) {
      case 4:
        Execution.adaptiveExecution(phaseExecutionInfo);
        break;
      case 2:
        final int numberOfThreads = Integer.valueOf(System.getProperty("numberOfThreads", "4"));
        Execution.parallelAdaptiveExecution(phaseExecutionInfo, numberOfThreads);
        break;
      case 3:
        Execution.adaptiveParallelAdaptiveExecution(phaseExecutionInfo);
        break;
    }
  }

  public static ThreadPoolExecutor gotoExecutor;

  public static void gotoAdaptiveExecution(
      PhaseExecutionInfo phaseExecutionInfo,
      int kernelHeight,
      int kernelWidth,
      int mLength,
      int kLength,
      int nLength) {
    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    final DirectCallNode plan = loopExecutionInfo.plans[0];
    final Object[] otherVarValues = phaseExecutionInfo.otherVarValues;

    // System.out.println("LENGTH");
    // System.out.println(mLength);
    // System.out.println(kLength);
    // System.out.println(nLength);

    final int executionMode = 5;
    final int threads = Integer.valueOf(System.getProperty("numberOfThreads", "1"));
    System.out.println("THREADS");
    System.out.println(threads);
    System.out.println(kernelHeight);
    System.out.println(kernelWidth);

    gotoExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);

    int[][][] loopValues;
    int[] incrSizes = new int[5];
    incrSizes[2] = kernelHeight;
    incrSizes[3] = kernelWidth;

    loopValues = new int[1][2][4];
    int[] loopStarts = new int[4];
    int[] loopEnds = new int[4];
    loopValues[0][0] = loopStarts;
    loopValues[0][1] = loopEnds;

    if (false) {

      setKC(1, incrSizes);
      setNC(1, incrSizes, loopEnds);
      loopStarts[0] = 64;
      loopEnds[0] = 65;
      loopStarts[1] = 64;
      loopEnds[1] = 65;
      loopStarts[2] = 64;
      loopEnds[2] = 65;
      gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);

      /*
      setKC(2048, incrSizes);
      setNC(2048, incrSizes, loopEnds);
      loopStarts[0] = 0;
      loopEnds[0] = kLength;
      loopStarts[1] = 0;
      loopEnds[1] = nLength;
      loopStarts[2] = 0;
      loopEnds[2] = mLength;
      gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);
      */

      gotoExecutor.shutdown();
      return;
    }

    long startTime, elapsedTime;
    float durationByWork;
    float min = 990000000;
    int bestKC = 0;

    setKC(kLength, incrSizes);
    setNC(kernelWidth * 2, incrSizes, loopEnds);

    final int exploreWidth = 2;

    loopStarts[0] = 0;
    loopEnds[0] = kLength;
    loopStarts[1] = 0;
    loopEnds[1] = kernelWidth * exploreWidth;
    loopStarts[2] = 0;
    loopEnds[2] = mLength;

    long explorationStartTime = System.nanoTime();
    long exploitationStartTime = -1;

    while (getKC(incrSizes) >= kernelWidth) {
      startTime = System.nanoTime();
      gotoDebugPrint(incrSizes, loopStarts, loopEnds);
      gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);
      elapsedTime = System.nanoTime() - startTime;
      durationByWork =
          ((float) elapsedTime) / ((loopEnds[2] - loopStarts[2]) * (loopEnds[0] - loopStarts[0]));
      System.out.println(getKC(incrSizes));
      System.out.println(durationByWork);
      if (durationByWork < min) {
        min = durationByWork;
        bestKC = getKC(incrSizes);
      }

      setKC(ceilDivision(getKC(incrSizes), 2), incrSizes);
      if (loopEnds[0] == kLength) {
        loopStarts[2] = 0;
        loopEnds[2] = mLength;
        loopStarts[1] = loopEnds[1];
        loopEnds[1] += kernelWidth * exploreWidth;
        loopStarts[0] = 0;
        loopEnds[0] = getKC(incrSizes);
      } else {
        loopStarts[0] = loopEnds[0];
        loopEnds[0] += getKC(incrSizes);
      }
    }
    System.out.println("BEST KC: " + bestKC);

    // Execute remaining leftover matrix
    loopEnds[0] = kLength;
    setKC(loopEnds[0] - loopStarts[0], incrSizes);
    gotoDebugPrint(incrSizes, loopStarts, loopEnds);
    gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);

    if (loopEnds[1] == nLength) {
      return;
    }

    setKC(bestKC, incrSizes);

    min = 990000000;
    int bestNC = 0;
    Boolean foundMin = false;
    loopStarts[0] = 0;
    loopEnds[0] = getKC(incrSizes);
    setNC(kernelWidth, incrSizes, loopEnds);
    loopStarts[1] = loopEnds[1];
    loopEnds[1] = loopStarts[1] + getNC(incrSizes);
    loopStarts[2] = 0;
    loopEnds[2] = mLength;

    while (true) {
      if (!foundMin) {
        startTime = System.nanoTime();
        gotoDebugPrint(incrSizes, loopStarts, loopEnds);
        gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);
        elapsedTime = System.nanoTime() - startTime;
        durationByWork = ((float) elapsedTime) / (loopEnds[1] - loopStarts[1]);
        System.out.println(getNC(incrSizes));
        System.out.println(durationByWork);
        if (durationByWork <= min) {
          min = durationByWork;
          bestNC = incrSizes[1];
          if (loopEnds[1] == nLength) {
            foundMin = true;
            System.out.println("BEST NC: " + bestNC);
            continue;
          }
        } else if (durationByWork > (min)) {
          foundMin = true;
          setNC(bestNC, incrSizes, loopEnds);
          System.out.println("BEST NC: " + bestNC);
          continue;
        }

        int nextNC =
            getNC(incrSizes) * 2 > nLength - kernelWidth * 4
                ? nLength - kernelWidth * 4
                : getNC(incrSizes) * 2;

        if (loopEnds[1] + nextNC > nLength) {
          loopStarts[1] = loopEnds[1];
          loopEnds[1] = nLength;
          setNC(nLength - loopStarts[1], incrSizes, loopEnds);
          gotoDebugPrint(incrSizes, loopStarts, loopEnds);
          gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);

          if (loopEnds[0] == kLength) { // Everything is executed
            return;
          } else {

            loopStarts[0] = loopEnds[0];
            loopEnds[0] =
                loopStarts[0] + getKC(incrSizes) > kLength
                    ? kLength
                    : loopStarts[0] + getKC(incrSizes);
            setNC(nextNC, incrSizes, loopEnds);
            loopStarts[1] = kernelWidth * 4;
            loopEnds[1] = loopStarts[1] + getNC(incrSizes);
          }

        } else {
          setNC(nextNC, incrSizes, loopEnds);
          loopStarts[1] = loopEnds[1];
          loopEnds[1] += getNC(incrSizes);
        }
      } else {
        System.out.println(
            "Exploration time: "
                + ((float) (System.nanoTime() - explorationStartTime) / 1000000000));
        exploitationStartTime = System.nanoTime();
        loopStarts[1] = loopEnds[1];
        loopEnds[1] = loopStarts[1] + ((nLength - loopStarts[1]) / bestNC) * bestNC;
        gotoDebugPrint(incrSizes, loopStarts, loopEnds);
        gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, threads);

        loopStarts[1] = loopEnds[1];
        loopEnds[1] = nLength;
        setNC(loopEnds[1] - loopStarts[1], incrSizes, loopEnds);
        gotoDebugPrint(incrSizes, loopStarts, loopEnds);
        gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);
        break;
      }
    }
    if (exploitationStartTime == -1) {
      exploitationStartTime = System.nanoTime();
    }
    setNC(bestNC, incrSizes, loopEnds);
    loopStarts[0] = loopEnds[0];
    loopEnds[0] = kLength;
    loopStarts[1] = kernelWidth * exploreWidth * 2;
    loopEnds[1] = loopStarts[1] + ((nLength - loopStarts[1]) / bestNC) * bestNC;
    gotoDebugPrint(incrSizes, loopStarts, loopEnds);
    gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, threads);

    loopStarts[1] = loopEnds[1];
    loopEnds[1] = nLength;
    setNC(loopEnds[1] - loopStarts[1], incrSizes, loopEnds);
    gotoDebugPrint(incrSizes, loopStarts, loopEnds);
    gotoPlanCall(plan, loopValues, otherVarValues, executionMode, incrSizes, 1);

    System.out.println(
        "Exploitation time: " + ((float) (System.nanoTime() - exploitationStartTime) / 1000000000));
    gotoExecutor.shutdown();
  }

  public static int getKC(int[] incrSizes) {
    return incrSizes[0];
  }

  public static int getNC(int[] incrSizes) {
    return incrSizes[1];
  }

  public static void setKC(int kc, int[] incrSizes) {
    incrSizes[0] = kc;
    incrSizes[4] = kc;
  }

  public static void setNC(int nc, int[] incrSizes, int[] loopEnds) {
    incrSizes[1] = nc;
    loopEnds[3] = nc;
  }

  public static int ceilDivision(int dividend, int divisor) {
    return (int) Math.ceil(((double) dividend / divisor));
  }

  public static void gotoDebugPrint(int[] incrSizes, int[] loopStarts, int[] loopEnds) {
    if (false) {
      System.out.println(Integer.toString(incrSizes[0]) + " " + Integer.toString(incrSizes[1]));
      System.out.println(
          Integer.toString(loopStarts[0])
              + " "
              + Integer.toString(loopEnds[0])
              + " "
              + Integer.toString(loopStarts[1])
              + " "
              + Integer.toString(loopEnds[1])
              + " "
              + Integer.toString(loopStarts[2])
              + " "
              + Integer.toString(loopEnds[2]));
    }
  }

  public static void gotoPlanCall(
      DirectCallNode plan,
      int[][][] loopValues,
      Object[] otherVarValues,
      int executionMode,
      int[] incrSizes,
      int threads) {
    if (threads == 1) {
      plan.call(loopValues, otherVarValues, executionMode, incrSizes);
      return;
    }
    int[][][][][] partitions = createPartitions(loopValues, incrSizes, threads);
    ArrayList<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
    // System.out.println(partitions.length * partitions[0].length);
    for (int i = 0; i < partitions.length; i++) {
      for (int j = 0; j < partitions[0].length; j++) {
        GotoExecutionTask task =
            new GotoExecutionTask(
                plan, partitions[i][j], otherVarValues, executionMode, incrSizes, "ID!");
        Future<Integer> result = gotoExecutor.submit(task);
        resultList.add(result);
      }
    }
    try {
      for (Future<Integer> future : resultList) {
        future.get().intValue();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static int[] getPartitionIndexes(int start, int end, int unit, int partitionNum) {
    int tickNum = ceilDivision(end - start, unit);
    int bigTickLength = Math.max(tickNum / partitionNum, 1);
    int[] ret = new int[partitionNum];
    for (int i = 0; i < partitionNum; i++) {
      if (i != partitionNum - 1) {
        ret[i] =
            (start + unit * bigTickLength * (i + 1) > end)
                ? end
                : start + unit * bigTickLength * (i + 1);
      } else {
        ret[i] = end;
      }
    }
    /*
    for(int index : ret) {
        System.out.println(index);

    }
    */
    return ret;
  }

  public static int[][][] loopValueCopy(int[][][] loopValues) {
    int[][][] ret = new int[1][2][4];
    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < 4; k++) {
          ret[i][j][k] = loopValues[i][j][k];
        }
      }
    }
    return ret;
  }

  public static int[] getPartitionDivides(int threads) {
    int[] ret = new int[2];
    for (int i = (int) Math.sqrt(threads); i > 0; i--) {
      if (threads % i == 0) {
        ret[1] = i;
        ret[0] = threads / i;
        break;
      }
    }
    return ret;
  }

  public static int[][][][][] createPartitions(int[][][] loopValues, int[] incrSizes, int threads) {
    int[] divides = getPartitionDivides(threads);
    // final int mPartitions = divides[0];
    // final int nPartitions = divides[1];
    final int mPartitions = 8;
    final int nPartitions = 2;
    int[][][][][] ret = new int[mPartitions][nPartitions][][][];

    final int kernelHeight = incrSizes[2];
    final int bestNC = getNC(incrSizes);

    int[] mIndexes =
        getPartitionIndexes(loopValues[0][0][2], loopValues[0][1][2], kernelHeight, mPartitions);
    int[] nIndexes =
        getPartitionIndexes(loopValues[0][0][1], loopValues[0][1][1], bestNC, nPartitions);

    for (int i = 0; i < mPartitions; i++) {
      for (int j = 0; j < nPartitions; j++) {
        ret[i][j] = loopValueCopy(loopValues);
        if (i != 0) {
          ret[i][j][0][0][2] = mIndexes[i - 1];
        }
        ret[i][j][0][1][2] = mIndexes[i];

        if (j != 0) {
          ret[i][j][0][0][1] = nIndexes[j - 1];
        }
        ret[i][j][0][1][1] = nIndexes[j];
      }
    }
    /*
    System.out.println("ADSFASDFA");
    for(int i = 0; i < mPartitions; i++) {
        for(int j = 0; j < nPartitions; j++) {
            gotoDebugPrint(incrSizes, ret[i][j][0][0], ret[i][j][0][1]);
        }
    }
    System.out.println("ADSFAadsfadsfSDFA");
    */
    return ret;
  }

  public static final class GotoExecutionTask implements Callable<Integer> {
    private final DirectCallNode plan;
    private int[][][] loopValues;
    private Object[] otherVarValues;
    private final int executionMode;
    private int[] incrSizes;
    private String id;

    public GotoExecutionTask(
        DirectCallNode plan,
        int[][][] loopValues,
        Object[] otherVarValues,
        final int executionMode,
        int[] incrSizes,
        String id) {
      this.plan = plan;
      this.loopValues = loopValues;
      this.otherVarValues = otherVarValues;
      this.executionMode = executionMode;
      this.incrSizes = incrSizes;
      this.id = id;
    }

    @Override
    public Integer call() throws Exception {
      plan.call(loopValues, otherVarValues, executionMode, incrSizes);
      return 1;
    }
  }
}
