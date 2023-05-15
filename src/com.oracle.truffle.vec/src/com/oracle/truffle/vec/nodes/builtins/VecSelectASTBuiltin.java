package com.oracle.truffle.vec.nodes.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.execution.Compilation;
import com.oracle.truffle.vec.execution.Execution;
import com.oracle.truffle.vec.execution.InterpreterException;
import com.oracle.truffle.vec.execution.LoopExecutionInfo;
import com.oracle.truffle.vec.execution.PhaseExecutionInfo;
import com.oracle.truffle.vec.nodes.VecBuiltinNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import com.oracle.truffle.vec.nodes.simd.VecGotoKernel8x8Node;
import com.oracle.truffle.vec.parser.ParseResult;
import com.oracle.truffle.vec.parser.VecTranslator;
import com.oracle.truffle.vec.runtime.VecContext;
import com.oracle.truffle.vec.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.graalvm.polyglot.*;

@NodeInfo(shortName = "selectAST")
public abstract class VecSelectASTBuiltin extends VecBuiltinNode {
  private final class ExecutionRootNode extends RootNode {
    @CompilationFinal(dimensions = 1)
    private final FrameSlot[] loopStartSlots;

    @CompilationFinal(dimensions = 1)
    private final FrameSlot[] loopEndSlots;

    @CompilationFinal(dimensions = 1)
    private final FrameSlot[] otherVarSlots;

    @CompilationFinal(dimensions = 1)
    private final FrameSlot[] incrSizeSlots;

    @Child VecStatementNode loopNode;

    protected ExecutionRootNode(
        VecStatementNode loopNode,
        FrameDescriptor frameDescriptor,
        FrameSlot[] loopStartSlots,
        FrameSlot[] loopEndSlots,
        FrameSlot[] otherVarSlots,
        FrameSlot[] incrSizeSlots) {
      super(null, frameDescriptor);
      this.loopNode = loopNode;
      this.loopStartSlots = loopStartSlots;
      this.loopEndSlots = loopEndSlots;
      this.otherVarSlots = otherVarSlots;
      this.incrSizeSlots = incrSizeSlots;
    }

    @ExplodeLoop
    void initializeOtherVars(VirtualFrame frame, Object[] otherVarValues) {
      for (int i = 0; i < otherVarSlots.length; i++) {
        if (otherVarValues[i] instanceof Integer) {
          getFrameDescriptor().setFrameSlotKind(otherVarSlots[i], FrameSlotKind.Int);
          frame.setInt(otherVarSlots[i], (Integer) otherVarValues[i]);
        } else if (otherVarValues[i] instanceof Double) {
          getFrameDescriptor().setFrameSlotKind(otherVarSlots[i], FrameSlotKind.Double);
          frame.setDouble(otherVarSlots[i], (Double) otherVarValues[i]);
        } else {
          CompilerDirectives.transferToInterpreterAndInvalidate();
        }
      }
    }

    @ExplodeLoop
    void initializeLoopVars(VirtualFrame frame, int[] loopStartValues, int[] loopEndValues) {
      for (int i = 0; i < loopStartSlots.length; i++) {
        frame.setInt(loopStartSlots[i], loopStartValues[i]);
        frame.setInt(loopEndSlots[i], loopEndValues[i]);
      }
    }

    @ExplodeLoop
    void initializeIncrSizes(VirtualFrame frame, int[] incrSizes) {
      for (int i = 0; i < incrSizeSlots.length; i++) {
        frame.setInt(incrSizeSlots[i], incrSizes[i]);
      }
    }

