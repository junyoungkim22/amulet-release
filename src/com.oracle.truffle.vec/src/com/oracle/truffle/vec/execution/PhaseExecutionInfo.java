package com.oracle.truffle.vec.execution;

import com.oracle.truffle.vec.utils.Utils;
import java.util.ArrayList;

public class PhaseExecutionInfo {
  public int[] phaseLoopStarts;
  public int[] phaseLoopEnds;
  public int[] curr;
  public Object[] otherVarValues;
  public int[] order;

  public Boolean continueExecution; // condition to check whether to keep executing loop
  public LoopExecutionInfo loopExecutionInfo;

  public long totalIterations;
  public long executed;

  public long[] iterationsPerLoop;

  public PhaseExecutionInfo(
      int[] phaseLoopStarts,
      int[] phaseLoopEnds,
      int[] curr,
      Object[] otherVarValues,
      LoopExecutionInfo loopExecutionInfo) {
    this.phaseLoopStarts = phaseLoopStarts;
    this.phaseLoopEnds = phaseLoopEnds;
    this.curr = curr;
    this.otherVarValues = otherVarValues;
    this.loopExecutionInfo = loopExecutionInfo;

    this.continueExecution = true;
    this.executed = 0;
    this.order = new int[this.phaseLoopStarts.length];
    for (int i = 0; i < order.length; i++) {
      order[i] = i;
    }

    this.iterationsPerLoop = new long[this.phaseLoopStarts.length];
    updateIterationsPerLoop();

    this.totalIterations = iterationsPerLoop[order[0]] - getNumberOfIterations(this.curr);
  }

  public PhaseExecutionInfo createSubPhaseExecutionInfo(long iterations) {
    int[] newCurr = new int[phaseLoopStarts.length];
    for (int i = 0; i < phaseLoopStarts.length; i++) {
      newCurr[i] = curr[i];
    }
    long newIterations = totalIterations >= iterations ? iterations : totalIterations;
    this.totalIterations -= newIterations;
    if (totalIterations == 0) {
      continueExecution = false;
    }

    PhaseExecutionInfo ret =
        new PhaseExecutionInfo(
            phaseLoopStarts, phaseLoopEnds, newCurr, otherVarValues, loopExecutionInfo);
    ret.totalIterations = newIterations;
    calculateNextStart(newIterations, this.curr);
    return ret;
  }

  public void updateIterationsPerLoop() {
    for (int i = phaseLoopStarts.length - 1; i >= 0; i--) {
      int index = order[i];
      int loopSize = phaseLoopEnds[index] - phaseLoopStarts[index];
      if (loopExecutionInfo.compareOpStrings[index].equals("<=")
          || loopExecutionInfo.compareOpStrings[index].equals(">=")) {
        loopSize += 1;
      }
      iterationsPerLoop[index] =
          (int) Math.ceil((double) loopSize / (loopExecutionInfo.incrementSizes[index]));
      if (i < phaseLoopStarts.length - 1) {
        iterationsPerLoop[order[i]] *= iterationsPerLoop[order[i + 1]];
      }
    }
  }

  public long getNumberOfIterations(int[] loopVarValues) {
    long ret = 0;
    final int numberOfVars = this.phaseLoopStarts.length;
    for (int i = 0; i < numberOfVars - 1; i++) {
      int index = order[i];
      ret +=
          (((loopVarValues[index] - this.phaseLoopStarts[index])
                  / loopExecutionInfo.incrementSizes[index]))
              * this.iterationsPerLoop[order[i + 1]];
    }
    int innermostorderIndex = order[numberOfVars - 1];
    ret +=
        ((loopVarValues[innermostorderIndex] - this.phaseLoopStarts[innermostorderIndex])
            / this.loopExecutionInfo.incrementSizes[innermostorderIndex]);
    return ret;
  }

