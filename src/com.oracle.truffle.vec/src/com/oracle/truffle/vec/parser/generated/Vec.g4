grammar Vec;

parse
	: block WS? EOF         #blockParse
	| forStmt WS? EOF       #forParse
	;

block
	: (stmt WS?)*
	;

stmt
	: assignment SCOL            #assignStatement
	| ifStmt                     #ifStatement
	| forStmt                    #forStatement
	| simdDoubleFmadd SCOL       #simdDoubleFmaddStatement
	| matmulKernel8x16 SCOL      #matmulKernel8x16Statement
	| matmulKernel2x8 SCOL       #matmulKernel2x8Statement
	| matmulKernel1D2x8 SCOL     #matmulKernel1D2x8Statement
	| gotoKernel8x8 SCOL         #gotoKernel8x8Statement
    | convKernel SCOL            #convKernelStatement
	| declarativeLoop            #declarativeLoopStatement
	;

ifStmt
    : WS? IF WS? LPAREN WS? expr WS? RPAREN WS? LBRACE WS? block RBRACE
    ;

forStmt
    : WS? FOR WS? LPAREN WS? assignment WS? SCOL WS? expr WS? SCOL assignment WS? RPAREN WS? LBRACE block WS? RBRACE
    ;


assignment
    : WS? DEC? var WS? assignop WS? expr  #equalAssn
    | WS? UNARY_OP var                    #unaryAssnFront
    | WS? var UNARY_OP                    #unaryAssnBack
    ;

simdDoubleFmadd
    : WS? SIMDDOUBLEFMADD LPAREN WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? RPAREN
    ;

matmulKernel8x16
    : WS? MATMULKERNEL8X16 LPAREN WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? RPAREN
    ;

matmulKernel2x8
    : WS? MATMULKERNEL2X8 LPAREN WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? RPAREN
    ;

gotoKernel8x8
    : WS? GOTOKERNEL8X8 LPAREN WS? STRING WS? (COMMA WS? expr WS?)* RPAREN
    ;

convKernel
    : WS? CONVKERNEL LPAREN WS? STRING WS? (COMMA WS? expr WS?)* RPAREN
    ;

declarativeLoop
    : WS? WHERE WS? LPAREN WS? var WS? IN WS? LBRACKET WS? expr WS? RANGE WS? expr WS? RBRACKET (WS? DECLAND WS? var WS? IN WS? LBRACKET WS? expr WS? RANGE WS? expr WS? RBRACKET WS?)* RPAREN WS? LBRACE block WS? RBRACE
    ;

matmulKernel1D2x8
    : WS? MATMULKERNEL1D2X8 LPAREN WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? COMMA WS? expr WS? RPAREN
    ;

expr
	: LPAREN expr RPAREN                  #parenExpr
    | expr WS? op=(MULTIPLY|MODULO) WS? expr             #relationalExpr
    | expr WS? op=(PLUS|MINUS) WS? expr             #relationalExpr
    | expr WS? op=(LESSTHANOREQUAL|GREATERTHANOREQUAL|LESSTHAN|GREATERTHAN) WS? expr             #relationalExpr
    | expr WS? AND WS? expr               #andExpr
	| var                                 #varExpr
	| atom                                #atomExpr
	;

var
    : ID (LBRACKET expr RBRACKET)+       #arrayVar
    | ID                                 #idVar
    ;

UNARY_OP
    : '++'
    | '--'
    ;

AND : '&&';
DECLAND : 'and';

binop
	: LESSTHANOREQUAL
	| GREATERTHANOREQUAL
	| LESSTHAN
	| GREATERTHAN
	| PLUS
	| MINUS
	| MULTIPLY
	| MODULO
	;

assignop
    : ASSIGN
    | ADDASSIGN
    | SUBASSIGN
    | MULASSIGN
    ;

atom
    : INT            #intAtom
    ;

SIMDDOUBLEFMADD : 'simdDoubleFmadd';
MATMULKERNEL8X16 : 'matmulKernel8x16';
MATMULKERNEL2X8 : 'matmulKernel2x8';
GOTOKERNEL8X8 : 'gotoKernel8x8';
MATMULKERNEL1D2X8 : 'matmulKernel1D2x8';
CONVKERNEL : 'convKernel';

IF : 'if';
FOR : 'for';
WHERE : 'where';
IN : 'in';
RANGE : '..';
DEC : 'var' WS;
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACKET : '[';
RBRACKET : ']';
COMMA : ',';
LESSTHANOREQUAL : '<=';
GREATERTHANOREQUAL : '>=';
LESSTHAN : '<';
GREATERTHAN : '>';
ADDASSIGN : '+=';
SUBASSIGN : '-=';
MULASSIGN : '*=';
PLUS : '+';
MINUS : '-';
MULTIPLY : '*';
MODULO : '%';

ASSIGN : '=';
SCOL : ';';

INT : [0-9]+;
ID : [a-zA-Z_][a-zA-Z_0-9]*;
STRING : '"' (ESC|.)*? '"';
fragment ESC : '\\"' | '\\\\' ;

WS : (' '|'\t'|'\r'|'\n')+;