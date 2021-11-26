import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    //used for check the type

    public static String[] keywordsArray = {"let", "void", "int", "real", "bool", "string", "static",
            "class", "for", "rof", "loop", "pool", "while", "break", "continue", "if", "fi", "else",
            "then", "new", "Array", "return", "in_string", "in_int", "print", "len"};
    public static String[] operatorsArray = {"+", "-", "*", "/", "%", "+=", "*=",
            "-=", "/=", "++", "--", "<", "<=",
            ">", ">=", "!=", "==", "=", "&&", "&", "||", "^", ".", "“",
            "!", ",", ";", "[", "]", "(", ")", "{", "}"};

    public static void main(String[] args) throws IOException {
        List<String> keywords = Arrays.asList(keywordsArray);
        List<String> operators = Arrays.asList(operatorsArray);

        Scanner input = new Scanner(new FileReader("src/testInput.txt"));
        while (true) {
            Symbol currentToken = input.nextToken();
            if (currentToken.getToken().equals("$")) {
                break;
            }
            if (!currentToken.getToken().startsWith("Illegal")) {
            }
            System.out.println(currentToken);
        }
    }
}
