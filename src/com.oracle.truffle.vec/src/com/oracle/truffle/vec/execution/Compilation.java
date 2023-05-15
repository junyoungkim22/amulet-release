package com.oracle.truffle.vec.execution;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.vec.parallelism.CompileTask;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Compilation {
  public static Method compileMethod;

  public static void compilation(
      CallTarget[] callTargets, long warmUpSize, PhaseExecutionInfo phaseExecutionInfo) {
    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    Method compileMethod = null;
    Class<?> ctClass = callTargets[0].getClass();
    try {
      compileMethod = ctClass.getMethod("compile", new Class<?>[] {boolean.class});
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    for (int planIndex = 0; planIndex < loopExecutionInfo.plans.length; planIndex++) {
      PhaseExecutionInfo subPhaseExecutionInfo =
          phaseExecutionInfo.createSubPhaseExecutionInfo(warmUpSize);
      compileSinglePlan(
          callTargets[planIndex], compileMethod, subPhaseExecutionInfo, planIndex, warmUpSize);
    }
  }

  public static void compileSinglePlan(
      CallTarget callTarget,
      Method compileMethod,
      PhaseExecutionInfo phaseExecutionInfo,
      int planIndex,
      long warmUpSize) {

    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    final DirectCallNode plan = loopExecutionInfo.plans[planIndex];
    final Object[] otherVarValues = phaseExecutionInfo.otherVarValues;
    final int executionMode = Integer.valueOf(System.getProperty("executionMode", "1"));

    Execution.callPlan(plan, phaseExecutionInfo, warmUpSize, otherVarValues, executionMode, null);
    try {
      compileMethod.invoke(callTarget, true);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void parallelCompilation(
      CallTarget[] callTargets, long warmUpSize, PhaseExecutionInfo phaseExecutionInfo) {
    final LoopExecutionInfo loopExecutionInfo = phaseExecutionInfo.loopExecutionInfo;
    Method compileMethod = null;
    Class<?> ctClass = callTargets[0].getClass();
    try {
      compileMethod = ctClass.getMethod("compile", new Class<?>[] {boolean.class});
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    ThreadPoolExecutor compileExecutor =
        (ThreadPoolExecutor) Executors.newFixedThreadPool(loopExecutionInfo.plans.length);
    ArrayList<Future<Integer>> compileResults = new ArrayList<Future<Integer>>();
    for (int planIndex = 0; planIndex < loopExecutionInfo.plans.length; planIndex++) {
      PhaseExecutionInfo subPhaseExecutionInfo =
          phaseExecutionInfo.createSubPhaseExecutionInfo(warmUpSize);
      CompileTask compileTask =
          new CompileTask(
              callTargets[planIndex],
              compileMethod,
              subPhaseExecutionInfo,
              planIndex,
              warmUpSize,
              "COMPILE THREAD " + planIndex);
      Future<Integer> compileResult = compileExecutor.submit(compileTask);
      compileResults.add(compileResult);
    }
    try {
      for (int i = 0; i < loopExecutionInfo.plans.length; i++) {
        compileResults.get(i).get();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    compileExecutor.shutdown();
  }
}
