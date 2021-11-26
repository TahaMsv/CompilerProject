
%%

%class Scanner
%public
%unicode
%line
%column
%function nextToken
%type Symbol

%{
    public int ICV = 0;
    public boolean BOOL = false;
    public double REAL = 0;
    public StringBuilder string = new StringBuilder();

    private double getValueOfScientificNumber(String scientific) {
        int index = 0;
        if (scientific.contains("e"))
            index = scientific.indexOf("e");
        else if (scientific.contains("E"))
            index = scientific.indexOf("E");
        double value = 0;
        if (index > 0) {
            double base = Double.valueOf(scientific.substring(0, index));
            int exp = 0;
            if (scientific.charAt(index + 1) == '+')
                exp = Integer.parseInt(scientific.substring(index + 2));
            else if (scientific.charAt(index + 1) == '-')
                exp = Integer.parseInt(scientific.substring(index + 2)) * -1;
            else
                exp = Integer.parseInt(scientific.substring(index + 1));

            value = base * Math.pow(10, exp);
        } else if (index < 0)
            value = Double.parseDouble(scientific);
        else
            throw new NumberFormatException();
        return value;
    }

    private int getValueOfHexadecimalNumber(String hex) {
        String pureHex = hex.charAt(0) == '-' ? "-" + hex.substring(3) : hex.substring(2);
        return Integer.parseInt(pureHex, 16);
    }
%}

Digit = [0-9]
Letter = [a-zA-Z]

PlusSign = "+"
MinusSign = "-"

///////////////Numbers//////////////////////

DecimalInteger = {Digit}+
// ScientificNumber = \b-?[1-9](?:\.\d+)?[Ee][-+]?\d+\b
ScientificNumber = ({RealNumber} | {DecimalInteger}) [eE] ({PlusSign} | {MinusSign})? {DecimalInteger}
// RealNumber = ^(0|(-?(((0|[1-9]\d*)\.\d+)|([1-9]\d*))))$
RealNumber = {Digit}+"."{Digit}*
Hexadecimal = [0][xX][0-9a-fA-F]+

///////////Strings///////////////////////////

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n\"\\]
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
SpecialCharacters = "\\n" | "\\t" | "\\r" | "\\'" |  "\\\"" | ("\\"{2})

////////////////Reserved Keywords///////////
ReservedKeywords = "let" | "void" | "int" | "real" | "bool" | "string" | "static"
                    | "class" | "for" | "rof" | "loop" | "pool" | "while" | "break" 
                    | "continue" | "if" | "fi" | "else" | "then" | "new" | "Array"
                    | "return" | "in_string" | "in_int" | "print" | "len"

%state String
 
%%
<YYINITIAL> {
    "let" {
        return (new Symbol("let"));
    } 
    "void" {
        return (new Symbol("void"));
    }
    "int" {
        return (new Symbol("int", yytext()));
    }
    "real" {
        return (new Symbol("real"));
    }
    "bool" {
        return (new Symbol("bool", yytext()));
    }
    "string" {
        return (new Symbol("string", yytext()));
    }
    "static" {
        return (new Symbol("static"));
    }
    "class" {
        return (new Symbol("class"));
    }
    "for" {
        return (new Symbol("for"));
    }
    "rof" {
        return (new Symbol("rof"));
    }
    "loop" {
        return (new Symbol("loop"));
    }
    "pool" {
        return (new Symbol("pool"));
    }
    "while" {
        return (new Symbol("while"));
    }
    "break" {
        return (new Symbol("break"));
    }
    "continue" {
        return (new Symbol("continue"));
    }
    "if" {
        return (new Symbol("if"));
    }
    "fi" {
        return (new Symbol("fi"));
    }
    "else" {
        return (new Symbol("else"));
    }
    "then" {
        return (new Symbol("then"));
    }
    "new" {
        return (new Symbol("new"));
    }
    "Array" {
        return (new Symbol("Array"));
    }
    "return" {
        return (new Symbol("return"));
    }
    "in_string" {
        return (new Symbol("in_string"));
    }
    "in_int" {
        return (new Symbol("in_int"));
    }
    "print" {
        return (new Symbol("print"));
    }
    "len" {
        return (new Symbol("len"));
    }

    "+" { return (new Symbol("+")); }
    "-" { return (new Symbol("-")); }
    "*" { return (new Symbol("*")); }
    "/" { return (new Symbol("/")); }
    "%" { return (new Symbol("%")); }
    "+=" { return (new Symbol("+=")); }
    "*=" { return (new Symbol("*=")); }
    "-=" { return (new Symbol("-=")); }
    "/=" { return (new Symbol("/=")); }
    "++" { return (new Symbol("++")); }
    "--" { return (new Symbol("--")); }
    "<" { return (new Symbol("<")); }
    "<=" { return (new Symbol("<=")); }
    ">" { return (new Symbol(">")); }
    ">=" { return (new Symbol(">=")); }
    "!=" { return (new Symbol("!=")); }
    "==" { return (new Symbol("==")); }
    "=" { return (new Symbol("=")); }
    "&&" { return (new Symbol("&&")); }
    "&" { return (new Symbol("&")); }
    "||" { return (new Symbol("|")); }
    "^" { return (new Symbol("^")); }
    "." { return (new Symbol(".")); }
    "“" { return (new Symbol("“")); }
    "!" { return (new Symbol("!")); }
    "," { return (new Symbol(",")); }
    ";" { return (new Symbol(";")); }
    "[" { return (new Symbol("[")); }
    "]" { return (new Symbol("]")); }
    "(" { return (new Symbol("(")); }
    ")" { return (new Symbol(")")); }
    "{" { return (new Symbol("{")); }
    "}" { return (new Symbol("}")); }

    {DecimalInteger} {
        ICV = Integer.parseInt(yytext());
        return new Symbol("decimal", ICV);
    }

    {Hexadecimal} {
        ICV = getValueOfHexadecimalNumber(yytext());
        return new Symbol("hexadecimal", ICV);
    }

    {RealNumber} {
        REAL = Double.valueOf(yytext());
        return new Symbol("realNumber", REAL);
    }

    {ScientificNumber} {
        REAL = getValueOfScientificNumber(yytext());
        return new Symbol("scientificNotation", REAL);
    }

    {Comment} { 
        return new Symbol("comment", yytext());   
    }

    {LineTerminator} {
        return new Symbol("lineTerminate");
    }

    {WhiteSpace} {
        /* ignore */
    }

    {Identifier} {
        return new Symbol("identifier", yytext());
    }

    \" {
        yybegin(String);
        string.setLength(0);
        string.append("\"");
    }

    [^] { 
        throw new RuntimeException("Illegal character \"" + yytext() + "\" at line "+yyline+", column " + yycolumn); 
    }
}

<String> {
    {InputCharacter}+ {
        string.append(yytext());
    }

    {SpecialCharacters} {
        string.append(yytext());
        return new Symbol("specialCharacter", yytext());
    }

    \" {
        yybegin(YYINITIAL);
        string.append("\"");
        StringBuilder temp = string;
        string = new StringBuilder();
        return new Symbol("stringLiteral", temp.toString());
    }
}

[^] { 
        throw new RuntimeException("Illegal character \"" + yytext() + "\" at line "+yyline+", column " + yycolumn); 
}

<<EOF>>    {return (new Symbol("$"));}