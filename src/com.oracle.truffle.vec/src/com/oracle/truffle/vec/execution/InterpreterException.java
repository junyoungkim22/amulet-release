package com.oracle.truffle.vec.execution;

import java.util.ArrayList;

public final class InterpreterException extends RuntimeException {
  private static final long serialVersionUID = 123;
  public ArrayList<Integer> loopVarList;
}
