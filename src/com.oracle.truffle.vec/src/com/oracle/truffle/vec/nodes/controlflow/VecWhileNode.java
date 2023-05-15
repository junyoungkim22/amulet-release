package com.oracle.truffle.vec.nodes.controlflow;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.execution.Execution;
import com.oracle.truffle.vec.execution.InterpreterException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class VecWhileNode extends VecStatementNode {
  // @Child private LoopNode loopNode;
  @Child private VecExpressionNode conditionNode;
  @Child private VecStatementNode bodyNode;

  private AtomicInteger iterationCount;
  private Boolean previouslyCompiled;
  public String loopVarName;

  public VecWhileNode(VecExpressionNode conditionNode, VecStatementNode bodyNode) {
    this.conditionNode = conditionNode;
    this.bodyNode = bodyNode;
    this.iterationCount = new AtomicInteger(0);
    this.previouslyCompiled = false;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    while (evaluateCondition(frame)) {
      try {
        bodyNode.executeVoid(frame);
      } catch (InterpreterException interpreterException) {
        int loopVarValue = 0;
        try {
          FrameSlot loopVarFrameSlot =
              frame.getFrameDescriptor().findOrAddFrameSlot(loopVarName, FrameSlotKind.Int);
          loopVarValue = frame.getInt(loopVarFrameSlot);
        } catch (Exception e) {
          System.out.println(e);
        }
        interpreterException.loopVarList.add(0, loopVarValue);
        throw interpreterException;
      }

      if (CompilerDirectives.inInterpreter() && loopVarName == Execution.innermostLoopVarName) {
        iterationCount.incrementAndGet();
        if (!previouslyCompiled) {
          if (iterationCount.get() < Execution.warmupSize) {
            continue;
          }
        }
        int loopVarValue = 0;
        try {
          FrameSlot loopVarFrameSlot =
              frame.getFrameDescriptor().findOrAddFrameSlot(loopVarName, FrameSlotKind.Int);
          loopVarValue = frame.getInt(loopVarFrameSlot);
        } catch (Exception e) {
          System.out.println(e);
        }
        InterpreterException toThrow = new InterpreterException();
        toThrow.loopVarList = new ArrayList<Integer>();
        toThrow.loopVarList.add(0, loopVarValue);
        previouslyCompiled = true;
        System.out.println("In interpreter!!");
        if (Integer.valueOf(System.getProperty("executionMode", "1")) != 6) {
          throw toThrow;
        }
      }
    }
  }

  private boolean evaluateCondition(VirtualFrame frame) {
    try {
      /*
       * The condition must evaluate to a boolean value, so we call the boolean-specialized
       * execute method.
       */
      return conditionNode.executeBoolean(frame);
    } catch (UnexpectedResultException ex) {
      /*
       * The condition evaluated to a non-boolean result. This is a type error in the SL
       * program. We report it with the same exception that Truffle DSL generated nodes use to
       * report type errors.
       */
      throw new UnsupportedSpecializationException(
          this, new Node[] {conditionNode}, ex.getResult());
    }
  }
}
