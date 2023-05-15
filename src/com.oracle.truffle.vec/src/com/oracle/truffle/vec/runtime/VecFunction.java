package com.oracle.truffle.vec.runtime;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public final class VecFunction extends VecObject {
  private RootCallTarget callTarget;

  protected VecFunction(RootCallTarget callTarget) {
    this.callTarget = callTarget;
  }

  @SuppressWarnings("static-method")
  @ExportMessage
  boolean isExecutable() {
    return true;
  }

  @ExportMessage
  abstract static class Execute {
    @Specialization
    protected static Object execute(VecFunction function, Object[] arguments) {
      return function.callTarget.call(arguments);
    }
  }
}
