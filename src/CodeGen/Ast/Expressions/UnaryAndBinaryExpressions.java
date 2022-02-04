package CodeGen.Ast.Expressions;

import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.DescriptorChecker;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;
import CodeGen.Utils.TypeChecker;

import java.util.Arrays;

public class UnaryAndBinaryExpressions {


    static Descriptor op1, op2;
    static String storeCommand = "sw", loadCommand = "lw", variableName0 = "$f0", variableName1 = "$f1", extention = null;
    public static String continueLabel, afterCompareLabel, variableNameOfContinue;

    public static Type doSomeStuff() {
        op2 = (Descriptor) Stacks.popSemanticS();
        op1 = (Descriptor) Stacks.popSemanticS();
        checkTypes();
        Type resultType = op1.getType();
        prepareCommandToWrite(resultType);
        return resultType;
    }

    public static void checkTypes() {
        TypeChecker.checkType(op1.getType(), op2.getType(), "add");
        if (op1.getIsLocal()) {
            DescriptorChecker.checkContainsDescriptor(op1);
        } else {
            DescriptorChecker.checkContainsDescriptorGlobal(op1);
        }
        if (op2.getIsLocal()) {
            DescriptorChecker.checkContainsDescriptor(op2);
        } else {
            DescriptorChecker.checkContainsDescriptorGlobal(op2);
        }
    }

    public static void prepareCommandToWrite(Type resultType) {
        switch (resultType) {
            case INTEGER_NUMBER:
                extention = "";
                storeCommand = "sw";
                loadCommand = "lw";
                variableName0 = "$t0";
                variableName1 = "$t1";
                break;
            case REAL_NUMBER:
                extention = ".s";
                storeCommand = "s.s";
                loadCommand = "l.s";
                variableName0 = "$f0";
                variableName1 = "$f1";
                break;
            case STRING:
                // extention = "";
                // TODO
                break;
            default:
                resultType = null;
        }
    }

    public static void add() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void sub() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "sub" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void production() {
        Type resultType = doSomeStuff();
        storeCommand = resultType == Type.INTEGER_NUMBER ? "sd" : "s.s";
        String variableName = loadAndOperate(op1, op2, "mul" + extention, storeCommand, loadCommand, variableName0, variableName1);
        SPIMFileWriter.addCommandToCode("mfhi", Arrays.asList("$t1"));
        SPIMFileWriter.addCommandToCode("mflo", Arrays.asList("$t0"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "space", "64");
        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList(variableName0, variableName));
//        AssemblyFileWriter.appendDebugLine(variableName);
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    public static void divide() {
        Type resultType = doSomeStuff();
        String variableName = loadAndOperate(op1, op2, "div" + extention, storeCommand, loadCommand, variableName0, variableName1);

        SPIMFileWriter.addCommandToCode("mfhi", Arrays.asList("$t1"));
        SPIMFileWriter.addCommandToCode("mflo", Arrays.asList("$t0"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList(variableName0, variableName));
//        AssemblyFileWriter.appendDebugLine(variableName);
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    public static void remainder() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.INTEGER_NUMBER && op2.getType() == Type.INTEGER_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "rem", storeCommand, loadCommand, variableName0, variableName1);
        }
//        else
//            throw new TypeError("%", firstOperandDes.getType(), firstOperandDes.getType());

    }

