package com.oracle.truffle.vec.parser.gotoParser;

public final class GotoOpCode {
  public static final int INDEXLENGTH = 5;

  public static final String OP = "00";
  public static final String MASKOP = "01";
  public static final String ARGOP = "10";
  public static final String CMPOP = "11";

  public static final String MUL = OP + "000";
  public static final String ADD = OP + "001";
  public static final String FMADD = OP + "010";
  public static final String SUB = OP + "011";
  public static final String DIV = OP + "100";
  public static final String LOAD = OP + "101";

  public static final String MASKMUL = MASKOP + "000";
  public static final String MASKADD = MASKOP + "001";
  public static final String MASKFMADD = MASKOP + "010";
  public static final String MASKSUB = MASKOP + "011";
  public static final String MASKDIV = MASKOP + "100";

  public static final String GT = CMPOP + "000";
  public static final String GE = CMPOP + "001";
  public static final String LT = CMPOP + "010";
  public static final String LE = CMPOP + "011";
  public static final String EQ = CMPOP + "100";
  public static final String NEQ = CMPOP + "101";

  public static final String A = ARGOP + "000";
  public static final String B = ARGOP + "001";
  public static final String C = ARGOP + "010";
  public static final String CONSTARG = ARGOP + "011";
  public static final String VARIABLEARG = ARGOP + "100";
  public static final String REG = ARGOP + "101";
  public static final String MASKREG = ARGOP + "110";
}
