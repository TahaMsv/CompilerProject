import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainScanner {
    public final static String RESERVED_KEYWORDS = "Reserved Keywords";
    public final static String IDENTIFIERS = "Identifiers";
    public final static String INTEGER_NUMBERS = "Integer Numbers";
    public final static String REAL_NUMBERS = "Real Numbers";
    public final static String STRINGS = "Strings";
    public final static String SPECIAL_CHARACTERS = "Special Characters";
    public final static String COMMENTS = "Comments";
    public final static String OPERATORS_AND_PUNCTUATIONS = "Operators and Punctuations";
    public final static String UNDEFINED_TOKEN = "Undefined Token";
    public final static String LINE_TERMINATOR = "Line Terminator";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    //used for check the type

    public static List<String> keywordsArray = new ArrayList<String>(
            Arrays.asList("let", "void", "int", "real", "bool", "string", "static",
                    "class", "for", "rof", "loop", "pool", "while", "break", "continue", "if", "fi", "else",
                    "then", "new", "Array", "return", "in_string", "in_int", "print", "len"));
    public static List<String> operatorsArray = new ArrayList<String>(
            Arrays.asList("+", "-", "*", "/", "%", "+=", "*=",
                    "-=", "/=", "++", "--", "<", "<=",
                    ">", ">=", "!=", "==", "=", "&&", "&", "||", "^", ".", "“",
                    "!", ",", ";", "[", "]", "(", ")", "{", "}"));


    public static void main(String[] args) throws IOException {
//        SyntaxHighlighter syntaxHighlighter = new SyntaxHighlighter();
        LexicalScanner input = new LexicalScanner(new FileReader("src/testInput.txt"));
        while (true) {
            Symbol currentToken = input.currentSymbol();
            if (input.yyatEOF()) {
                break;
            }
            if (!currentToken.getToken().startsWith("Error")) {
//                syntaxHighlighter.addHtmlText(currentToken.toString(), getTokenType(currentToken.getToken()));
            }
            else {
                System.out.println(ANSI_RED + currentToken.toString() + ANSI_RESET);
            }
//            syntaxHighlighter.writeToHtml(syntaxHighlighter.getDocument().outerHtml(),"src/OutputHtmlHighlighter.html");
        }
    }

    private static String getTokenType(String tokenValue) {
        if (keywordsArray.contains(tokenValue)) {
            return RESERVED_KEYWORDS;
        }
        if (operatorsArray.contains(tokenValue)) {
            return OPERATORS_AND_PUNCTUATIONS;
        }
        switch (tokenValue) {
            case "identifier":
                return IDENTIFIERS;
            case "decimal":
            case "hexadecimal":
                return INTEGER_NUMBERS;
            case "realNumber":
            case "scientificNotation":
                return REAL_NUMBERS;
            case "stringLiteral":
                return STRINGS;
            case "specialCharacter":
                return SPECIAL_CHARACTERS;
            case "comment":
                return COMMENTS;
            case "lineTerminate":
                return LINE_TERMINATOR;
            default:
                return UNDEFINED_TOKEN;
        }
    }
}
