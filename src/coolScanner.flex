
%%

%class Scanner
%public
%unicode
%line
%column
%function nextToken
%type String

%{
    public int ICV = 0;
    public boolean BOOL = false;
    public String HEX;
    public double REAL = 0;
    public StringBuffer string = new StringBuffer();
%}

Digit = [0-9]
Letter = [a-zA-Z]

/////////////////Signes//////////////////
PlusSign = "+"
MinusSign = "-"
AddSign = "+"
UnaryMinusSign = "-"
ProductionSign = "*"
DivisionSign ="/"
ModSign = "%"
AdditionassignmentSign = "+="
ProductionassignmentSign = "*="
subtractionAssignmentSign = "-="
DivisionAssignmentSign = "/="
IncrementSign = "++"
Decrement = "--"
LessSign = "<"
LessEqualSign = "<="
GreaterSign = ">"
GreaterEqualSign = ">="
NotEqualSign = "!="
EqualSign = "=="
AssignmentSign = "="
LogicalAndSign = "&&"
BitWiseAndSign = "&"
LogicalOrSign = "||"
BitwiseORSign = "|"
BitwiseXorSign = "^"
DotSign = "."
StringLiteralSign = "“"
NotSign = "!"
ColonSign = ","
SemiColonSign = ";"
OpeningBracesSign = "["
ClosingBracesSign = "]" 
OpeningParenthesisSign = "("
closingparenthesisSign = ")"
OpeningCurlyBracesSign = "{"
ClosingCurlyBracesSign = "}"


///////////////Numbers//////////////////////

DecimalInteger = {Digit}+
// ScientificNumber = \b-?[1-9](?:\.\d+)?[Ee][-+]?\d+\b
ScientificNumber = (RealNumber | DecimalInteger) [eE] (PlusSign | MinusSign) {DecimalInteger}
// RealNumber = ^(0|(-?(((0|[1-9]\d*)\.\d+)|([1-9]\d*))))$
RealNumber = {Digit}+ "." {Digit}*
Hexadecimal = [0][xX][0-9a-fA-F]+

///////////Strings///////////////////////////
StringConstant = (\".*?[^\\]\")

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

//////////////CommentSection///////////////
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

///////////////Identifire///////////////////
Identifier = {Letter} ([a-zA-Z0-9_]){0,30}


///////////////Special Characters (Escape Characters)/////
SpecialCharacters = "\\n" | "\\t" | "\\r" | "\\’" ||  "\\”" || ("\\"{2})

////////////////Reserved Keywords///////////
ReservedKeywords = "let" | "void" | "int" | "real" | "bool" | "string" | "static"
                    | "class" | "for" | "rof" | "loop" | "pool" | "while" | "break" 
                    | "continue" | "if" | "fi" | "else" | "then" | "new" | "Array"
                    | "return" | "in_string" | "in_int" | "print" | "len"

///////////////Operators And Punctuation/////////
OperatorsAndPunctuation={AddSign} | {UnaryMinusSign} | {ProductionSign} | {DivisionSign} | {ModSign} 
    | {AdditionassignmentSign} | {ProductionassignmentSign} | {subtractionAssignmentSign} |{DivisionAssignmentSign} 
    | {IncrementSign} | {Decrement} | {LessSign} | {LessEqualSign} | {GreaterSign} | {GreaterEqualSign} 
    | {NotEqualSign} | {EqualSign} | {AssignmentSign} | {LogicalAndSign} | {BitWiseAndSign} 
    | {LogicalOrSign} | {BitwiseORSign} | {BitwiseXorSign} | {DotSign} | {StringLiteralSign} | {NotSign} | {ColonSign} | {SemiColonSign} 
    | {OpeningBracesSign} | {ClosingBracesSign} | {OpeningParenthesisSign} | {closingparenthesisSign} |
    | {OpeningCurlyBracesSign} | {ClosingCurlyBracesSign}