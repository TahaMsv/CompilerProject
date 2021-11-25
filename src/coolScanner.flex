
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
DecimalInteger = 0 | {Digit}*
ScientificNumber = \b-?[1-9](?:\.\d+)?[Ee][-+]?\d+\b
RealNumber = ^(0|(-?(((0|[1-9]\d*)\.\d+)|([1-9]\d*))))$
Hexadecimal = [0][xX][0-9a-fA-F]+
StringConstant = (\".*?[^\\]\")

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

//CommentSection
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*

