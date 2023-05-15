package com.oracle.truffle.vec.nodes.controlflow;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;

public final class VecExecutionPlanNode extends VecStatementNode {
  @Children VecExpressionNode[] conditions;
  @Child VecStatementNode bodyStmts;
  private final String mode;
  private final FrameSlot maskSlot;

  public VecExecutionPlanNode(
      VecExpressionNode[] conditions, VecStatementNode bodyStmts, String mode, FrameSlot maskSlot) {
    this.conditions = conditions;
    /*
    this.conditions = new VecExpressionNode[conditions.length];
    for(int i = 0; i < conditions.length; i++) {
        this.conditions[i] = VecUnboxNodeGen.create(conditions[i]);
    }
    */
    this.bodyStmts = bodyStmts;
    this.mode = mode;
    this.maskSlot = maskSlot;
  }

  public VecExpressionNode[] getConditions() {
    return this.conditions;
  }

  public VecStatementNode getBodyStmts() {
    return this.bodyStmts;
  }

  public String getMode() {
    return this.mode;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    if (evaluateCondition(frame)) {
      this.bodyStmts.executeVoid(frame);
    }
    return;
  }

  @ExplodeLoop
  public boolean evaluateCondition(VirtualFrame frame) {
    // Todo: Implement exceptions that handle invalid cases
    CompilerAsserts.compilationConstant(this.mode);
    try {
      switch (this.mode) {
        case "LOGICAL_AND": // logical AND
          for (VecExpressionNode cond : conditions) {
            if (!cond.executeBoolean(frame)) {
              return false;
            }
          }
          return true;
        case "BITWISE_AND": // bitwise AND
          boolean result = true;
          for (VecExpressionNode cond : conditions) {
            result &= cond.executeBoolean(frame);
          }
          return result;
        case "NO_BRANCH": // no branch
          boolean test = true;
          for (VecExpressionNode cond : conditions) {
            test &= cond.executeBoolean(frame);
          }
          int mask = test ? -1 : 0;
          // FrameSlot frameSlot = frame.getFrameDescriptor().findOrAddFrameSlot("NOBRANCH_MASK",
          // FrameSlotKind.Illegal);
          frame.setInt(maskSlot, mask);
          return true;
        default:
          System.out.println("Invalid Execution Plan!");
          return false;
      }
    } catch (UnexpectedResultException ex) {
      System.out.println("Type Error in evaluating IF conditions!");
      return false;
    }
  }
}