    @ExplodeLoop
    void readFromFrame(VirtualFrame frame, Object[] otherVarValues) {
      try {
        for (int i = 0; i < otherVarSlots.length; i++) {
          if (frame.isInt(otherVarSlots[i])) {
            otherVarValues[i] = frame.getInt(otherVarSlots[i]);
          } else if (frame.isDouble(otherVarSlots[i])) {
            otherVarValues[i] = frame.getDouble(otherVarSlots[i]);
          } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    @ExplodeLoop
    void iterationLoopInvocation(VirtualFrame frame, int[][][] loopValues) {
      for (int i = 0; i < 2 * loopStartSlots.length; i++) {
        if (i < loopValues.length) {
          initializeLoopVars(frame, loopValues[i][0], loopValues[i][1]);
          this.loopNode.executeVoid(frame);
        }
      }
    }

    // @ExplodeLoop
    @Override
    public Object execute(VirtualFrame frame) {
      Object[] args = frame.getArguments();
      final int[][][] loopValues = (int[][][]) args[0];
      final Object[] otherVarValues = (Object[]) args[1];
      final int executionMode = (int) args[2];
      // final int partitionType = (int) args[3];  //0 if iteration, 1 if cube
      initializeOtherVars(frame, otherVarValues);
      if (executionMode == 5) {
        // set increment sizes
        final int[] incrSizes = (int[]) args[3];
        initializeIncrSizes(frame, incrSizes);
      }
      try {
        iterationLoopInvocation(frame, loopValues);
        if (executionMode == 1 || executionMode == 4) {
          readFromFrame(frame, otherVarValues);
        }
      } catch (InterpreterException e) {
        if (executionMode == 1 || executionMode == 4) {
          readFromFrame(frame, otherVarValues);
        }
        throw e;
      }

      return null;
    }
  }

  public final class Variable {
    public final String name;
    public final FrameSlot frameSlot;
    public Object value;

    public Variable(String name, FrameSlot frameSlot, Object value) {
      this.name = name;
      this.frameSlot = frameSlot;
      this.value = value;
    }
  }

  @Specialization
  public Object select(VirtualFrame frame, @CachedLibrary(limit = "4") InteropLibrary interop) {
    long prepareStartTime = System.nanoTime();
    Object[] args = frame.getArguments();

    String srcString = (String) args[0];

    HashMap<String, Object> originalVarMap =
        (HashMap<String, Object>) VecContext.getEnv().asHostObject(args[1]);

    FrameDescriptor newFrameDescriptor = new FrameDescriptor();
    ParseResult parseResult =
        VecTranslator.strToNodes(srcString, originalVarMap, newFrameDescriptor);
    HashMap<String, FrameSlot> locals = parseResult.locals;

    HashMap<String, Object> varMap = new HashMap<String, Object>();
    for (Map.Entry<String, Object> set : originalVarMap.entrySet()) {
      if (locals.containsKey(set.getKey())) {
        varMap.put(set.getKey(), set.getValue());
      }
    }

    VirtualFrame newFrame = Truffle.getRuntime().createVirtualFrame(null, newFrameDescriptor);
    Utils.writeToStack(varMap, locals, newFrame);

    String[] compareOpStrings = new String[parseResult.compareOpStrings.size()];
    compareOpStrings = parseResult.compareOpStrings.toArray(compareOpStrings);

    final int[] loopStarts = new int[parseResult.loopStarts.size()];
    final int[] loopEnds = new int[parseResult.loopEnds.size()];
    final int[] incrementSizes = new int[parseResult.updates.size()];

    assert (loopStarts.length == loopEnds.length);
    assert (loopEnds.length == incrementSizes.length);

    try {
      for (int i = 0; i < loopStarts.length; i++) {
        loopStarts[i] = parseResult.loopStarts.get(i).executeInt(newFrame);
        loopEnds[i] = parseResult.loopEnds.get(i).executeInt(newFrame);
        incrementSizes[i] = parseResult.updates.get(i).executeInt(newFrame);
        if (parseResult.negates.get(i)) {
          incrementSizes[i] *= -1;
        }
      }
    } catch (UnexpectedResultException e) {
      System.out.println("Error when evaluating loop boundary expressions!");
    }

    LoopExecutionInfo loopExecutionInfo =
        new LoopExecutionInfo(loopStarts, loopEnds, incrementSizes, compareOpStrings);

    ArrayList<Variable> varList = new ArrayList<Variable>();
    for (Map.Entry<String, FrameSlot> set : locals.entrySet()) {
      if (originalVarMap.containsKey(set.getKey())) {
        varList.add(
            new Variable(set.getKey(), locals.get(set.getKey()), originalVarMap.get(set.getKey())));
      } else {
        varList.add(new Variable(set.getKey(), locals.get(set.getKey()), 0));
      }
    }

    FrameSlot[] loopVarSlots = new FrameSlot[parseResult.loopVariableStrs.size()];
    String[] loopVarNames = new String[parseResult.loopVariableStrs.size()];
    for (int i = 0; i < loopVarSlots.length; i++) {
      loopVarSlots[i] = locals.get(parseResult.loopVariableStrs.get(i));
      loopVarNames[i] = parseResult.loopVariableStrs.get(i);
      if (i == loopVarSlots.length - 1) {
        Execution.innermostLoopVarName = loopVarNames[i];
      }
    }

    ArrayList<FrameSlot> otherVarFrameSlotList = new ArrayList<FrameSlot>();
    ArrayList<Object> otherVarValList = new ArrayList<Object>();
    ArrayList<String> otherVarNameList = new ArrayList<String>();
    for (int i = 0; i < varList.size(); i++) {
      boolean found = false;
      for (String loopVarName : loopVarNames) {
        if (varList.get(i).name.equals(loopVarName)) {
          found = true;
        }
      }
      if (!found) {
        otherVarFrameSlotList.add(varList.get(i).frameSlot);
        otherVarValList.add(varList.get(i).value);
        otherVarNameList.add(varList.get(i).name);
      }
    }
    FrameSlot[] otherVarSlots = new FrameSlot[otherVarFrameSlotList.size()];
    otherVarSlots = otherVarFrameSlotList.toArray(otherVarSlots);
    Object[] otherVarValues = new Object[otherVarValList.size()];
    for (int i = 0; i < otherVarValList.size(); i++) {
      otherVarValues[i] = otherVarValList.get(i);
    }
    String[] otherVarNames = new String[otherVarNameList.size()];
    otherVarNames = otherVarNameList.toArray(otherVarNames);

    FrameSlot[] loopEndSlots = new FrameSlot[loopVarSlots.length];
    FrameSlot[] loopStartSlots = new FrameSlot[loopVarSlots.length];
    for (int i = 0; i < loopEndSlots.length; i++) {
      loopEndSlots[i] =
          newFrameDescriptor.findOrAddFrameSlot(
              "PHASE_END" + Integer.toString(i), FrameSlotKind.Int);
      loopStartSlots[i] =
          newFrameDescriptor.findOrAddFrameSlot(
              "PHASE_START" + Integer.toString(i), FrameSlotKind.Int);
    }

    int executionMode = Integer.valueOf(System.getProperty("executionMode", "1"));
    // Todo: Make declarative loop statements compatible with different execution modes
    if (parseResult.isDeclarativeLoop) {
      executionMode = 5;
    }
    final String[] planTypes =
        System.getProperty("planTypes", "LOGICAL_AND,BITWISE_AND,NO_BRANCH").split(",");
    final int numberOfPlans = planTypes.length;

    final CallTarget[] callTargets = new CallTarget[numberOfPlans];
    final DirectCallNode[] plans = new DirectCallNode[numberOfPlans];

    final int partitionType = Integer.valueOf(System.getProperty("partitionType", "1"));

    int[] blockDimensions = null;
    if (partitionType == 2) {
      blockDimensions = new int[loopVarSlots.length];
      for (int i = 0; i < loopVarSlots.length; i++) {
        blockDimensions[i] = Integer.valueOf(System.getProperty("cubeSize", "1"));
      }
    }

    int[] direction = new int[loopVarSlots.length];
    for (int i = 0; i < direction.length; i++) {
      direction[i] = i;
    }

    VecStatementNode planNode = null;
    RootNode blockRootNode = null;

    // Check if matrix multiplication-like task
    planNode =
        Utils.createGotoPlanNode(
            parseResult, newFrameDescriptor, loopExecutionInfo, originalVarMap);
    if (planNode != null) {
      FrameSlot[] gotoLoopStartSlots = new FrameSlot[4];
      FrameSlot[] gotoLoopEndSlots = new FrameSlot[4];
      FrameSlot[] gotoIncrSizeSlots = new FrameSlot[5];
      for (int index = 0; index < 4; index++) {
        gotoLoopStartSlots[index] =
            newFrameDescriptor.findOrAddFrameSlot(
                "PHASE_START" + Integer.toString(index), FrameSlotKind.Int);
        gotoLoopEndSlots[index] =
            newFrameDescriptor.findOrAddFrameSlot(
                "PHASE_END" + Integer.toString(index), FrameSlotKind.Int);
        gotoIncrSizeSlots[index] =
            newFrameDescriptor.findOrAddFrameSlot(
                "INCR_SIZE" + Integer.toString(index), FrameSlotKind.Int);
      }
      gotoIncrSizeSlots[4] =
          newFrameDescriptor.findOrAddFrameSlot("K_PANEL_SIZE", FrameSlotKind.Int);
      blockRootNode =
          new ExecutionRootNode(
              planNode,
              newFrameDescriptor,
              gotoLoopStartSlots,
              gotoLoopEndSlots,
              otherVarSlots,
              gotoIncrSizeSlots);
      CallTarget testCallTarget = Truffle.getRuntime().createCallTarget(blockRootNode);
      callTargets[0] = testCallTarget;
      plans[0] = Truffle.getRuntime().createDirectCallNode(testCallTarget);
      executionMode = 5;
    } else {
      for (int i = 0; i < numberOfPlans; i++) {
        planNode =
            Utils.createPlanNode(
                parseResult,
                planTypes[i],
                newFrameDescriptor,
                loopExecutionInfo,
                direction,
                blockDimensions);
        blockRootNode =
            new ExecutionRootNode(
                planNode, newFrameDescriptor, loopStartSlots, loopEndSlots, otherVarSlots, null);
        CallTarget testCallTarget = Truffle.getRuntime().createCallTarget(blockRootNode);
        callTargets[i] = testCallTarget;
        plans[i] = Truffle.getRuntime().createDirectCallNode(testCallTarget);
      }
    }

    Class<?> ctClass = callTargets[0].getClass();
    try {
      Compilation.compileMethod = ctClass.getMethod("compile", new Class<?>[] {boolean.class});
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    int[] phaseStartValues = new int[loopVarSlots.length];
    int[] phaseEndValues = new int[loopVarSlots.length];
    for (int i = 0; i < loopStarts.length; i++) {
      phaseStartValues[i] = loopStarts[i];
      phaseEndValues[i] = loopEnds[i];
    }

    loopExecutionInfo.plans = plans;
    loopExecutionInfo.callTargets = callTargets;

    // Call each plan so that all WHILE conditions are failed at least once, but the innermost body
    // statements are not executed.
    // This is to ensure that Graal sees all of the code, so it can compile all of the code later.
    if (executionMode != 5) {
      for (int i = 0; i < numberOfPlans; i++) {
        int[] dummyLoopStartValues = new int[loopEnds.length];
        int[] dummyLoopEndValues = new int[loopEnds.length];
        for (int j = 0; j < loopEnds.length - 1; j++) {
          dummyLoopStartValues[j] = phaseStartValues[j];
          dummyLoopEndValues[j] = phaseStartValues[j] + loopExecutionInfo.incrementSizes[j];
        }
        dummyLoopStartValues[loopEnds.length - 1] = phaseStartValues[loopEnds.length - 1];
        dummyLoopEndValues[loopEnds.length - 1] = phaseStartValues[loopEnds.length - 1];
        int[][][] dummyLoopValues = new int[1][2][loopEnds.length];
        dummyLoopValues[0][0] = dummyLoopStartValues;
        dummyLoopValues[0][1] = dummyLoopEndValues;
        plans[i].call(dummyLoopValues, otherVarValues, executionMode);
      }
    }
    /*
    System.out.println(VM.current().details());
    Object helperArray[] = new Object[1];
    helperArray[0] = phaseStartValues;
    Unsafe unsafe = getUnsafe();
    long addressOfObject = unsafe.getLong(helperArray, unsafe.arrayBaseOffset(Object[].class));
    System.out.println("asdfa");
    System.out.println(addressOfObject);
    */
    if (partitionType == 2) {
      for (int i = 0; i < loopEnds.length; i++) {
        loopExecutionInfo.incrementSizes[i] *= blockDimensions[i];
        Execution.warmupSize *= blockDimensions[i];
      }
    }
    PhaseExecutionInfo phaseExecutionInfo =
        new PhaseExecutionInfo(
            loopStarts, loopEnds, phaseStartValues, otherVarValues, loopExecutionInfo);

    // Compile plan ahead of time if adaptive Goto execution is used
    if (executionMode == 5) {
      int[][][] callLoopValues = new int[1][2][4];
      callLoopValues[0][0] = new int[] {0, 0, 0, 0};
      callLoopValues[0][1] = new int[] {1, 1, 1, 1};

      // Last element in testIncrSizes is kPanelSize to be passed to the computation kernel in
      // assembly
      // It is set to zero so that no actual computation is done during this execution (but it needs
      // to be executed for the code to be compiled)
      int[] testIncrSizes = new int[] {1, 1, 1, 1, 0};
      plans[0].call(callLoopValues, otherVarValues, executionMode, testIncrSizes);

      try {
        Compilation.compileMethod.invoke(callTargets[0], true);
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }

    if (executionMode == 6) {
      long iterations = Execution.warmupSize;
      final long numIterations =
          phaseExecutionInfo.executed + iterations < phaseExecutionInfo.totalIterations
              ? iterations
              : phaseExecutionInfo.totalIterations - phaseExecutionInfo.executed;
      phaseExecutionInfo.executed += numIterations;
      if (phaseExecutionInfo.executed >= phaseExecutionInfo.totalIterations) {
        phaseExecutionInfo.continueExecution = false;
      }
      final int numberOfVars = phaseExecutionInfo.phaseLoopStarts.length;
      int[] warmupLoopEndValues = new int[numberOfVars];
      phaseExecutionInfo.calculateNextStart(numIterations, warmupLoopEndValues);
      int[][][] callLoopValues = phaseExecutionInfo.createPhaseLoopEnds(warmupLoopEndValues);
      plans[0].call(callLoopValues, otherVarValues, executionMode);
      try {
        Compilation.compileMethod.invoke(callTargets[0], true);
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }

    long preprocessTime = System.nanoTime() - prepareStartTime;
    System.out.println("\nTotal Prepare time: " + ((float) preprocessTime / 1000000000));

    if (Integer.valueOf(System.getProperty("executionMode", "1")) == 6) {
      executionMode = 6;
    }

    // Adaptive Execution
    long adaptiveExecutionStartTime = System.nanoTime();
    switch (executionMode) {
      case 1:
        Execution.adaptiveExecution(phaseExecutionInfo);
        break;
      case 2:
        final int numberOfThreads = Integer.valueOf(System.getProperty("numberOfThreads", "4"));
        Execution.parallelAdaptiveExecution(phaseExecutionInfo, numberOfThreads);
        break;
      case 3:
        Execution.adaptiveParallelAdaptiveExecution(phaseExecutionInfo);
        break;
      case 4:
        System.out.println("Not implemented yet!");
        // Execution.findOptimalLoopOrderExecution(phaseExecutionInfo);
        break;
      case 5:
        Execution.gotoAdaptiveExecution(
            phaseExecutionInfo,
            VecGotoKernel8x8Node.kernelHeight,
            VecGotoKernel8x8Node.kernelWidth,
            VecGotoKernel8x8Node.mLength,
            VecGotoKernel8x8Node.kLength,
            VecGotoKernel8x8Node.nLength);
        break;
      case 6: // For debugging purposes
        Execution.adaptiveExecution(phaseExecutionInfo);
        break;
    }
    long adaptiveExecutionTime = System.nanoTime() - adaptiveExecutionStartTime;
    System.out.println("Loop Execution time: " + ((float) adaptiveExecutionTime / 1000000000));

    // Save variable values to original hash map so that they are accessible from JavaScript
    for (int i = 0; i < otherVarNames.length; i++) {
      if (originalVarMap.containsKey(otherVarNames[i])) {
        originalVarMap.put(otherVarNames[i], otherVarValues[i]);
      }
    }
    for (int i = 0; i < loopVarNames.length; i++) {
      if (originalVarMap.containsKey(loopVarNames[i])) {
        originalVarMap.put(loopVarNames[i], phaseExecutionInfo.phaseLoopStarts[i]);
      }
    }

    System.out.println(
        "Total time: " + ((float) (System.nanoTime() - prepareStartTime) / 1000000000));
    return true;
  }
}
