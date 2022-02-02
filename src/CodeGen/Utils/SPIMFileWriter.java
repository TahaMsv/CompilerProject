package CodeGen.Utils;

import java.util.List;

public class SPIMFileWriter {

    private static String code = "";
    private static String data = "";

    public static void addCommandToCode(String command, List<String> operands) {
        code += ('\t' + command);
        if (operands != null && operands.size() > 0)
            code += (' ' + String.join(", ", operands) + '\n');
    }

    public static void addCommandToDataSegment(String dataName, String directive, String value) {
        data += ('\t' + dataName + ": ." + directive + " " + value);
    }

    public static void addCommentToCode(String comment) {
        code += ('\t' + comment + '\n');
    }
}
