package com.oracle.truffle.vec.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.nodes.Node;

public class VecException extends RuntimeException implements TruffleException {
  private static final long serialVersionUID = -8227190812998190001L;
  private final Node location;

  @TruffleBoundary
  public VecException(String message, Node location) {
    super(message);
    this.location = location;
  }

  @Override
  public Node getLocation() {
    return location;
  }
}
