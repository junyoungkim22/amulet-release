package com.oracle.truffle.vec.nodes.local;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

@NodeChild("valueNode")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class VecWriteLocalVariableNode extends VecExpressionNode {
  public abstract FrameSlot getSlot();

  public abstract VecExpressionNode getValueNode();
  // public abstract void setSlot(FrameSlot slot);

  @Specialization(guards = "isIntOrIllegal(frame)")
  protected int writeInt(VirtualFrame frame, int value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Int);
    frame.setInt(getSlot(), value);
    return value;
  }

  @Specialization(guards = "isBooleanOrIllegal(frame)")
  protected boolean writeBoolean(VirtualFrame frame, boolean value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Boolean);
    frame.setBoolean(getSlot(), value);
    return value;
  }

  @Specialization(guards = "isDoubleOrIllegal(frame)")
  protected double writeDouble(VirtualFrame frame, double value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Double);
    frame.setDouble(getSlot(), value);
    return value;
  }

  @Specialization(replaces = {"writeInt", "writeDouble", "writeBoolean"})
  protected Object write(VirtualFrame frame, Object value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Object);
    frame.setObject(getSlot(), value);
    return value;
  }

  protected boolean isIntOrIllegal(VirtualFrame frame) {
    final FrameSlotKind kind = frame.getFrameDescriptor().getFrameSlotKind(getSlot());
    return kind == FrameSlotKind.Int || kind == FrameSlotKind.Illegal;
  }

  protected boolean isBooleanOrIllegal(VirtualFrame frame) {
    final FrameSlotKind kind = frame.getFrameDescriptor().getFrameSlotKind(getSlot());
    return kind == FrameSlotKind.Boolean || kind == FrameSlotKind.Illegal;
  }

  protected boolean isDoubleOrIllegal(VirtualFrame frame) {
    final FrameSlotKind kind = frame.getFrameDescriptor().getFrameSlotKind(getSlot());
    return kind == FrameSlotKind.Double || kind == FrameSlotKind.Illegal;
  }
}