    public static void bitwiseOr() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void logicalOr() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void logicalAnd() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "and", "sw", "lw", variableName0, variableName1);

    }

    public static void bitwiseAnd() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "and", "sw", "lw", variableName0, variableName1);

    }

    public static void bitwiseXor() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "xor", "sw", "lw", variableName0, variableName1);

    }

    public static void nor() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "or", "sw", "lw", variableName0, variableName1);

    }

    public static void equal() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {     //TODO (for all). better: firstOperandDes.getType() == secondOperandDes.getType() == Type.REAL_NUMBER
            generate2OperandCommands(op1, op2, resultType, "c.eq.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "seq", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void notEqual() {
        Type resultType = doSomeStuff();
        generate2OperandCommands(op1, op2, resultType, "sne", "sw", "lw", variableName0, variableName1);

    }

    public static void lessEqual() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "sle", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void greaterEqual() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            generate2OperandCommands(op2, op1, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "sge", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void less() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "slt", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void greater() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            generate2OperandCommands(op2, op1, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            generate2OperandCommands(op1, op2, resultType, "sgt", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void increment() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.INTEGER_NUMBER) {
            generatePlusPlusCommand(op1, resultType, "addi");
        }
//        else
//            throw new TypeError("++", firstOperandDes.getType());
    }

    public static void decrement() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.INTEGER_NUMBER) {
            generateMinusMinusCommand(op1, resultType, "addi");
        }
//        else
//            throw new TypeError("--", firstOperandDes.getType());
//        break;
    }

    public static void not(){
        Type resultType = doSomeStuff();
        //TODO: check if its logical (not int or ...)
        generateNotCommand(op1, resultType, "not");

    }

    public static void negativeSign(){}

    public static void positiveSign(){}

    private static String loadAndOperate(Descriptor op1, Descriptor op2, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + operationCommand + " expression of " + op1.getName() + ", " + op2.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t1", op2.getName()));
        SPIMFileWriter.addCommandToCode(loadCommand, Arrays.asList(variableName0, "0($t0)"));
        SPIMFileWriter.addCommandToCode(loadCommand, Arrays.asList(variableName1, "0($t1)"));
        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList(variableName0, variableName0, variableName1));
        return variableName;
    }

    private static void generate2OperandCommands(Descriptor op1, Descriptor op2, Type resultType, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = loadAndOperate(op1, op2, operationCommand, storeCommand, loadCommand, variableName0, variableName1);

        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");

        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList(variableName0, variableName));

//        AssemblyFileWriter.appendDebugLine(variableName);
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    private static void generateMinusMinusCommand(Descriptor op1, Type resultType, String operationCommand/*, boolean isBeforeExpression*/) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + "--" + " expression of " + op1.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));

        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$t0", "$t0", "-1"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableName));
//        AssemblyFileWriter.appendDebugLine(variableName);
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
        /*if (isBeforeExpression){
        }
        else {
        }*/
    }

    private static void generateNotCommand(Descriptor op1, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + operationCommand + " expression of " + op1.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));

        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$t0", "$t0"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableName));
//        AssemblyFileWriter.appendDebugLine(variableName);
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
//
    }

    private static void generatePlusPlusCommand(Descriptor op1, Type resultType, String operationCommand/*, boolean isBeforeExpression*/) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + "++" + " expression of " + op1.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));

        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$t0", "$t0", "0x1"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableName));
//        AssemblyFileWriter.appendDebugLine(variableName);
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
        /*if (isBeforeExpression){
        }
        else {
        }*/
    }

    private void generateCompare(Descriptor firstOperandDes, Descriptor secondOperandDes) {
        variableNameOfContinue = CodeGeneratorImpl.getVariableName();
        continueLabel = CodeGeneratorImpl.generateNewLabel();
        afterCompareLabel = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addCommentToCode("compare of real");

        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", firstOperandDes.getName()));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t1", secondOperandDes.getName()));
        SPIMFileWriter.addCommandToCode("l.s", Arrays.asList("$f0", "0($t0)"));
        SPIMFileWriter.addCommandToCode("l.s", Arrays.asList("$f1", "0($t1)"));
        SPIMFileWriter.addCommandToCode("c.eq.s", Arrays.asList("$f0", "$f1"));
        SPIMFileWriter.addCommandToCode("bc1t", Arrays.asList(afterCompareLabel));
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$t0", "0"));
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableNameOfContinue));

//        AssemblyFileWriter.appendDebugLine(variableNameOfContinue);
        Stacks.pushSemanticS(new VariableDescriptor(variableNameOfContinue, Type.INTEGER_NUMBER, true));
    }


}
