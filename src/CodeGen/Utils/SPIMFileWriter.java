package CodeGen.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SPIMFileWriter {
    private String filePath;
    private static BufferedWriter writer;
    private static String OUTPUT_ASSEMBLY_FILE_NAME = "compiled_code.s";
    private static String code = "";
    private static String data = "";

    public SPIMFileWriter(String filePath) {
        this.filePath = filePath;
        createCompiledFile();
    }

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

    public static void addLabel(String name) {
        code += name + ":\n" ;
    }

    public static void deleteLabel(String label) {
        code = code.replaceFirst(label + ":", "");
    }


    private void createCompiledFile() {
        try {
            writer = new BufferedWriter(new FileWriter(filePath + OUTPUT_ASSEMBLY_FILE_NAME));
            code += ".text" + '\n';
            //TODO check if input file has main
            code += ".globl main" + '\n';
            code += "main:" + '\n';
            data += ".data" + '\n';
            addCommandToDataSegment("nl", "asciiz", "\"\\n\"");
            addCommandToDataSegment("strbuffer", "space", "20");
            addCommandToDataSegment("stradr", "word", "0");
        } catch (IOException e) {
            System.err.println("Can not create output file");
            e.printStackTrace();
        }
    }

    public void writeOutputFile() {
        try {
            writer.write(code);
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Can not write to output file");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Can not close output file");
            e.printStackTrace();
        }
    }
}
