package CodeGen.Ast.Statements;


import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.SPIMFileWriter;

import java.util.Arrays;

public class If {
    private Descriptor value;
    private static String afterIfLabel;
    private static String afterElseLabel;

    public If(Descriptor value) {
        this.value = value;
    }

    public void startIf() {
        afterIfLabel = CodeGeneratorImpl.generateNewLabel();
        afterElseLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", value.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t1", "0($t0)"));
        SPIMFileWriter.addCommandToCode("beqz", Arrays.asList("$t1", afterIfLabel));
    }

    public static void completeIf() {
        SPIMFileWriter.addCommandToCode("j", Arrays.asList(afterElseLabel));
        SPIMFileWriter.addLabel(afterIfLabel);
        SPIMFileWriter.addLabel(afterElseLabel);
    }

    public static void elseCode() {
        SPIMFileWriter.deleteLabel(afterIfLabel);
        SPIMFileWriter.addLabel(afterIfLabel);
    }

    public static void completeElse() {
        SPIMFileWriter.deleteLabel(afterElseLabel);
        SPIMFileWriter.addLabel(afterElseLabel);
    }
}
