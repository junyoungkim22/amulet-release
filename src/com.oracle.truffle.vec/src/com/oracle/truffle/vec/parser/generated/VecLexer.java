package com.oracle.truffle.parser.generated;
// Generated from Vec.g4 by ANTLR 4.7
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VecLexer extends Lexer {
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
  public static String[] channelNames = {"DEFAULT_TOKEN_CHANNEL", "HIDDEN"};

  public static String[] modeNames = {"DEFAULT_MODE"};

  public static final String[] ruleNames = {
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
    "ESC",
    "WS"
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

  public VecLexer(CharStream input) {
    super(input);
    _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
  public String[] getChannelNames() {
    return channelNames;
  }

  @Override
  public String[] getModeNames() {
    return modeNames;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public static final String _serializedATN =
      "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2)\u0123\b\1\4\2\t"
          + "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
          + "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
          + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
          + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
          + "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\3\2\3"
          + "\2\5\2X\n\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"
          + "\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"
          + "\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"
          + "\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"
          + "\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"
          + "\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"
          + "\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3"
          + "\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3"
          + "\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3"
          + "\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3"
          + " \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\6%\u0104\n%\r%\16%\u0105\3&\3&\7&\u010a"
          + "\n&\f&\16&\u010d\13&\3\'\3\'\3\'\7\'\u0112\n\'\f\'\16\'\u0115\13\'\3\'"
          + "\3\'\3(\3(\3(\3(\5(\u011d\n(\3)\6)\u0120\n)\r)\16)\u0121\3\u0113\2*\3"
          + "\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37"
          + "\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37="
          + " ?!A\"C#E$G%I&K\'M(O\2Q)\3\2\6\3\2\62;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13"
          + "\f\17\17\"\"\2\u0128\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"
          + "\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"
          + "\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"
          + "\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"
          + "\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2"
          + "\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2"
          + "\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2Q\3\2\2\2\3W"
          + "\3\2\2\2\5Y\3\2\2\2\7\\\3\2\2\2\t`\3\2\2\2\13p\3\2\2\2\r\u0081\3\2\2\2"
          + "\17\u0091\3\2\2\2\21\u009f\3\2\2\2\23\u00b1\3\2\2\2\25\u00bc\3\2\2\2\27"
          + "\u00bf\3\2\2\2\31\u00c3\3\2\2\2\33\u00c9\3\2\2\2\35\u00cc\3\2\2\2\37\u00cf"
          + "\3\2\2\2!\u00d5\3\2\2\2#\u00d7\3\2\2\2%\u00d9\3\2\2\2\'\u00db\3\2\2\2"
          + ")\u00dd\3\2\2\2+\u00df\3\2\2\2-\u00e1\3\2\2\2/\u00e3\3\2\2\2\61\u00e6"
          + "\3\2\2\2\63\u00e9\3\2\2\2\65\u00eb\3\2\2\2\67\u00ed\3\2\2\29\u00f0\3\2"
          + "\2\2;\u00f3\3\2\2\2=\u00f6\3\2\2\2?\u00f8\3\2\2\2A\u00fa\3\2\2\2C\u00fc"
          + "\3\2\2\2E\u00fe\3\2\2\2G\u0100\3\2\2\2I\u0103\3\2\2\2K\u0107\3\2\2\2M"
          + "\u010e\3\2\2\2O\u011c\3\2\2\2Q\u011f\3\2\2\2ST\7-\2\2TX\7-\2\2UV\7/\2"
          + "\2VX\7/\2\2WS\3\2\2\2WU\3\2\2\2X\4\3\2\2\2YZ\7(\2\2Z[\7(\2\2[\6\3\2\2"
          + "\2\\]\7c\2\2]^\7p\2\2^_\7f\2\2_\b\3\2\2\2`a\7u\2\2ab\7k\2\2bc\7o\2\2c"
          + "d\7f\2\2de\7F\2\2ef\7q\2\2fg\7w\2\2gh\7d\2\2hi\7n\2\2ij\7g\2\2jk\7H\2"
          + "\2kl\7o\2\2lm\7c\2\2mn\7f\2\2no\7f\2\2o\n\3\2\2\2pq\7o\2\2qr\7c\2\2rs"
          + "\7v\2\2st\7o\2\2tu\7w\2\2uv\7n\2\2vw\7M\2\2wx\7g\2\2xy\7t\2\2yz\7p\2\2"
          + "z{\7g\2\2{|\7n\2\2|}\7:\2\2}~\7z\2\2~\177\7\63\2\2\177\u0080\78\2\2\u0080"
          + "\f\3\2\2\2\u0081\u0082\7o\2\2\u0082\u0083\7c\2\2\u0083\u0084\7v\2\2\u0084"
          + "\u0085\7o\2\2\u0085\u0086\7w\2\2\u0086\u0087\7n\2\2\u0087\u0088\7M\2\2"
          + "\u0088\u0089\7g\2\2\u0089\u008a\7t\2\2\u008a\u008b\7p\2\2\u008b\u008c"
          + "\7g\2\2\u008c\u008d\7n\2\2\u008d\u008e\7\64\2\2\u008e\u008f\7z\2\2\u008f"
          + "\u0090\7:\2\2\u0090\16\3\2\2\2\u0091\u0092\7i\2\2\u0092\u0093\7q\2\2\u0093"
          + "\u0094\7v\2\2\u0094\u0095\7q\2\2\u0095\u0096\7M\2\2\u0096\u0097\7g\2\2"
          + "\u0097\u0098\7t\2\2\u0098\u0099\7p\2\2\u0099\u009a\7g\2\2\u009a\u009b"
          + "\7n\2\2\u009b\u009c\7:\2\2\u009c\u009d\7z\2\2\u009d\u009e\7:\2\2\u009e"
          + "\20\3\2\2\2\u009f\u00a0\7o\2\2\u00a0\u00a1\7c\2\2\u00a1\u00a2\7v\2\2\u00a2"
          + "\u00a3\7o\2\2\u00a3\u00a4\7w\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7M\2\2"
          + "\u00a6\u00a7\7g\2\2\u00a7\u00a8\7t\2\2\u00a8\u00a9\7p\2\2\u00a9\u00aa"
          + "\7g\2\2\u00aa\u00ab\7n\2\2\u00ab\u00ac\7\63\2\2\u00ac\u00ad\7F\2\2\u00ad"
          + "\u00ae\7\64\2\2\u00ae\u00af\7z\2\2\u00af\u00b0\7:\2\2\u00b0\22\3\2\2\2"
          + "\u00b1\u00b2\7e\2\2\u00b2\u00b3\7q\2\2\u00b3\u00b4\7p\2\2\u00b4\u00b5"
          + "\7x\2\2\u00b5\u00b6\7M\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7t\2\2\u00b8"
          + "\u00b9\7p\2\2\u00b9\u00ba\7g\2\2\u00ba\u00bb\7n\2\2\u00bb\24\3\2\2\2\u00bc"
          + "\u00bd\7k\2\2\u00bd\u00be\7h\2\2\u00be\26\3\2\2\2\u00bf\u00c0\7h\2\2\u00c0"
          + "\u00c1\7q\2\2\u00c1\u00c2\7t\2\2\u00c2\30\3\2\2\2\u00c3\u00c4\7y\2\2\u00c4"
          + "\u00c5\7j\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7\7t\2\2\u00c7\u00c8\7g\2\2"
          + "\u00c8\32\3\2\2\2\u00c9\u00ca\7k\2\2\u00ca\u00cb\7p\2\2\u00cb\34\3\2\2"
          + "\2\u00cc\u00cd\7\60\2\2\u00cd\u00ce\7\60\2\2\u00ce\36\3\2\2\2\u00cf\u00d0"
          + "\7x\2\2\u00d0\u00d1\7c\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\3\2\2\2\u00d3"
          + "\u00d4\5Q)\2\u00d4 \3\2\2\2\u00d5\u00d6\7*\2\2\u00d6\"\3\2\2\2\u00d7\u00d8"
          + "\7+\2\2\u00d8$\3\2\2\2\u00d9\u00da\7}\2\2\u00da&\3\2\2\2\u00db\u00dc\7"
          + "\177\2\2\u00dc(\3\2\2\2\u00dd\u00de\7]\2\2\u00de*\3\2\2\2\u00df\u00e0"
          + "\7_\2\2\u00e0,\3\2\2\2\u00e1\u00e2\7.\2\2\u00e2.\3\2\2\2\u00e3\u00e4\7"
          + ">\2\2\u00e4\u00e5\7?\2\2\u00e5\60\3\2\2\2\u00e6\u00e7\7@\2\2\u00e7\u00e8"
          + "\7?\2\2\u00e8\62\3\2\2\2\u00e9\u00ea\7>\2\2\u00ea\64\3\2\2\2\u00eb\u00ec"
          + "\7@\2\2\u00ec\66\3\2\2\2\u00ed\u00ee\7-\2\2\u00ee\u00ef\7?\2\2\u00ef8"
          + "\3\2\2\2\u00f0\u00f1\7/\2\2\u00f1\u00f2\7?\2\2\u00f2:\3\2\2\2\u00f3\u00f4"
          + "\7,\2\2\u00f4\u00f5\7?\2\2\u00f5<\3\2\2\2\u00f6\u00f7\7-\2\2\u00f7>\3"
          + "\2\2\2\u00f8\u00f9\7/\2\2\u00f9@\3\2\2\2\u00fa\u00fb\7,\2\2\u00fbB\3\2"
          + "\2\2\u00fc\u00fd\7\'\2\2\u00fdD\3\2\2\2\u00fe\u00ff\7?\2\2\u00ffF\3\2"
          + "\2\2\u0100\u0101\7=\2\2\u0101H\3\2\2\2\u0102\u0104\t\2\2\2\u0103\u0102"
          + "\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106"
          + "J\3\2\2\2\u0107\u010b\t\3\2\2\u0108\u010a\t\4\2\2\u0109\u0108\3\2\2\2"
          + "\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010cL\3"
          + "\2\2\2\u010d\u010b\3\2\2\2\u010e\u0113\7$\2\2\u010f\u0112\5O(\2\u0110"
          + "\u0112\13\2\2\2\u0111\u010f\3\2\2\2\u0111\u0110\3\2\2\2\u0112\u0115\3"
          + "\2\2\2\u0113\u0114\3\2\2\2\u0113\u0111\3\2\2\2\u0114\u0116\3\2\2\2\u0115"
          + "\u0113\3\2\2\2\u0116\u0117\7$\2\2\u0117N\3\2\2\2\u0118\u0119\7^\2\2\u0119"
          + "\u011d\7$\2\2\u011a\u011b\7^\2\2\u011b\u011d\7^\2\2\u011c\u0118\3\2\2"
          + "\2\u011c\u011a\3\2\2\2\u011dP\3\2\2\2\u011e\u0120\t\5\2\2\u011f\u011e"
          + "\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122"
          + "R\3\2\2\2\n\2W\u0105\u010b\u0111\u0113\u011c\u0121\2";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
