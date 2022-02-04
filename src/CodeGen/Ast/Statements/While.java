package CodeGen.Ast.Statements;


import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.SPIMFileWriter;

import java.util.Arrays;

public class While {
    private Descriptor conditionValue;
    private static String startOfConditionLabel;
    private static String endOfWhileLabel;

    public While(Descriptor conditionValue) {
        this.conditionValue = conditionValue;
    }

    public void startWhileLoop() {
        SPIMFileWriter.addCommentToCode("while code for " + conditionValue);
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", conditionValue.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t1", "0($t0)"));
        endOfWhileLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addCommandToCode("beqz", Arrays.asList("$t1", endOfWhileLabel));
    }

    public static void startCondition() {
        startOfConditionLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addLabel(startOfConditionLabel);
    }

    public static void completeWhile() {
        SPIMFileWriter.addCommandToCode("j", Arrays.asList(startOfConditionLabel));
        SPIMFileWriter.addLabel(endOfWhileLabel);
    }

}
