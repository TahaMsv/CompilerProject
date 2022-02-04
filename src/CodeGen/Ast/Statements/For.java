package CodeGen.Ast.Statements;

import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.SPIMFileWriter;

import java.util.Arrays;

public class For {
    private Descriptor conditionValue;
    private static String startOfConditionLabel;
    private static String endOfForLabel;
    private static String startOfStepLabel;

    public For(Descriptor conditionValue) {
        this.conditionValue = conditionValue;
    }


    public void startForLoop() {
        SPIMFileWriter.addCommentToCode("FOR code for " + conditionValue);
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", conditionValue.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t1", "0($t0)"));
        endOfForLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addCommandToCode("beqz", Arrays.asList("$t1", endOfForLabel));

    }

    public static void startCondition() {
        startOfConditionLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addLabel(startOfConditionLabel);
    }

    public static void completeFor() {
        startOfStepLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addCommandToCode("j", Arrays.asList(startOfStepLabel));
    }

    public static void stepStatement() {
        SPIMFileWriter.addLabel(startOfStepLabel);
    }

    public static void completeStepOfFor() {
        SPIMFileWriter.addCommandToCode("j", Arrays.asList(startOfConditionLabel));
        SPIMFileWriter.addLabel(endOfForLabel);
    }
}
