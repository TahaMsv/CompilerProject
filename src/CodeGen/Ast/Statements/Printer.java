package CodeGen.Ast.Statements;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;

import java.util.Arrays;
import java.util.Collections;

public class Printer {

    public void print(Descriptor value) {
        String outputType = "1";  // int
        String comment = "Int";
        switch (value.getType()) {
            case INTEGER_NUMBER:
                outputType = "1";  // int
                comment = "Int";
                break;
            case REAL_NUMBER:
                outputType = "2";  //float(Real)
                comment = "Real";
                break;
            case STRING:
                outputType = "4"; //String
                comment = "String";
                break;
            default:
                outputType = "4"; //String
                comment = "String";
                break;
        }
        SPIMFileWriter.addCommentToCode("Print " + comment + " (Value: " + value + ")");
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$v0", outputType));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", value.getName()));

        if (value.getType() == Type.REAL_NUMBER) {
            SPIMFileWriter.addCommandToCode("l.s", Arrays.asList("$f0", "0($t0)"));
            SPIMFileWriter.addCommandToCode("mov.s", Arrays.asList("$f12", "$f0"));
        } else {
            SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));
            SPIMFileWriter.addCommandToCode("move", Arrays.asList("$a0", "$t0"));
        }
        SPIMFileWriter.addCommandToCode("syscall", Collections.emptyList());
        if (value.getType() == Type.REAL_NUMBER || value.getType() == Type.INTEGER_NUMBER) {
            SPIMFileWriter.addCommentToCode("new line");
            SPIMFileWriter.addCommandToCode("li", Arrays.asList("$v0", "4"));
            SPIMFileWriter.addCommandToCode("la", Arrays.asList("$a0", "nl"));
            SPIMFileWriter.addCommandToCode("syscall", Collections.emptyList());
        }
    }
}
