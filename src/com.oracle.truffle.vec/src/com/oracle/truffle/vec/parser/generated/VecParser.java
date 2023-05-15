package com.oracle.truffle.parser.generated;
// Generated from Vec.g4 by ANTLR 4.7
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VecParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int UNARY_OP = 1,
      AND = 2,
      DECLAND = 3,
      SIMDDOUBLEFMADD = 4,
      MATMULKERNEL8X16 = 5,
      MATMULKERNEL2X8 = 6,
      GOTOKERNEL8X8 = 7,
      MATMULKERNEL1D2X8 = 8,
      CONVKERNEL = 9,
      IF = 10,
      FOR = 11,
      WHERE = 12,
      IN = 13,
      RANGE = 14,
      DEC = 15,
      LPAREN = 16,
      RPAREN = 17,
      LBRACE = 18,
      RBRACE = 19,
      LBRACKET = 20,
      RBRACKET = 21,
      COMMA = 22,
      LESSTHANOREQUAL = 23,
      GREATERTHANOREQUAL = 24,
      LESSTHAN = 25,
      GREATERTHAN = 26,
      ADDASSIGN = 27,
      SUBASSIGN = 28,
      MULASSIGN = 29,
      PLUS = 30,
      MINUS = 31,
      MULTIPLY = 32,
      MODULO = 33,
      ASSIGN = 34,
      SCOL = 35,
      INT = 36,
      ID = 37,
      STRING = 38,
      WS = 39;
  public static final int RULE_parse = 0,
      RULE_block = 1,
      RULE_stmt = 2,
      RULE_ifStmt = 3,
      RULE_forStmt = 4,
      RULE_assignment = 5,
      RULE_simdDoubleFmadd = 6,
      RULE_matmulKernel8x16 = 7,
      RULE_matmulKernel2x8 = 8,
      RULE_gotoKernel8x8 = 9,
      RULE_convKernel = 10,
      RULE_declarativeLoop = 11,
      RULE_matmulKernel1D2x8 = 12,
      RULE_expr = 13,
      RULE_var = 14,
      RULE_binop = 15,
      RULE_assignop = 16,
      RULE_atom = 17;
  public static final String[] ruleNames = {
    "parse",
    "block",
    "stmt",
    "ifStmt",
    "forStmt",
    "assignment",
    "simdDoubleFmadd",
    "matmulKernel8x16",
    "matmulKernel2x8",
    "gotoKernel8x8",
    "convKernel",
    "declarativeLoop",
    "matmulKernel1D2x8",
    "expr",
    "var",
    "binop",
    "assignop",
    "atom"
  };

  private static final String[] _LITERAL_NAMES = {
    null,
    null,
    "'&&'",
    "'and'",
    "'simdDoubleFmadd'",
    "'matmulKernel8x16'",
    "'matmulKernel2x8'",
    "'gotoKernel8x8'",
    "'matmulKernel1D2x8'",
    "'convKernel'",
    "'if'",
    "'for'",
    "'where'",
    "'in'",
    "'..'",
    null,
    "'('",
    "')'",
    "'{'",
    "'}'",
    "'['",
    "']'",
    "','",
    "'<='",
    "'>='",
    "'<'",
    "'>'",
    "'+='",
    "'-='",
    "'*='",
    "'+'",
    "'-'",
    "'*'",
    "'%'",
    "'='",
    "';'"
  };
  private static final String[] _SYMBOLIC_NAMES = {
    null,
    "UNARY_OP",
    "AND",
    "DECLAND",
    "SIMDDOUBLEFMADD",
    "MATMULKERNEL8X16",
    "MATMULKERNEL2X8",
    "GOTOKERNEL8X8",
    "MATMULKERNEL1D2X8",
    "CONVKERNEL",
    "IF",
    "FOR",
    "WHERE",
    "IN",
    "RANGE",
    "DEC",
    "LPAREN",
    "RPAREN",
    "LBRACE",
    "RBRACE",
    "LBRACKET",
    "RBRACKET",
    "COMMA",
    "LESSTHANOREQUAL",
    "GREATERTHANOREQUAL",
    "LESSTHAN",
    "GREATERTHAN",
    "ADDASSIGN",
    "SUBASSIGN",
    "MULASSIGN",
    "PLUS",
    "MINUS",
    "MULTIPLY",
    "MODULO",
    "ASSIGN",
    "SCOL",
    "INT",
    "ID",
    "STRING",
    "WS"
  };
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated public static final String[] tokenNames;

  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override
  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() {
    return "Vec.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public VecParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  public static class ParseContext extends ParserRuleContext {
    public ParseContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_parse;
    }

    public ParseContext() {}

    public void copyFrom(ParseContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class ForParseContext extends ParseContext {
    public ForStmtContext forStmt() {
      return getRuleContext(ForStmtContext.class, 0);
    }

    public TerminalNode EOF() {
      return getToken(VecParser.EOF, 0);
    }

    public TerminalNode WS() {
      return getToken(VecParser.WS, 0);
    }

    public ForParseContext(ParseContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterForParse(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitForParse(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitForParse(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class BlockParseContext extends ParseContext {
    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public TerminalNode EOF() {
      return getToken(VecParser.EOF, 0);
    }

    public TerminalNode WS() {
      return getToken(VecParser.WS, 0);
    }

    public BlockParseContext(ParseContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterBlockParse(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitBlockParse(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitBlockParse(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ParseContext parse() throws RecognitionException {
    ParseContext _localctx = new ParseContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_parse);
    int _la;
    try {
      setState(48);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
        case 1:
          _localctx = new BlockParseContext(_localctx);
          enterOuterAlt(_localctx, 1);
          {
            setState(36);
            block();
            setState(38);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(37);
                match(WS);
              }
            }

            setState(40);
            match(EOF);
          }
          break;
        case 2:
          _localctx = new ForParseContext(_localctx);
          enterOuterAlt(_localctx, 2);
          {
            setState(42);
            forStmt();
            setState(44);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(43);
                match(WS);
              }
            }

            setState(46);
            match(EOF);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class BlockContext extends ParserRuleContext {
    public List<StmtContext> stmt() {
      return getRuleContexts(StmtContext.class);
    }

    public StmtContext stmt(int i) {
      return getRuleContext(StmtContext.class, i);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public BlockContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_block;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterBlock(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitBlock(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitBlock(this);
      else return visitor.visitChildren(this);
    }
  }

  public final BlockContext block() throws RecognitionException {
    BlockContext _localctx = new BlockContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_block);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(56);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            {
              {
                setState(50);
                stmt();
                setState(52);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                  case 1:
                    {
                      setState(51);
                      match(WS);
                    }
                    break;
                }
              }
            }
          }
          setState(58);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class StmtContext extends ParserRuleContext {
    public StmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_stmt;
    }

    public StmtContext() {}

    public void copyFrom(StmtContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class MatmulKernel8x16StatementContext extends StmtContext {
    public MatmulKernel8x16Context matmulKernel8x16() {
      return getRuleContext(MatmulKernel8x16Context.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public MatmulKernel8x16StatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).enterMatmulKernel8x16Statement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).exitMatmulKernel8x16Statement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitMatmulKernel8x16Statement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class MatmulKernel1D2x8StatementContext extends StmtContext {
    public MatmulKernel1D2x8Context matmulKernel1D2x8() {
      return getRuleContext(MatmulKernel1D2x8Context.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public MatmulKernel1D2x8StatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).enterMatmulKernel1D2x8Statement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).exitMatmulKernel1D2x8Statement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitMatmulKernel1D2x8Statement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class GotoKernel8x8StatementContext extends StmtContext {
    public GotoKernel8x8Context gotoKernel8x8() {
      return getRuleContext(GotoKernel8x8Context.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public GotoKernel8x8StatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).enterGotoKernel8x8Statement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).exitGotoKernel8x8Statement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitGotoKernel8x8Statement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class DeclarativeLoopStatementContext extends StmtContext {
    public DeclarativeLoopContext declarativeLoop() {
      return getRuleContext(DeclarativeLoopContext.class, 0);
    }

    public DeclarativeLoopStatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).enterDeclarativeLoopStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).exitDeclarativeLoopStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitDeclarativeLoopStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class MatmulKernel2x8StatementContext extends StmtContext {
    public MatmulKernel2x8Context matmulKernel2x8() {
      return getRuleContext(MatmulKernel2x8Context.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public MatmulKernel2x8StatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).enterMatmulKernel2x8Statement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).exitMatmulKernel2x8Statement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitMatmulKernel2x8Statement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class SimdDoubleFmaddStatementContext extends StmtContext {
    public SimdDoubleFmaddContext simdDoubleFmadd() {
      return getRuleContext(SimdDoubleFmaddContext.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public SimdDoubleFmaddStatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).enterSimdDoubleFmaddStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener)
        ((VecListener) listener).exitSimdDoubleFmaddStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitSimdDoubleFmaddStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class AssignStatementContext extends StmtContext {
    public AssignmentContext assignment() {
      return getRuleContext(AssignmentContext.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public AssignStatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterAssignStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitAssignStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitAssignStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class ForStatementContext extends StmtContext {
    public ForStmtContext forStmt() {
      return getRuleContext(ForStmtContext.class, 0);
    }

    public ForStatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterForStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitForStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitForStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class ConvKernelStatementContext extends StmtContext {
    public ConvKernelContext convKernel() {
      return getRuleContext(ConvKernelContext.class, 0);
    }

    public TerminalNode SCOL() {
      return getToken(VecParser.SCOL, 0);
    }

    public ConvKernelStatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterConvKernelStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitConvKernelStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitConvKernelStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class IfStatementContext extends StmtContext {
    public IfStmtContext ifStmt() {
      return getRuleContext(IfStmtContext.class, 0);
    }

    public IfStatementContext(StmtContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterIfStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitIfStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitIfStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public final StmtContext stmt() throws RecognitionException {
    StmtContext _localctx = new StmtContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_stmt);
    try {
      setState(83);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
        case 1:
          _localctx = new AssignStatementContext(_localctx);
          enterOuterAlt(_localctx, 1);
          {
            setState(59);
            assignment();
            setState(60);
            match(SCOL);
          }
          break;
        case 2:
          _localctx = new IfStatementContext(_localctx);
          enterOuterAlt(_localctx, 2);
          {
            setState(62);
            ifStmt();
          }
          break;
        case 3:
          _localctx = new ForStatementContext(_localctx);
          enterOuterAlt(_localctx, 3);
          {
            setState(63);
            forStmt();
          }
          break;
        case 4:
          _localctx = new SimdDoubleFmaddStatementContext(_localctx);
          enterOuterAlt(_localctx, 4);
          {
            setState(64);
            simdDoubleFmadd();
            setState(65);
            match(SCOL);
          }
          break;
        case 5:
          _localctx = new MatmulKernel8x16StatementContext(_localctx);
          enterOuterAlt(_localctx, 5);
          {
            setState(67);
            matmulKernel8x16();
            setState(68);
            match(SCOL);
          }
          break;
        case 6:
          _localctx = new MatmulKernel2x8StatementContext(_localctx);
          enterOuterAlt(_localctx, 6);
          {
            setState(70);
            matmulKernel2x8();
            setState(71);
            match(SCOL);
          }
          break;
        case 7:
          _localctx = new MatmulKernel1D2x8StatementContext(_localctx);
          enterOuterAlt(_localctx, 7);
          {
            setState(73);
            matmulKernel1D2x8();
            setState(74);
            match(SCOL);
          }
          break;
        case 8:
          _localctx = new GotoKernel8x8StatementContext(_localctx);
          enterOuterAlt(_localctx, 8);
          {
            setState(76);
            gotoKernel8x8();
            setState(77);
            match(SCOL);
          }
          break;
        case 9:
          _localctx = new ConvKernelStatementContext(_localctx);
          enterOuterAlt(_localctx, 9);
          {
            setState(79);
            convKernel();
            setState(80);
            match(SCOL);
          }
          break;
        case 10:
          _localctx = new DeclarativeLoopStatementContext(_localctx);
          enterOuterAlt(_localctx, 10);
          {
            setState(82);
            declarativeLoop();
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class IfStmtContext extends ParserRuleContext {
    public TerminalNode IF() {
      return getToken(VecParser.IF, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public TerminalNode LBRACE() {
      return getToken(VecParser.LBRACE, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public TerminalNode RBRACE() {
      return getToken(VecParser.RBRACE, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public IfStmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_ifStmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterIfStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitIfStmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitIfStmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final IfStmtContext ifStmt() throws RecognitionException {
    IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_ifStmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(86);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(85);
            match(WS);
          }
        }

        setState(88);
        match(IF);
        setState(90);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(89);
            match(WS);
          }
        }

        setState(92);
        match(LPAREN);
        setState(94);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(93);
            match(WS);
          }
        }

        setState(96);
        expr(0);
        setState(98);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(97);
            match(WS);
          }
        }

        setState(100);
        match(RPAREN);
        setState(102);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(101);
            match(WS);
          }
        }

        setState(104);
        match(LBRACE);
        setState(106);
        _errHandler.sync(this);
        switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
          case 1:
            {
              setState(105);
              match(WS);
            }
            break;
        }
        setState(108);
        block();
        setState(109);
        match(RBRACE);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ForStmtContext extends ParserRuleContext {
    public TerminalNode FOR() {
      return getToken(VecParser.FOR, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public List<AssignmentContext> assignment() {
      return getRuleContexts(AssignmentContext.class);
    }

    public AssignmentContext assignment(int i) {
      return getRuleContext(AssignmentContext.class, i);
    }

    public List<TerminalNode> SCOL() {
      return getTokens(VecParser.SCOL);
    }

    public TerminalNode SCOL(int i) {
      return getToken(VecParser.SCOL, i);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public TerminalNode LBRACE() {
      return getToken(VecParser.LBRACE, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public TerminalNode RBRACE() {
      return getToken(VecParser.RBRACE, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public ForStmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_forStmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterForStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitForStmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitForStmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ForStmtContext forStmt() throws RecognitionException {
    ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_forStmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(112);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(111);
            match(WS);
          }
        }

        setState(114);
        match(FOR);
        setState(116);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(115);
            match(WS);
          }
        }

        setState(118);
        match(LPAREN);
        setState(120);
        _errHandler.sync(this);
        switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
          case 1:
            {
              setState(119);
              match(WS);
            }
            break;
        }
        setState(122);
        assignment();
        setState(124);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(123);
            match(WS);
          }
        }

        setState(126);
        match(SCOL);
        setState(128);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(127);
            match(WS);
          }
        }

        setState(130);
        expr(0);
        setState(132);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(131);
            match(WS);
          }
        }

        setState(134);
        match(SCOL);
        setState(135);
        assignment();
        setState(137);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(136);
            match(WS);
          }
        }

        setState(139);
        match(RPAREN);
        setState(141);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(140);
            match(WS);
          }
        }

        setState(143);
        match(LBRACE);
        setState(144);
        block();
        setState(146);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(145);
            match(WS);
          }
        }

        setState(148);
        match(RBRACE);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class AssignmentContext extends ParserRuleContext {
    public AssignmentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_assignment;
    }

    public AssignmentContext() {}

    public void copyFrom(AssignmentContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class EqualAssnContext extends AssignmentContext {
    public VarContext var() {
      return getRuleContext(VarContext.class, 0);
    }

    public AssignopContext assignop() {
      return getRuleContext(AssignopContext.class, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public TerminalNode DEC() {
      return getToken(VecParser.DEC, 0);
    }

    public EqualAssnContext(AssignmentContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterEqualAssn(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitEqualAssn(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitEqualAssn(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class UnaryAssnBackContext extends AssignmentContext {
    public VarContext var() {
      return getRuleContext(VarContext.class, 0);
    }

    public TerminalNode UNARY_OP() {
      return getToken(VecParser.UNARY_OP, 0);
    }

    public TerminalNode WS() {
      return getToken(VecParser.WS, 0);
    }

    public UnaryAssnBackContext(AssignmentContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterUnaryAssnBack(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitUnaryAssnBack(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitUnaryAssnBack(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class UnaryAssnFrontContext extends AssignmentContext {
    public TerminalNode UNARY_OP() {
      return getToken(VecParser.UNARY_OP, 0);
    }

    public VarContext var() {
      return getRuleContext(VarContext.class, 0);
    }

    public TerminalNode WS() {
      return getToken(VecParser.WS, 0);
    }

    public UnaryAssnFrontContext(AssignmentContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterUnaryAssnFront(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitUnaryAssnFront(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitUnaryAssnFront(this);
      else return visitor.visitChildren(this);
    }
  }

  public final AssignmentContext assignment() throws RecognitionException {
    AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_assignment);
    int _la;
    try {
      setState(177);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
        case 1:
          _localctx = new EqualAssnContext(_localctx);
          enterOuterAlt(_localctx, 1);
          {
            setState(151);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(150);
                match(WS);
              }
            }

            setState(154);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == DEC) {
              {
                setState(153);
                match(DEC);
              }
            }

            setState(156);
            var();
            setState(158);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(157);
                match(WS);
              }
            }

            setState(160);
            assignop();
            setState(162);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(161);
                match(WS);
              }
            }

            setState(164);
            expr(0);
          }
          break;
        case 2:
          _localctx = new UnaryAssnFrontContext(_localctx);
          enterOuterAlt(_localctx, 2);
          {
            setState(167);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(166);
                match(WS);
              }
            }

            setState(169);
            match(UNARY_OP);
            setState(170);
            var();
          }
          break;
        case 3:
          _localctx = new UnaryAssnBackContext(_localctx);
          enterOuterAlt(_localctx, 3);
          {
            setState(172);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == WS) {
              {
                setState(171);
                match(WS);
              }
            }

            setState(174);
            var();
            setState(175);
            match(UNARY_OP);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class SimdDoubleFmaddContext extends ParserRuleContext {
    public TerminalNode SIMDDOUBLEFMADD() {
      return getToken(VecParser.SIMDDOUBLEFMADD, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(VecParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(VecParser.COMMA, i);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public SimdDoubleFmaddContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_simdDoubleFmadd;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterSimdDoubleFmadd(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitSimdDoubleFmadd(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitSimdDoubleFmadd(this);
      else return visitor.visitChildren(this);
    }
  }

  public final SimdDoubleFmaddContext simdDoubleFmadd() throws RecognitionException {
    SimdDoubleFmaddContext _localctx = new SimdDoubleFmaddContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_simdDoubleFmadd);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(180);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(179);
            match(WS);
          }
        }

        setState(182);
        match(SIMDDOUBLEFMADD);
        setState(183);
        match(LPAREN);
        setState(185);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(184);
            match(WS);
          }
        }

        setState(187);
        expr(0);
        setState(189);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(188);
            match(WS);
          }
        }

        setState(191);
        match(COMMA);
        setState(193);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(192);
            match(WS);
          }
        }

        setState(195);
        expr(0);
        setState(197);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(196);
            match(WS);
          }
        }

        setState(199);
        match(COMMA);
        setState(201);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(200);
            match(WS);
          }
        }

        setState(203);
        expr(0);
        setState(205);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(204);
            match(WS);
          }
        }

        setState(207);
        match(RPAREN);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class MatmulKernel8x16Context extends ParserRuleContext {
    public TerminalNode MATMULKERNEL8X16() {
      return getToken(VecParser.MATMULKERNEL8X16, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(VecParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(VecParser.COMMA, i);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public MatmulKernel8x16Context(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_matmulKernel8x16;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterMatmulKernel8x16(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitMatmulKernel8x16(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitMatmulKernel8x16(this);
      else return visitor.visitChildren(this);
    }
  }

  public final MatmulKernel8x16Context matmulKernel8x16() throws RecognitionException {
    MatmulKernel8x16Context _localctx = new MatmulKernel8x16Context(_ctx, getState());
    enterRule(_localctx, 14, RULE_matmulKernel8x16);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(210);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(209);
            match(WS);
          }
        }

        setState(212);
        match(MATMULKERNEL8X16);
        setState(213);
        match(LPAREN);
        setState(215);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(214);
            match(WS);
          }
        }

        setState(217);
        expr(0);
        setState(219);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(218);
            match(WS);
          }
        }

        setState(221);
        match(COMMA);
        setState(223);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(222);
            match(WS);
          }
        }

        setState(225);
        expr(0);
        setState(227);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(226);
            match(WS);
          }
        }

        setState(229);
        match(COMMA);
        setState(231);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(230);
            match(WS);
          }
        }

        setState(233);
        expr(0);
        setState(235);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(234);
            match(WS);
          }
        }

        setState(237);
        match(COMMA);
        setState(239);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(238);
            match(WS);
          }
        }

        setState(241);
        expr(0);
        setState(243);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(242);
            match(WS);
          }
        }

        setState(245);
        match(COMMA);
        setState(247);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(246);
            match(WS);
          }
        }

        setState(249);
        expr(0);
        setState(251);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(250);
            match(WS);
          }
        }

        setState(253);
        match(COMMA);
        setState(255);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(254);
            match(WS);
          }
        }

        setState(257);
        expr(0);
        setState(259);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(258);
            match(WS);
          }
        }

        setState(261);
        match(COMMA);
        setState(263);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(262);
            match(WS);
          }
        }

        setState(265);
        expr(0);
        setState(267);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(266);
            match(WS);
          }
        }

        setState(269);
        match(RPAREN);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class MatmulKernel2x8Context extends ParserRuleContext {
    public TerminalNode MATMULKERNEL2X8() {
      return getToken(VecParser.MATMULKERNEL2X8, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(VecParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(VecParser.COMMA, i);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public MatmulKernel2x8Context(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_matmulKernel2x8;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterMatmulKernel2x8(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitMatmulKernel2x8(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitMatmulKernel2x8(this);
      else return visitor.visitChildren(this);
    }
  }

  public final MatmulKernel2x8Context matmulKernel2x8() throws RecognitionException {
    MatmulKernel2x8Context _localctx = new MatmulKernel2x8Context(_ctx, getState());
    enterRule(_localctx, 16, RULE_matmulKernel2x8);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(272);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(271);
            match(WS);
          }
        }

        setState(274);
        match(MATMULKERNEL2X8);
        setState(275);
        match(LPAREN);
        setState(277);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(276);
            match(WS);
          }
        }

        setState(279);
        expr(0);
        setState(281);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(280);
            match(WS);
          }
        }

        setState(283);
        match(COMMA);
        setState(285);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(284);
            match(WS);
          }
        }

        setState(287);
        expr(0);
        setState(289);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(288);
            match(WS);
          }
        }

        setState(291);
        match(COMMA);
        setState(293);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(292);
            match(WS);
          }
        }

        setState(295);
        expr(0);
        setState(297);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(296);
            match(WS);
          }
        }

        setState(299);
        match(COMMA);
        setState(301);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(300);
            match(WS);
          }
        }

        setState(303);
        expr(0);
        setState(305);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(304);
            match(WS);
          }
        }

        setState(307);
        match(COMMA);
        setState(309);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(308);
            match(WS);
          }
        }

        setState(311);
        expr(0);
        setState(313);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(312);
            match(WS);
          }
        }

        setState(315);
        match(COMMA);
        setState(317);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(316);
            match(WS);
          }
        }

        setState(319);
        expr(0);
        setState(321);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(320);
            match(WS);
          }
        }

        setState(323);
        match(COMMA);
        setState(325);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(324);
            match(WS);
          }
        }

        setState(327);
        expr(0);
        setState(329);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(328);
            match(WS);
          }
        }

        setState(331);
        match(RPAREN);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class GotoKernel8x8Context extends ParserRuleContext {
    public TerminalNode GOTOKERNEL8X8() {
      return getToken(VecParser.GOTOKERNEL8X8, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public TerminalNode STRING() {
      return getToken(VecParser.STRING, 0);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(VecParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(VecParser.COMMA, i);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public GotoKernel8x8Context(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_gotoKernel8x8;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterGotoKernel8x8(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitGotoKernel8x8(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitGotoKernel8x8(this);
      else return visitor.visitChildren(this);
    }
  }

  public final GotoKernel8x8Context gotoKernel8x8() throws RecognitionException {
    GotoKernel8x8Context _localctx = new GotoKernel8x8Context(_ctx, getState());
    enterRule(_localctx, 18, RULE_gotoKernel8x8);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(334);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(333);
            match(WS);
          }
        }

        setState(336);
        match(GOTOKERNEL8X8);
        setState(337);
        match(LPAREN);
        setState(339);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(338);
            match(WS);
          }
        }

        setState(341);
        match(STRING);
        setState(343);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(342);
            match(WS);
          }
        }

        setState(355);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == COMMA) {
          {
            {
              setState(345);
              match(COMMA);
              setState(347);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(346);
                  match(WS);
                }
              }

              setState(349);
              expr(0);
              setState(351);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(350);
                  match(WS);
                }
              }
            }
          }
          setState(357);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(358);
        match(RPAREN);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ConvKernelContext extends ParserRuleContext {
    public TerminalNode CONVKERNEL() {
      return getToken(VecParser.CONVKERNEL, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public TerminalNode STRING() {
      return getToken(VecParser.STRING, 0);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(VecParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(VecParser.COMMA, i);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ConvKernelContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_convKernel;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterConvKernel(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitConvKernel(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitConvKernel(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ConvKernelContext convKernel() throws RecognitionException {
    ConvKernelContext _localctx = new ConvKernelContext(_ctx, getState());
    enterRule(_localctx, 20, RULE_convKernel);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(361);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(360);
            match(WS);
          }
        }

        setState(363);
        match(CONVKERNEL);
        setState(364);
        match(LPAREN);
        setState(366);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(365);
            match(WS);
          }
        }

        setState(368);
        match(STRING);
        setState(370);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(369);
            match(WS);
          }
        }

        setState(382);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == COMMA) {
          {
            {
              setState(372);
              match(COMMA);
              setState(374);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(373);
                  match(WS);
                }
              }

              setState(376);
              expr(0);
              setState(378);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(377);
                  match(WS);
                }
              }
            }
          }
          setState(384);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(385);
        match(RPAREN);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class DeclarativeLoopContext extends ParserRuleContext {
    public TerminalNode WHERE() {
      return getToken(VecParser.WHERE, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public List<VarContext> var() {
      return getRuleContexts(VarContext.class);
    }

    public VarContext var(int i) {
      return getRuleContext(VarContext.class, i);
    }

    public List<TerminalNode> IN() {
      return getTokens(VecParser.IN);
    }

    public TerminalNode IN(int i) {
      return getToken(VecParser.IN, i);
    }

    public List<TerminalNode> LBRACKET() {
      return getTokens(VecParser.LBRACKET);
    }

    public TerminalNode LBRACKET(int i) {
      return getToken(VecParser.LBRACKET, i);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> RANGE() {
      return getTokens(VecParser.RANGE);
    }

    public TerminalNode RANGE(int i) {
      return getToken(VecParser.RANGE, i);
    }

    public List<TerminalNode> RBRACKET() {
      return getTokens(VecParser.RBRACKET);
    }

    public TerminalNode RBRACKET(int i) {
      return getToken(VecParser.RBRACKET, i);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public TerminalNode LBRACE() {
      return getToken(VecParser.LBRACE, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public TerminalNode RBRACE() {
      return getToken(VecParser.RBRACE, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public List<TerminalNode> DECLAND() {
      return getTokens(VecParser.DECLAND);
    }

    public TerminalNode DECLAND(int i) {
      return getToken(VecParser.DECLAND, i);
    }

    public DeclarativeLoopContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_declarativeLoop;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterDeclarativeLoop(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitDeclarativeLoop(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitDeclarativeLoop(this);
      else return visitor.visitChildren(this);
    }
  }

  public final DeclarativeLoopContext declarativeLoop() throws RecognitionException {
    DeclarativeLoopContext _localctx = new DeclarativeLoopContext(_ctx, getState());
    enterRule(_localctx, 22, RULE_declarativeLoop);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(388);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(387);
            match(WS);
          }
        }

        setState(390);
        match(WHERE);
        setState(392);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(391);
            match(WS);
          }
        }

        setState(394);
        match(LPAREN);
        setState(396);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(395);
            match(WS);
          }
        }

        setState(398);
        var();
        setState(400);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(399);
            match(WS);
          }
        }

        setState(402);
        match(IN);
        setState(404);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(403);
            match(WS);
          }
        }

        setState(406);
        match(LBRACKET);
        setState(408);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(407);
            match(WS);
          }
        }

        setState(410);
        expr(0);
        setState(412);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(411);
            match(WS);
          }
        }

        setState(414);
        match(RANGE);
        setState(416);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(415);
            match(WS);
          }
        }

        setState(418);
        expr(0);
        setState(420);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(419);
            match(WS);
          }
        }

        setState(422);
        match(RBRACKET);
        setState(460);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == DECLAND || _la == WS) {
          {
            {
              setState(424);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(423);
                  match(WS);
                }
              }

              setState(426);
              match(DECLAND);
              setState(428);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(427);
                  match(WS);
                }
              }

              setState(430);
              var();
              setState(432);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(431);
                  match(WS);
                }
              }

              setState(434);
              match(IN);
              setState(436);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(435);
                  match(WS);
                }
              }

              setState(438);
              match(LBRACKET);
              setState(440);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(439);
                  match(WS);
                }
              }

              setState(442);
              expr(0);
              setState(444);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(443);
                  match(WS);
                }
              }

              setState(446);
              match(RANGE);
              setState(448);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(447);
                  match(WS);
                }
              }

              setState(450);
              expr(0);
              setState(452);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if (_la == WS) {
                {
                  setState(451);
                  match(WS);
                }
              }

              setState(454);
              match(RBRACKET);
              setState(456);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 94, _ctx)) {
                case 1:
                  {
                    setState(455);
                    match(WS);
                  }
                  break;
              }
            }
          }
          setState(462);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(463);
        match(RPAREN);
        setState(465);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(464);
            match(WS);
          }
        }

        setState(467);
        match(LBRACE);
        setState(468);
        block();
        setState(470);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(469);
            match(WS);
          }
        }

        setState(472);
        match(RBRACE);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class MatmulKernel1D2x8Context extends ParserRuleContext {
    public TerminalNode MATMULKERNEL1D2X8() {
      return getToken(VecParser.MATMULKERNEL1D2X8, 0);
    }

    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(VecParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(VecParser.COMMA, i);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public MatmulKernel1D2x8Context(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_matmulKernel1D2x8;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterMatmulKernel1D2x8(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitMatmulKernel1D2x8(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitMatmulKernel1D2x8(this);
      else return visitor.visitChildren(this);
    }
  }

  public final MatmulKernel1D2x8Context matmulKernel1D2x8() throws RecognitionException {
    MatmulKernel1D2x8Context _localctx = new MatmulKernel1D2x8Context(_ctx, getState());
    enterRule(_localctx, 24, RULE_matmulKernel1D2x8);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(475);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(474);
            match(WS);
          }
        }

        setState(477);
        match(MATMULKERNEL1D2X8);
        setState(478);
        match(LPAREN);
        setState(480);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(479);
            match(WS);
          }
        }

        setState(482);
        expr(0);
        setState(484);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(483);
            match(WS);
          }
        }

        setState(486);
        match(COMMA);
        setState(488);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(487);
            match(WS);
          }
        }

        setState(490);
        expr(0);
        setState(492);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(491);
            match(WS);
          }
        }

        setState(494);
        match(COMMA);
        setState(496);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(495);
            match(WS);
          }
        }

        setState(498);
        expr(0);
        setState(500);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(499);
            match(WS);
          }
        }

        setState(502);
        match(COMMA);
        setState(504);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(503);
            match(WS);
          }
        }

        setState(506);
        expr(0);
        setState(508);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(507);
            match(WS);
          }
        }

        setState(510);
        match(COMMA);
        setState(512);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(511);
            match(WS);
          }
        }

        setState(514);
        expr(0);
        setState(516);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(515);
            match(WS);
          }
        }

        setState(518);
        match(COMMA);
        setState(520);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(519);
            match(WS);
          }
        }

        setState(522);
        expr(0);
        setState(524);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(523);
            match(WS);
          }
        }

        setState(526);
        match(COMMA);
        setState(528);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(527);
            match(WS);
          }
        }

        setState(530);
        expr(0);
        setState(532);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == WS) {
          {
            setState(531);
            match(WS);
          }
        }

        setState(534);
        match(RPAREN);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ExprContext extends ParserRuleContext {
    public ExprContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expr;
    }

    public ExprContext() {}

    public void copyFrom(ExprContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class VarExprContext extends ExprContext {
    public VarContext var() {
      return getRuleContext(VarContext.class, 0);
    }

    public VarExprContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterVarExpr(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitVarExpr(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitVarExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class AtomExprContext extends ExprContext {
    public AtomContext atom() {
      return getRuleContext(AtomContext.class, 0);
    }

    public AtomExprContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterAtomExpr(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitAtomExpr(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitAtomExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class RelationalExprContext extends ExprContext {
    public Token op;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public TerminalNode MULTIPLY() {
      return getToken(VecParser.MULTIPLY, 0);
    }

    public TerminalNode MODULO() {
      return getToken(VecParser.MODULO, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public TerminalNode PLUS() {
      return getToken(VecParser.PLUS, 0);
    }

    public TerminalNode MINUS() {
      return getToken(VecParser.MINUS, 0);
    }

    public TerminalNode LESSTHANOREQUAL() {
      return getToken(VecParser.LESSTHANOREQUAL, 0);
    }

    public TerminalNode GREATERTHANOREQUAL() {
      return getToken(VecParser.GREATERTHANOREQUAL, 0);
    }

    public TerminalNode LESSTHAN() {
      return getToken(VecParser.LESSTHAN, 0);
    }

    public TerminalNode GREATERTHAN() {
      return getToken(VecParser.GREATERTHAN, 0);
    }

    public RelationalExprContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterRelationalExpr(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitRelationalExpr(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitRelationalExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class ParenExprContext extends ExprContext {
    public TerminalNode LPAREN() {
      return getToken(VecParser.LPAREN, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public TerminalNode RPAREN() {
      return getToken(VecParser.RPAREN, 0);
    }

    public ParenExprContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterParenExpr(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitParenExpr(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitParenExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class AndExprContext extends ExprContext {
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public TerminalNode AND() {
      return getToken(VecParser.AND, 0);
    }

    public List<TerminalNode> WS() {
      return getTokens(VecParser.WS);
    }

    public TerminalNode WS(int i) {
      return getToken(VecParser.WS, i);
    }

    public AndExprContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterAndExpr(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitAndExpr(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitAndExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ExprContext expr() throws RecognitionException {
    return expr(0);
  }

  private ExprContext expr(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExprContext _localctx = new ExprContext(_ctx, _parentState);
    ExprContext _prevctx = _localctx;
    int _startState = 26;
    enterRecursionRule(_localctx, 26, RULE_expr, _p);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(543);
        _errHandler.sync(this);
        switch (_input.LA(1)) {
          case LPAREN:
            {
              _localctx = new ParenExprContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;

              setState(537);
              match(LPAREN);
              setState(538);
              expr(0);
              setState(539);
              match(RPAREN);
            }
            break;
          case ID:
            {
              _localctx = new VarExprContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(541);
              var();
            }
            break;
          case INT:
            {
              _localctx = new AtomExprContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(542);
              atom();
            }
            break;
          default:
            throw new NoViableAltException(this);
        }
        _ctx.stop = _input.LT(-1);
        setState(583);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 123, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null) triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(581);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 122, _ctx)) {
                case 1:
                  {
                    _localctx =
                        new RelationalExprContext(new ExprContext(_parentctx, _parentState));
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(545);
                    if (!(precpred(_ctx, 6)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                    setState(547);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(546);
                        match(WS);
                      }
                    }

                    setState(549);
                    ((RelationalExprContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!(_la == MULTIPLY || _la == MODULO)) {
                      ((RelationalExprContext) _localctx).op =
                          (Token) _errHandler.recoverInline(this);
                    } else {
                      if (_input.LA(1) == Token.EOF) matchedEOF = true;
                      _errHandler.reportMatch(this);
                      consume();
                    }
                    setState(551);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(550);
                        match(WS);
                      }
                    }

                    setState(553);
                    expr(7);
                  }
                  break;
                case 2:
                  {
                    _localctx =
                        new RelationalExprContext(new ExprContext(_parentctx, _parentState));
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(554);
                    if (!(precpred(_ctx, 5)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                    setState(556);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(555);
                        match(WS);
                      }
                    }

                    setState(558);
                    ((RelationalExprContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!(_la == PLUS || _la == MINUS)) {
                      ((RelationalExprContext) _localctx).op =
                          (Token) _errHandler.recoverInline(this);
                    } else {
                      if (_input.LA(1) == Token.EOF) matchedEOF = true;
                      _errHandler.reportMatch(this);
                      consume();
                    }
                    setState(560);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(559);
                        match(WS);
                      }
                    }

                    setState(562);
                    expr(6);
                  }
                  break;
                case 3:
                  {
                    _localctx =
                        new RelationalExprContext(new ExprContext(_parentctx, _parentState));
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(563);
                    if (!(precpred(_ctx, 4)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                    setState(565);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(564);
                        match(WS);
                      }
                    }

                    setState(567);
                    ((RelationalExprContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0
                        && ((1L << _la)
                                & ((1L << LESSTHANOREQUAL)
                                    | (1L << GREATERTHANOREQUAL)
                                    | (1L << LESSTHAN)
                                    | (1L << GREATERTHAN)))
                            != 0))) {
                      ((RelationalExprContext) _localctx).op =
                          (Token) _errHandler.recoverInline(this);
                    } else {
                      if (_input.LA(1) == Token.EOF) matchedEOF = true;
                      _errHandler.reportMatch(this);
                      consume();
                    }
                    setState(569);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(568);
                        match(WS);
                      }
                    }

                    setState(571);
                    expr(5);
                  }
                  break;
                case 4:
                  {
                    _localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(572);
                    if (!(precpred(_ctx, 3)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                    setState(574);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(573);
                        match(WS);
                      }
                    }

                    setState(576);
                    match(AND);
                    setState(578);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WS) {
                      {
                        setState(577);
                        match(WS);
                      }
                    }

                    setState(580);
                    expr(4);
                  }
                  break;
              }
            }
          }
          setState(585);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 123, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  public static class VarContext extends ParserRuleContext {
    public VarContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_var;
    }

    public VarContext() {}

    public void copyFrom(VarContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class IdVarContext extends VarContext {
    public TerminalNode ID() {
      return getToken(VecParser.ID, 0);
    }

    public IdVarContext(VarContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterIdVar(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitIdVar(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitIdVar(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class ArrayVarContext extends VarContext {
    public TerminalNode ID() {
      return getToken(VecParser.ID, 0);
    }

    public List<TerminalNode> LBRACKET() {
      return getTokens(VecParser.LBRACKET);
    }

    public TerminalNode LBRACKET(int i) {
      return getToken(VecParser.LBRACKET, i);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> RBRACKET() {
      return getTokens(VecParser.RBRACKET);
    }

    public TerminalNode RBRACKET(int i) {
      return getToken(VecParser.RBRACKET, i);
    }

    public ArrayVarContext(VarContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterArrayVar(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitArrayVar(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitArrayVar(this);
      else return visitor.visitChildren(this);
    }
  }

  public final VarContext var() throws RecognitionException {
    VarContext _localctx = new VarContext(_ctx, getState());
    enterRule(_localctx, 28, RULE_var);
    try {
      int _alt;
      setState(596);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 125, _ctx)) {
        case 1:
          _localctx = new ArrayVarContext(_localctx);
          enterOuterAlt(_localctx, 1);
          {
            setState(586);
            match(ID);
            setState(591);
            _errHandler.sync(this);
            _alt = 1;
            do {
              switch (_alt) {
                case 1:
                  {
                    {
                      setState(587);
                      match(LBRACKET);
                      setState(588);
                      expr(0);
                      setState(589);
                      match(RBRACKET);
                    }
                  }
                  break;
                default:
                  throw new NoViableAltException(this);
              }
              setState(593);
              _errHandler.sync(this);
              _alt = getInterpreter().adaptivePredict(_input, 124, _ctx);
            } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
          }
          break;
        case 2:
          _localctx = new IdVarContext(_localctx);
          enterOuterAlt(_localctx, 2);
          {
            setState(595);
            match(ID);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class BinopContext extends ParserRuleContext {
    public TerminalNode LESSTHANOREQUAL() {
      return getToken(VecParser.LESSTHANOREQUAL, 0);
    }

    public TerminalNode GREATERTHANOREQUAL() {
      return getToken(VecParser.GREATERTHANOREQUAL, 0);
    }

    public TerminalNode LESSTHAN() {
      return getToken(VecParser.LESSTHAN, 0);
    }

    public TerminalNode GREATERTHAN() {
      return getToken(VecParser.GREATERTHAN, 0);
    }

    public TerminalNode PLUS() {
      return getToken(VecParser.PLUS, 0);
    }

    public TerminalNode MINUS() {
      return getToken(VecParser.MINUS, 0);
    }

    public TerminalNode MULTIPLY() {
      return getToken(VecParser.MULTIPLY, 0);
    }

    public TerminalNode MODULO() {
      return getToken(VecParser.MODULO, 0);
    }

    public BinopContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_binop;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterBinop(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitBinop(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitBinop(this);
      else return visitor.visitChildren(this);
    }
  }

  public final BinopContext binop() throws RecognitionException {
    BinopContext _localctx = new BinopContext(_ctx, getState());
    enterRule(_localctx, 30, RULE_binop);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(598);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0
            && ((1L << _la)
                    & ((1L << LESSTHANOREQUAL)
                        | (1L << GREATERTHANOREQUAL)
                        | (1L << LESSTHAN)
                        | (1L << GREATERTHAN)
                        | (1L << PLUS)
                        | (1L << MINUS)
                        | (1L << MULTIPLY)
                        | (1L << MODULO)))
                != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class AssignopContext extends ParserRuleContext {
    public TerminalNode ASSIGN() {
      return getToken(VecParser.ASSIGN, 0);
    }

    public TerminalNode ADDASSIGN() {
      return getToken(VecParser.ADDASSIGN, 0);
    }

    public TerminalNode SUBASSIGN() {
      return getToken(VecParser.SUBASSIGN, 0);
    }

    public TerminalNode MULASSIGN() {
      return getToken(VecParser.MULASSIGN, 0);
    }

    public AssignopContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_assignop;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterAssignop(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitAssignop(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitAssignop(this);
      else return visitor.visitChildren(this);
    }
  }

  public final AssignopContext assignop() throws RecognitionException {
    AssignopContext _localctx = new AssignopContext(_ctx, getState());
    enterRule(_localctx, 32, RULE_assignop);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(600);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0
            && ((1L << _la)
                    & ((1L << ADDASSIGN) | (1L << SUBASSIGN) | (1L << MULASSIGN) | (1L << ASSIGN)))
                != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class AtomContext extends ParserRuleContext {
    public AtomContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_atom;
    }

    public AtomContext() {}

    public void copyFrom(AtomContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class IntAtomContext extends AtomContext {
    public TerminalNode INT() {
      return getToken(VecParser.INT, 0);
    }

    public IntAtomContext(AtomContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).enterIntAtom(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof VecListener) ((VecListener) listener).exitIntAtom(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof VecVisitor)
        return ((VecVisitor<? extends T>) visitor).visitIntAtom(this);
      else return visitor.visitChildren(this);
    }
  }

  public final AtomContext atom() throws RecognitionException {
    AtomContext _localctx = new AtomContext(_ctx, getState());
    enterRule(_localctx, 34, RULE_atom);
    try {
      _localctx = new IntAtomContext(_localctx);
      enterOuterAlt(_localctx, 1);
      {
        setState(602);
        match(INT);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
      case 13:
        return expr_sempred((ExprContext) _localctx, predIndex);
    }
    return true;
  }

  private boolean expr_sempred(ExprContext _localctx, int predIndex) {
    switch (predIndex) {
      case 0:
        return precpred(_ctx, 6);
      case 1:
        return precpred(_ctx, 5);
      case 2:
        return precpred(_ctx, 4);
      case 3:
        return precpred(_ctx, 3);
    }
    return true;
  }

  public static final String _serializedATN =
      "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3)\u025f\4\2\t\2\4"
          + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
          + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
          + "\4\23\t\23\3\2\3\2\5\2)\n\2\3\2\3\2\3\2\3\2\5\2/\n\2\3\2\3\2\5\2\63\n"
          + "\2\3\3\3\3\5\3\67\n\3\7\39\n\3\f\3\16\3<\13\3\3\4\3\4\3\4\3\4\3\4\3\4"
          + "\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"
          + "\4\5\4V\n\4\3\5\5\5Y\n\5\3\5\3\5\5\5]\n\5\3\5\3\5\5\5a\n\5\3\5\3\5\5\5"
          + "e\n\5\3\5\3\5\5\5i\n\5\3\5\3\5\5\5m\n\5\3\5\3\5\3\5\3\6\5\6s\n\6\3\6\3"
          + "\6\5\6w\n\6\3\6\3\6\5\6{\n\6\3\6\3\6\5\6\177\n\6\3\6\3\6\5\6\u0083\n\6"
          + "\3\6\3\6\5\6\u0087\n\6\3\6\3\6\3\6\5\6\u008c\n\6\3\6\3\6\5\6\u0090\n\6"
          + "\3\6\3\6\3\6\5\6\u0095\n\6\3\6\3\6\3\7\5\7\u009a\n\7\3\7\5\7\u009d\n\7"
          + "\3\7\3\7\5\7\u00a1\n\7\3\7\3\7\5\7\u00a5\n\7\3\7\3\7\3\7\5\7\u00aa\n\7"
          + "\3\7\3\7\3\7\5\7\u00af\n\7\3\7\3\7\3\7\5\7\u00b4\n\7\3\b\5\b\u00b7\n\b"
          + "\3\b\3\b\3\b\5\b\u00bc\n\b\3\b\3\b\5\b\u00c0\n\b\3\b\3\b\5\b\u00c4\n\b"
          + "\3\b\3\b\5\b\u00c8\n\b\3\b\3\b\5\b\u00cc\n\b\3\b\3\b\5\b\u00d0\n\b\3\b"
          + "\3\b\3\t\5\t\u00d5\n\t\3\t\3\t\3\t\5\t\u00da\n\t\3\t\3\t\5\t\u00de\n\t"
          + "\3\t\3\t\5\t\u00e2\n\t\3\t\3\t\5\t\u00e6\n\t\3\t\3\t\5\t\u00ea\n\t\3\t"
          + "\3\t\5\t\u00ee\n\t\3\t\3\t\5\t\u00f2\n\t\3\t\3\t\5\t\u00f6\n\t\3\t\3\t"
          + "\5\t\u00fa\n\t\3\t\3\t\5\t\u00fe\n\t\3\t\3\t\5\t\u0102\n\t\3\t\3\t\5\t"
          + "\u0106\n\t\3\t\3\t\5\t\u010a\n\t\3\t\3\t\5\t\u010e\n\t\3\t\3\t\3\n\5\n"
          + "\u0113\n\n\3\n\3\n\3\n\5\n\u0118\n\n\3\n\3\n\5\n\u011c\n\n\3\n\3\n\5\n"
          + "\u0120\n\n\3\n\3\n\5\n\u0124\n\n\3\n\3\n\5\n\u0128\n\n\3\n\3\n\5\n\u012c"
          + "\n\n\3\n\3\n\5\n\u0130\n\n\3\n\3\n\5\n\u0134\n\n\3\n\3\n\5\n\u0138\n\n"
          + "\3\n\3\n\5\n\u013c\n\n\3\n\3\n\5\n\u0140\n\n\3\n\3\n\5\n\u0144\n\n\3\n"
          + "\3\n\5\n\u0148\n\n\3\n\3\n\5\n\u014c\n\n\3\n\3\n\3\13\5\13\u0151\n\13"
          + "\3\13\3\13\3\13\5\13\u0156\n\13\3\13\3\13\5\13\u015a\n\13\3\13\3\13\5"
          + "\13\u015e\n\13\3\13\3\13\5\13\u0162\n\13\7\13\u0164\n\13\f\13\16\13\u0167"
          + "\13\13\3\13\3\13\3\f\5\f\u016c\n\f\3\f\3\f\3\f\5\f\u0171\n\f\3\f\3\f\5"
          + "\f\u0175\n\f\3\f\3\f\5\f\u0179\n\f\3\f\3\f\5\f\u017d\n\f\7\f\u017f\n\f"
          + "\f\f\16\f\u0182\13\f\3\f\3\f\3\r\5\r\u0187\n\r\3\r\3\r\5\r\u018b\n\r\3"
          + "\r\3\r\5\r\u018f\n\r\3\r\3\r\5\r\u0193\n\r\3\r\3\r\5\r\u0197\n\r\3\r\3"
          + "\r\5\r\u019b\n\r\3\r\3\r\5\r\u019f\n\r\3\r\3\r\5\r\u01a3\n\r\3\r\3\r\5"
          + "\r\u01a7\n\r\3\r\3\r\5\r\u01ab\n\r\3\r\3\r\5\r\u01af\n\r\3\r\3\r\5\r\u01b3"
          + "\n\r\3\r\3\r\5\r\u01b7\n\r\3\r\3\r\5\r\u01bb\n\r\3\r\3\r\5\r\u01bf\n\r"
          + "\3\r\3\r\5\r\u01c3\n\r\3\r\3\r\5\r\u01c7\n\r\3\r\3\r\5\r\u01cb\n\r\7\r"
          + "\u01cd\n\r\f\r\16\r\u01d0\13\r\3\r\3\r\5\r\u01d4\n\r\3\r\3\r\3\r\5\r\u01d9"
          + "\n\r\3\r\3\r\3\16\5\16\u01de\n\16\3\16\3\16\3\16\5\16\u01e3\n\16\3\16"
          + "\3\16\5\16\u01e7\n\16\3\16\3\16\5\16\u01eb\n\16\3\16\3\16\5\16\u01ef\n"
          + "\16\3\16\3\16\5\16\u01f3\n\16\3\16\3\16\5\16\u01f7\n\16\3\16\3\16\5\16"
          + "\u01fb\n\16\3\16\3\16\5\16\u01ff\n\16\3\16\3\16\5\16\u0203\n\16\3\16\3"
          + "\16\5\16\u0207\n\16\3\16\3\16\5\16\u020b\n\16\3\16\3\16\5\16\u020f\n\16"
          + "\3\16\3\16\5\16\u0213\n\16\3\16\3\16\5\16\u0217\n\16\3\16\3\16\3\17\3"
          + "\17\3\17\3\17\3\17\3\17\3\17\5\17\u0222\n\17\3\17\3\17\5\17\u0226\n\17"
          + "\3\17\3\17\5\17\u022a\n\17\3\17\3\17\3\17\5\17\u022f\n\17\3\17\3\17\5"
          + "\17\u0233\n\17\3\17\3\17\3\17\5\17\u0238\n\17\3\17\3\17\5\17\u023c\n\17"
          + "\3\17\3\17\3\17\5\17\u0241\n\17\3\17\3\17\5\17\u0245\n\17\3\17\7\17\u0248"
          + "\n\17\f\17\16\17\u024b\13\17\3\20\3\20\3\20\3\20\3\20\6\20\u0252\n\20"
          + "\r\20\16\20\u0253\3\20\5\20\u0257\n\20\3\21\3\21\3\22\3\22\3\23\3\23\3"
          + "\23\2\3\34\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\7\3\2\"#\3"
          + "\2 !\3\2\31\34\4\2\31\34 #\4\2\35\37$$\2\u02d6\2\62\3\2\2\2\4:\3\2\2\2"
          + "\6U\3\2\2\2\bX\3\2\2\2\nr\3\2\2\2\f\u00b3\3\2\2\2\16\u00b6\3\2\2\2\20"
          + "\u00d4\3\2\2\2\22\u0112\3\2\2\2\24\u0150\3\2\2\2\26\u016b\3\2\2\2\30\u0186"
          + "\3\2\2\2\32\u01dd\3\2\2\2\34\u0221\3\2\2\2\36\u0256\3\2\2\2 \u0258\3\2"
          + "\2\2\"\u025a\3\2\2\2$\u025c\3\2\2\2&(\5\4\3\2\')\7)\2\2(\'\3\2\2\2()\3"
          + "\2\2\2)*\3\2\2\2*+\7\2\2\3+\63\3\2\2\2,.\5\n\6\2-/\7)\2\2.-\3\2\2\2./"
          + "\3\2\2\2/\60\3\2\2\2\60\61\7\2\2\3\61\63\3\2\2\2\62&\3\2\2\2\62,\3\2\2"
          + "\2\63\3\3\2\2\2\64\66\5\6\4\2\65\67\7)\2\2\66\65\3\2\2\2\66\67\3\2\2\2"
          + "\679\3\2\2\28\64\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;\5\3\2\2\2<:\3"
          + "\2\2\2=>\5\f\7\2>?\7%\2\2?V\3\2\2\2@V\5\b\5\2AV\5\n\6\2BC\5\16\b\2CD\7"
          + "%\2\2DV\3\2\2\2EF\5\20\t\2FG\7%\2\2GV\3\2\2\2HI\5\22\n\2IJ\7%\2\2JV\3"
          + "\2\2\2KL\5\32\16\2LM\7%\2\2MV\3\2\2\2NO\5\24\13\2OP\7%\2\2PV\3\2\2\2Q"
          + "R\5\26\f\2RS\7%\2\2SV\3\2\2\2TV\5\30\r\2U=\3\2\2\2U@\3\2\2\2UA\3\2\2\2"
          + "UB\3\2\2\2UE\3\2\2\2UH\3\2\2\2UK\3\2\2\2UN\3\2\2\2UQ\3\2\2\2UT\3\2\2\2"
          + "V\7\3\2\2\2WY\7)\2\2XW\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Z\\\7\f\2\2[]\7)\2\2"
          + "\\[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^`\7\22\2\2_a\7)\2\2`_\3\2\2\2`a\3\2\2"
          + "\2ab\3\2\2\2bd\5\34\17\2ce\7)\2\2dc\3\2\2\2de\3\2\2\2ef\3\2\2\2fh\7\23"
          + "\2\2gi\7)\2\2hg\3\2\2\2hi\3\2\2\2ij\3\2\2\2jl\7\24\2\2km\7)\2\2lk\3\2"
          + "\2\2lm\3\2\2\2mn\3\2\2\2no\5\4\3\2op\7\25\2\2p\t\3\2\2\2qs\7)\2\2rq\3"
          + "\2\2\2rs\3\2\2\2st\3\2\2\2tv\7\r\2\2uw\7)\2\2vu\3\2\2\2vw\3\2\2\2wx\3"
          + "\2\2\2xz\7\22\2\2y{\7)\2\2zy\3\2\2\2z{\3\2\2\2{|\3\2\2\2|~\5\f\7\2}\177"
          + "\7)\2\2~}\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\7%\2\2\u0081"
          + "\u0083\7)\2\2\u0082\u0081\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2"
          + "\2\2\u0084\u0086\5\34\17\2\u0085\u0087\7)\2\2\u0086\u0085\3\2\2\2\u0086"
          + "\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\7%\2\2\u0089\u008b\5\f"
          + "\7\2\u008a\u008c\7)\2\2\u008b\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c"
          + "\u008d\3\2\2\2\u008d\u008f\7\23\2\2\u008e\u0090\7)\2\2\u008f\u008e\3\2"
          + "\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092\7\24\2\2\u0092"
          + "\u0094\5\4\3\2\u0093\u0095\7)\2\2\u0094\u0093\3\2\2\2\u0094\u0095\3\2"
          + "\2\2\u0095\u0096\3\2\2\2\u0096\u0097\7\25\2\2\u0097\13\3\2\2\2\u0098\u009a"
          + "\7)\2\2\u0099\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b"
          + "\u009d\7\21\2\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009e\3"
          + "\2\2\2\u009e\u00a0\5\36\20\2\u009f\u00a1\7)\2\2\u00a0\u009f\3\2\2\2\u00a0"
          + "\u00a1\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a4\5\"\22\2\u00a3\u00a5\7"
          + ")\2\2\u00a4\u00a3\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6"
          + "\u00a7\5\34\17\2\u00a7\u00b4\3\2\2\2\u00a8\u00aa\7)\2\2\u00a9\u00a8\3"
          + "\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\7\3\2\2\u00ac"
          + "\u00b4\5\36\20\2\u00ad\u00af\7)\2\2\u00ae\u00ad\3\2\2\2\u00ae\u00af\3"
          + "\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\5\36\20\2\u00b1\u00b2\7\3\2\2\u00b2"
          + "\u00b4\3\2\2\2\u00b3\u0099\3\2\2\2\u00b3\u00a9\3\2\2\2\u00b3\u00ae\3\2"
          + "\2\2\u00b4\r\3\2\2\2\u00b5\u00b7\7)\2\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7"
          + "\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\7\6\2\2\u00b9\u00bb\7\22\2\2"
          + "\u00ba\u00bc\7)\2\2\u00bb\u00ba\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd"
          + "\3\2\2\2\u00bd\u00bf\5\34\17\2\u00be\u00c0\7)\2\2\u00bf\u00be\3\2\2\2"
          + "\u00bf\u00c0\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c3\7\30\2\2\u00c2\u00c4"
          + "\7)\2\2\u00c3\u00c2\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"
          + "\u00c7\5\34\17\2\u00c6\u00c8\7)\2\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8\3"
          + "\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00cb\7\30\2\2\u00ca\u00cc\7)\2\2\u00cb"
          + "\u00ca\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00cf\5\34"
          + "\17\2\u00ce\u00d0\7)\2\2\u00cf\u00ce\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0"
          + "\u00d1\3\2\2\2\u00d1\u00d2\7\23\2\2\u00d2\17\3\2\2\2\u00d3\u00d5\7)\2"
          + "\2\u00d4\u00d3\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7"
          + "\7\7\2\2\u00d7\u00d9\7\22\2\2\u00d8\u00da\7)\2\2\u00d9\u00d8\3\2\2\2\u00d9"
          + "\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dd\5\34\17\2\u00dc\u00de\7"
          + ")\2\2\u00dd\u00dc\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\3\2\2\2\u00df"
          + "\u00e1\7\30\2\2\u00e0\u00e2\7)\2\2\u00e1\u00e0\3\2\2\2\u00e1\u00e2\3\2"
          + "\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e5\5\34\17\2\u00e4\u00e6\7)\2\2\u00e5"
          + "\u00e4\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e9\7\30"
          + "\2\2\u00e8\u00ea\7)\2\2\u00e9\u00e8\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea"
          + "\u00eb\3\2\2\2\u00eb\u00ed\5\34\17\2\u00ec\u00ee\7)\2\2\u00ed\u00ec\3"
          + "\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f1\7\30\2\2\u00f0"
          + "\u00f2\7)\2\2\u00f1\u00f0\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3\3\2"
          + "\2\2\u00f3\u00f5\5\34\17\2\u00f4\u00f6\7)\2\2\u00f5\u00f4\3\2\2\2\u00f5"
          + "\u00f6\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f9\7\30\2\2\u00f8\u00fa\7"
          + ")\2\2\u00f9\u00f8\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb"
          + "\u00fd\5\34\17\2\u00fc\u00fe\7)\2\2\u00fd\u00fc\3\2\2\2\u00fd\u00fe\3"
          + "\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101\7\30\2\2\u0100\u0102\7)\2\2\u0101"
          + "\u0100\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0105\5\34"
          + "\17\2\u0104\u0106\7)\2\2\u0105\u0104\3\2\2\2\u0105\u0106\3\2\2\2\u0106"
          + "\u0107\3\2\2\2\u0107\u0109\7\30\2\2\u0108\u010a\7)\2\2\u0109\u0108\3\2"
          + "\2\2\u0109\u010a\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u010d\5\34\17\2\u010c"
          + "\u010e\7)\2\2\u010d\u010c\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u010f\3\2"
          + "\2\2\u010f\u0110\7\23\2\2\u0110\21\3\2\2\2\u0111\u0113\7)\2\2\u0112\u0111"
          + "\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0115\7\b\2\2\u0115"
          + "\u0117\7\22\2\2\u0116\u0118\7)\2\2\u0117\u0116\3\2\2\2\u0117\u0118\3\2"
          + "\2\2\u0118\u0119\3\2\2\2\u0119\u011b\5\34\17\2\u011a\u011c\7)\2\2\u011b"
          + "\u011a\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011f\7\30"
          + "\2\2\u011e\u0120\7)\2\2\u011f\u011e\3\2\2\2\u011f\u0120\3\2\2\2\u0120"
          + "\u0121\3\2\2\2\u0121\u0123\5\34\17\2\u0122\u0124\7)\2\2\u0123\u0122\3"
          + "\2\2\2\u0123\u0124\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0127\7\30\2\2\u0126"
          + "\u0128\7)\2\2\u0127\u0126\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0129\3\2"
          + "\2\2\u0129\u012b\5\34\17\2\u012a\u012c\7)\2\2\u012b\u012a\3\2\2\2\u012b"
          + "\u012c\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012f\7\30\2\2\u012e\u0130\7"
          + ")\2\2\u012f\u012e\3\2\2\2\u012f\u0130\3\2\2\2\u0130\u0131\3\2\2\2\u0131"
          + "\u0133\5\34\17\2\u0132\u0134\7)\2\2\u0133\u0132\3\2\2\2\u0133\u0134\3"
          + "\2\2\2\u0134\u0135\3\2\2\2\u0135\u0137\7\30\2\2\u0136\u0138\7)\2\2\u0137"
          + "\u0136\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013b\5\34"
          + "\17\2\u013a\u013c\7)\2\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2\2\2\u013c"
          + "\u013d\3\2\2\2\u013d\u013f\7\30\2\2\u013e\u0140\7)\2\2\u013f\u013e\3\2"
          + "\2\2\u013f\u0140\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0143\5\34\17\2\u0142"
          + "\u0144\7)\2\2\u0143\u0142\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0145\3\2"
          + "\2\2\u0145\u0147\7\30\2\2\u0146\u0148\7)\2\2\u0147\u0146\3\2\2\2\u0147"
          + "\u0148\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u014b\5\34\17\2\u014a\u014c\7"
          + ")\2\2\u014b\u014a\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014d\3\2\2\2\u014d"
          + "\u014e\7\23\2\2\u014e\23\3\2\2\2\u014f\u0151\7)\2\2\u0150\u014f\3\2\2"
          + "\2\u0150\u0151\3\2\2\2\u0151\u0152\3\2\2\2\u0152\u0153\7\t\2\2\u0153\u0155"
          + "\7\22\2\2\u0154\u0156\7)\2\2\u0155\u0154\3\2\2\2\u0155\u0156\3\2\2\2\u0156"
          + "\u0157\3\2\2\2\u0157\u0159\7(\2\2\u0158\u015a\7)\2\2\u0159\u0158\3\2\2"
          + "\2\u0159\u015a\3\2\2\2\u015a\u0165\3\2\2\2\u015b\u015d\7\30\2\2\u015c"
          + "\u015e\7)\2\2\u015d\u015c\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u015f\3\2"
          + "\2\2\u015f\u0161\5\34\17\2\u0160\u0162\7)\2\2\u0161\u0160\3\2\2\2\u0161"
          + "\u0162\3\2\2\2\u0162\u0164\3\2\2\2\u0163\u015b\3\2\2\2\u0164\u0167\3\2"
          + "\2\2\u0165\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0168\3\2\2\2\u0167"
          + "\u0165\3\2\2\2\u0168\u0169\7\23\2\2\u0169\25\3\2\2\2\u016a\u016c\7)\2"
          + "\2\u016b\u016a\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016e"
          + "\7\13\2\2\u016e\u0170\7\22\2\2\u016f\u0171\7)\2\2\u0170\u016f\3\2\2\2"
          + "\u0170\u0171\3\2\2\2\u0171\u0172\3\2\2\2\u0172\u0174\7(\2\2\u0173\u0175"
          + "\7)\2\2\u0174\u0173\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u0180\3\2\2\2\u0176"
          + "\u0178\7\30\2\2\u0177\u0179\7)\2\2\u0178\u0177\3\2\2\2\u0178\u0179\3\2"
          + "\2\2\u0179\u017a\3\2\2\2\u017a\u017c\5\34\17\2\u017b\u017d\7)\2\2\u017c"
          + "\u017b\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017f\3\2\2\2\u017e\u0176\3\2"
          + "\2\2\u017f\u0182\3\2\2\2\u0180\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181"
          + "\u0183\3\2\2\2\u0182\u0180\3\2\2\2\u0183\u0184\7\23\2\2\u0184\27\3\2\2"
          + "\2\u0185\u0187\7)\2\2\u0186\u0185\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u0188"
          + "\3\2\2\2\u0188\u018a\7\16\2\2\u0189\u018b\7)\2\2\u018a\u0189\3\2\2\2\u018a"
          + "\u018b\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018e\7\22\2\2\u018d\u018f\7"
          + ")\2\2\u018e\u018d\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u0190\3\2\2\2\u0190"
          + "\u0192\5\36\20\2\u0191\u0193\7)\2\2\u0192\u0191\3\2\2\2\u0192\u0193\3"
          + "\2\2\2\u0193\u0194\3\2\2\2\u0194\u0196\7\17\2\2\u0195\u0197\7)\2\2\u0196"
          + "\u0195\3\2\2\2\u0196\u0197\3\2\2\2\u0197\u0198\3\2\2\2\u0198\u019a\7\26"
          + "\2\2\u0199\u019b\7)\2\2\u019a\u0199\3\2\2\2\u019a\u019b\3\2\2\2\u019b"
          + "\u019c\3\2\2\2\u019c\u019e\5\34\17\2\u019d\u019f\7)\2\2\u019e\u019d\3"
          + "\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a2\7\20\2\2\u01a1"
          + "\u01a3\7)\2\2\u01a2\u01a1\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a4\3\2"
          + "\2\2\u01a4\u01a6\5\34\17\2\u01a5\u01a7\7)\2\2\u01a6\u01a5\3\2\2\2\u01a6"
          + "\u01a7\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01ce\7\27\2\2\u01a9\u01ab\7"
          + ")\2\2\u01aa\u01a9\3\2\2\2\u01aa\u01ab\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac"
          + "\u01ae\7\5\2\2\u01ad\u01af\7)\2\2\u01ae\u01ad\3\2\2\2\u01ae\u01af\3\2"
          + "\2\2\u01af\u01b0\3\2\2\2\u01b0\u01b2\5\36\20\2\u01b1\u01b3\7)\2\2\u01b2"
          + "\u01b1\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3\u01b4\3\2\2\2\u01b4\u01b6\7\17"
          + "\2\2\u01b5\u01b7\7)\2\2\u01b6\u01b5\3\2\2\2\u01b6\u01b7\3\2\2\2\u01b7"
          + "\u01b8\3\2\2\2\u01b8\u01ba\7\26\2\2\u01b9\u01bb\7)\2\2\u01ba\u01b9\3\2"
          + "\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01be\5\34\17\2\u01bd"
          + "\u01bf\7)\2\2\u01be\u01bd\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01c0\3\2"
          + "\2\2\u01c0\u01c2\7\20\2\2\u01c1\u01c3\7)\2\2\u01c2\u01c1\3\2\2\2\u01c2"
          + "\u01c3\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c6\5\34\17\2\u01c5\u01c7\7"
          + ")\2\2\u01c6\u01c5\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8"
          + "\u01ca\7\27\2\2\u01c9\u01cb\7)\2\2\u01ca\u01c9\3\2\2\2\u01ca\u01cb\3\2"
          + "\2\2\u01cb\u01cd\3\2\2\2\u01cc\u01aa\3\2\2\2\u01cd\u01d0\3\2\2\2\u01ce"
          + "\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d1\3\2\2\2\u01d0\u01ce\3\2"
          + "\2\2\u01d1\u01d3\7\23\2\2\u01d2\u01d4\7)\2\2\u01d3\u01d2\3\2\2\2\u01d3"
          + "\u01d4\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5\u01d6\7\24\2\2\u01d6\u01d8\5"
          + "\4\3\2\u01d7\u01d9\7)\2\2\u01d8\u01d7\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9"
          + "\u01da\3\2\2\2\u01da\u01db\7\25\2\2\u01db\31\3\2\2\2\u01dc\u01de\7)\2"
          + "\2\u01dd\u01dc\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e0"
          + "\7\n\2\2\u01e0\u01e2\7\22\2\2\u01e1\u01e3\7)\2\2\u01e2\u01e1\3\2\2\2\u01e2"
          + "\u01e3\3\2\2\2\u01e3\u01e4\3\2\2\2\u01e4\u01e6\5\34\17\2\u01e5\u01e7\7"
          + ")\2\2\u01e6\u01e5\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8"
          + "\u01ea\7\30\2\2\u01e9\u01eb\7)\2\2\u01ea\u01e9\3\2\2\2\u01ea\u01eb\3\2"
          + "\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ee\5\34\17\2\u01ed\u01ef\7)\2\2\u01ee"
          + "\u01ed\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0\u01f2\7\30"
          + "\2\2\u01f1\u01f3\7)\2\2\u01f2\u01f1\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3"
          + "\u01f4\3\2\2\2\u01f4\u01f6\5\34\17\2\u01f5\u01f7\7)\2\2\u01f6\u01f5\3"
          + "\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01fa\7\30\2\2\u01f9"
          + "\u01fb\7)\2\2\u01fa\u01f9\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fc\3\2"
          + "\2\2\u01fc\u01fe\5\34\17\2\u01fd\u01ff\7)\2\2\u01fe\u01fd\3\2\2\2\u01fe"
          + "\u01ff\3\2\2\2\u01ff\u0200\3\2\2\2\u0200\u0202\7\30\2\2\u0201\u0203\7"
          + ")\2\2\u0202\u0201\3\2\2\2\u0202\u0203\3\2\2\2\u0203\u0204\3\2\2\2\u0204"
          + "\u0206\5\34\17\2\u0205\u0207\7)\2\2\u0206\u0205\3\2\2\2\u0206\u0207\3"
          + "\2\2\2\u0207\u0208\3\2\2\2\u0208\u020a\7\30\2\2\u0209\u020b\7)\2\2\u020a"
          + "\u0209\3\2\2\2\u020a\u020b\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020e\5\34"
          + "\17\2\u020d\u020f\7)\2\2\u020e\u020d\3\2\2\2\u020e\u020f\3\2\2\2\u020f"
          + "\u0210\3\2\2\2\u0210\u0212\7\30\2\2\u0211\u0213\7)\2\2\u0212\u0211\3\2"
          + "\2\2\u0212\u0213\3\2\2\2\u0213\u0214\3\2\2\2\u0214\u0216\5\34\17\2\u0215"
          + "\u0217\7)\2\2\u0216\u0215\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0218\3\2"
          + "\2\2\u0218\u0219\7\23\2\2\u0219\33\3\2\2\2\u021a\u021b\b\17\1\2\u021b"
          + "\u021c\7\22\2\2\u021c\u021d\5\34\17\2\u021d\u021e\7\23\2\2\u021e\u0222"
          + "\3\2\2\2\u021f\u0222\5\36\20\2\u0220\u0222\5$\23\2\u0221\u021a\3\2\2\2"
          + "\u0221\u021f\3\2\2\2\u0221\u0220\3\2\2\2\u0222\u0249\3\2\2\2\u0223\u0225"
          + "\f\b\2\2\u0224\u0226\7)\2\2\u0225\u0224\3\2\2\2\u0225\u0226\3\2\2\2\u0226"
          + "\u0227\3\2\2\2\u0227\u0229\t\2\2\2\u0228\u022a\7)\2\2\u0229\u0228\3\2"
          + "\2\2\u0229\u022a\3\2\2\2\u022a\u022b\3\2\2\2\u022b\u0248\5\34\17\t\u022c"
          + "\u022e\f\7\2\2\u022d\u022f\7)\2\2\u022e\u022d\3\2\2\2\u022e\u022f\3\2"
          + "\2\2\u022f\u0230\3\2\2\2\u0230\u0232\t\3\2\2\u0231\u0233\7)\2\2\u0232"
          + "\u0231\3\2\2\2\u0232\u0233\3\2\2\2\u0233\u0234\3\2\2\2\u0234\u0248\5\34"
          + "\17\b\u0235\u0237\f\6\2\2\u0236\u0238\7)\2\2\u0237\u0236\3\2\2\2\u0237"
          + "\u0238\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u023b\t\4\2\2\u023a\u023c\7)"
          + "\2\2\u023b\u023a\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u023d\3\2\2\2\u023d"
          + "\u0248\5\34\17\7\u023e\u0240\f\5\2\2\u023f\u0241\7)\2\2\u0240\u023f\3"
          + "\2\2\2\u0240\u0241\3\2\2\2\u0241\u0242\3\2\2\2\u0242\u0244\7\4\2\2\u0243"
          + "\u0245\7)\2\2\u0244\u0243\3\2\2\2\u0244\u0245\3\2\2\2\u0245\u0246\3\2"
          + "\2\2\u0246\u0248\5\34\17\6\u0247\u0223\3\2\2\2\u0247\u022c\3\2\2\2\u0247"
          + "\u0235\3\2\2\2\u0247\u023e\3\2\2\2\u0248\u024b\3\2\2\2\u0249\u0247\3\2"
          + "\2\2\u0249\u024a\3\2\2\2\u024a\35\3\2\2\2\u024b\u0249\3\2\2\2\u024c\u0251"
          + "\7\'\2\2\u024d\u024e\7\26\2\2\u024e\u024f\5\34\17\2\u024f\u0250\7\27\2"
          + "\2\u0250\u0252\3\2\2\2\u0251\u024d\3\2\2\2\u0252\u0253\3\2\2\2\u0253\u0251"
          + "\3\2\2\2\u0253\u0254\3\2\2\2\u0254\u0257\3\2\2\2\u0255\u0257\7\'\2\2\u0256"
          + "\u024c\3\2\2\2\u0256\u0255\3\2\2\2\u0257\37\3\2\2\2\u0258\u0259\t\5\2"
          + "\2\u0259!\3\2\2\2\u025a\u025b\t\6\2\2\u025b#\3\2\2\2\u025c\u025d\7&\2"
          + "\2\u025d%\3\2\2\2\u0080(.\62\66:UX\\`dhlrvz~\u0082\u0086\u008b\u008f\u0094"
          + "\u0099\u009c\u00a0\u00a4\u00a9\u00ae\u00b3\u00b6\u00bb\u00bf\u00c3\u00c7"
          + "\u00cb\u00cf\u00d4\u00d9\u00dd\u00e1\u00e5\u00e9\u00ed\u00f1\u00f5\u00f9"
          + "\u00fd\u0101\u0105\u0109\u010d\u0112\u0117\u011b\u011f\u0123\u0127\u012b"
          + "\u012f\u0133\u0137\u013b\u013f\u0143\u0147\u014b\u0150\u0155\u0159\u015d"
          + "\u0161\u0165\u016b\u0170\u0174\u0178\u017c\u0180\u0186\u018a\u018e\u0192"
          + "\u0196\u019a\u019e\u01a2\u01a6\u01aa\u01ae\u01b2\u01b6\u01ba\u01be\u01c2"
          + "\u01c6\u01ca\u01ce\u01d3\u01d8\u01dd\u01e2\u01e6\u01ea\u01ee\u01f2\u01f6"
          + "\u01fa\u01fe\u0202\u0206\u020a\u020e\u0212\u0216\u0221\u0225\u0229\u022e"
          + "\u0232\u0237\u023b\u0240\u0244\u0247\u0249\u0253\u0256";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