  public void calculateNextStart(long iterations, int[] resultArray) {
    final int numberOfVars = this.phaseLoopStarts.length;
    long currentIterations = getNumberOfIterations(this.curr);

    long targetIterations =
        currentIterations + iterations < iterationsPerLoop[order[0]]
            ? currentIterations + iterations
            : iterationsPerLoop[order[0]];

    for (int i = 0; i < numberOfVars - 1; i++) {
      int index = order[i];
      int numberOfIncrements = (int) (targetIterations / iterationsPerLoop[order[i + 1]]);
      resultArray[index] =
          this.phaseLoopStarts[index]
              + (numberOfIncrements * this.loopExecutionInfo.incrementSizes[index]);
      targetIterations -= numberOfIncrements * this.iterationsPerLoop[order[i + 1]];
    }
    int innermostorderIndex = order[numberOfVars - 1];
    resultArray[innermostorderIndex] =
        this.phaseLoopStarts[innermostorderIndex]
            + ((int) targetIterations) * loopExecutionInfo.incrementSizes[innermostorderIndex];
  }

  public int[][][] createPhaseLoopEnds(int[] loopEndValues) {
    final int numberOfVars = this.phaseLoopStarts.length;
    ArrayList<int[][]> invocationList = new ArrayList<int[][]>();

    // Find first index where curr[order[index]] < loopEndValues[order[index]]
    int resetIndex = 0;
    Boolean found = false;
    for (int i = 0; i < numberOfVars; i++) {
      int index = order[i];
      if (Utils.evaluateLoopCondition(
          curr[index], loopEndValues[index], loopExecutionInfo.compareOpStrings[index])) {
        found = true;
        resetIndex = i;
        break;
      }
    }

    if (!found) {
      return new int[0][2][numberOfVars];
    }

    Boolean done = false;

    for (int i = numberOfVars - 1; i >= resetIndex + 1; i--) {
      int[][] loopVars = new int[2][numberOfVars];
      Boolean updated = false;
      for (int j = 0; j < numberOfVars; j++) {
        loopVars[0][j] = curr[j];
        if (j < i) {
          loopVars[1][order[j]] = curr[order[j]] + loopExecutionInfo.incrementSizes[order[j]];
        } else {
          loopVars[1][order[j]] = phaseLoopEnds[order[j]];
        }
      }
      invocationList.add(loopVars);

      // Update current coordinates
      curr[order[i]] = phaseLoopStarts[order[i]];
      curr[order[i - 1]] = curr[order[i - 1]] + loopExecutionInfo.incrementSizes[order[i - 1]];
      for (int j = i - 1; j >= 0; j--) {
        int index = order[j];
        if (!Utils.evaluateLoopCondition(
            curr[index], phaseLoopEnds[index], loopExecutionInfo.compareOpStrings[index])) {
          if (j == 0) {
            done = true;
            break;
          } else {
            curr[index] = phaseLoopStarts[index];
            curr[order[j - 1]] =
                curr[order[j - 1]] + loopExecutionInfo.incrementSizes[order[j - 1]];
          }
        }
      }
      if (done) {
        break;
      }
      if (!Utils.evaluateLoopCondition(
          curr[order[resetIndex]],
          loopEndValues[order[resetIndex]],
          loopExecutionInfo.compareOpStrings[order[resetIndex]])) {
        break;
      }
    }

    if (getNumberOfIterations(curr) >= getNumberOfIterations(loopEndValues)) {
      done = true;
    }

    if (!done) {
      for (int i = 0; i < numberOfVars; i++) {
        int[][] loopVars = new int[2][numberOfVars];
        for (int j = 0; j < numberOfVars; j++) {
          int index = order[j];
          if (j < i) {
            loopVars[0][index] = loopEndValues[index];
            loopVars[1][index] = loopEndValues[index] + loopExecutionInfo.incrementSizes[index];
          } else {
            loopVars[0][index] = curr[index];
            if (j == i) {
              loopVars[1][index] = loopEndValues[index];
            } else {
              loopVars[1][index] = phaseLoopEnds[index];
            }
          }
        }
        invocationList.add(loopVars);
      }
    }

    int[][][] ret = new int[invocationList.size()][2][numberOfVars];

    for (int i = 0; i < invocationList.size(); i++) {
      ret[i] = invocationList.get(i);
    }

    for (int i = 0; i < numberOfVars; i++) {
      if (loopEndValues[i] == this.phaseLoopEnds[i]) {
        this.curr[i] = this.phaseLoopStarts[i];
      } else {
        this.curr[i] = loopEndValues[i];
      }
    }
    return ret;
  }
}
