package com.oracle.truffle.vec.nodes.controlflow;

import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;

public final class VecWhileRepeatingNode extends Node implements RepeatingNode {
  @Child private VecExpressionNode conditionNode;

  @Child private VecStatementNode bodyNode;

  public VecWhileRepeatingNode(VecExpressionNode conditionNode, VecStatementNode bodyNode) {
    this.conditionNode = conditionNode;
    this.bodyNode = bodyNode;
  }

  public void setConditionNode(VecExpressionNode conditionNode) {
    this.conditionNode = conditionNode;
  }

  @Override
  public boolean executeRepeating(VirtualFrame frame) {
    if (!evaluateCondition(frame)) {
      return false;
    }

    try {
      bodyNode.executeVoid(frame);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Error while executing body!");
      return false;
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
