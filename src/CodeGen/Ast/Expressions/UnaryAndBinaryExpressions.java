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


    public static Descriptor op1, op2;
    static String storeCommand = "sw", loadCommand = "lw", variableName0 = "$f0", variableName1 = "$f1", extention = null;
    public static String continueLabel, afterCompL, continueVName;

    public static Type doSomeStuff() {
        op2 = (Descriptor) Stacks.popSemanticS();
        op1 = (Descriptor) Stacks.popSemanticS();
        checkTypes();
        Type resultType = op1.getType();
        prepareCommandToWrite(resultType);
        return resultType;
    }

    public static void checkTypes() {
        TypeChecker.checkType(op1.getType(), op2.getType());
        if (op1.getIsLocal()) {
            DescriptorChecker.IsInSymbolTable(op1, false);
        } else {
            DescriptorChecker.IsInSymbolTable(op1, true);
        }
        if (op2.getIsLocal()) {
            DescriptorChecker.IsInSymbolTable(op2, false);
        } else {
            DescriptorChecker.IsInSymbolTable(op2, true);
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

    public static void add(boolean isAddAssign) {
        Type resultType = doSomeStuff();
        if (isAddAssign)
            Stacks.pushSemanticS(op1);
        if (op1.getType() == Type.STRING || op2.getType() == Type.STRING) {
            throw new RuntimeException("Error message: Type error");
        }
        twoOperandsInstruction(op1, op2, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void sub(boolean isSunAssign) {
        Type resultType = doSomeStuff();
        if (isSunAssign)
            Stacks.pushSemanticS(op1);
        twoOperandsInstruction(op1, op2, resultType, "sub" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void production(boolean isProductionAssign) {
        Type resultType = doSomeStuff();
        if (isProductionAssign)
            Stacks.pushSemanticS(op1);
        storeCommand = resultType == Type.INTEGER_NUMBER ? "sd" : "s.s";
        String variableName = loadAndOperate(op1, op2, "mul" + extention, storeCommand, loadCommand, variableName0, variableName1);
        SPIMFileWriter.addCommandToCode("mfhi", Arrays.asList("$t1"));
        SPIMFileWriter.addCommandToCode("mflo", Arrays.asList("$t0"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "space", "64");
        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList(variableName0, variableName));
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    public static void divide(boolean isDivideAssign) {
        Type resultType = doSomeStuff();
        if (isDivideAssign)
            Stacks.pushSemanticS(op1);
        String variableName = loadAndOperate(op1, op2, "div" + extention, storeCommand, loadCommand, variableName0, variableName1);

        SPIMFileWriter.addCommandToCode("mfhi", Arrays.asList("$t1"));
        SPIMFileWriter.addCommandToCode("mflo", Arrays.asList("$t0"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList(variableName0, variableName));
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    public static void remainder() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.INTEGER_NUMBER && op2.getType() == Type.INTEGER_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "rem", storeCommand, loadCommand, variableName0, variableName1);
        }
//        else
//            throw new TypeError("%", firstOperandDes.getType(), firstOperandDes.getType());

    }

    public static void bitwiseOr() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void logicalOr() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);

    }

    public static void logicalAnd() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "and", "sw", "lw", variableName0, variableName1);

    }

    public static void bitwiseAnd() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "and", "sw", "lw", variableName0, variableName1);

    }

    public static void bitwiseXor() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "xor", "sw", "lw", variableName0, variableName1);

    }

    public static void nor() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "or", "sw", "lw", variableName0, variableName1);

    }

    public static void equal() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "c.eq.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "seq", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void notEqual() {
        Type resultType = doSomeStuff();
        twoOperandsInstruction(op1, op2, resultType, "sne", "sw", "lw", variableName0, variableName1);

    }

    public static void lessEqual() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "sle", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void greaterEqual() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            twoOperandsInstruction(op2, op1, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "sge", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void less() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "slt", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void greater() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.REAL_NUMBER) {
            twoOperandsInstruction(op2, op1, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
        } else if (op1.getType() == Type.INTEGER_NUMBER) {
            twoOperandsInstruction(op1, op2, resultType, "sgt", storeCommand, loadCommand, variableName0, variableName1);
        }
    }

    public static void increment() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.INTEGER_NUMBER) {
            plusPlus(op1, resultType, "addi");
        }
//        else
//            throw new TypeError("++", firstOperandDes.getType());
    }

    public static void decrement() {
        Type resultType = doSomeStuff();
        if (op1.getType() == Type.INTEGER_NUMBER) {
            minusMinus(op1, resultType, "addi");
        }
//        else
//            throw new TypeError("--", firstOperandDes.getType());

    }

    public static void not() {
        Type resultType = doSomeStuff();
        generateNotCommand(op1, resultType, "not");

    }

    public static void negativeSign() {
    }

    public static void positiveSign() {
    }

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

    private static void twoOperandsInstruction(Descriptor op1, Descriptor op2, Type resultType, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = loadAndOperate(op1, op2, operationCommand, storeCommand, loadCommand, variableName0, variableName1);
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList(variableName0, variableName));
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    private static void minusMinus(Descriptor op1, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + "--" + " expression of " + op1.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));
        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$t0", "$t0", "-1"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableName));
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    private static void generateNotCommand(Descriptor op1, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + operationCommand + " expression of " + op1.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));
        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$t0", "$t0"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableName));
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
//
    }

    private static void plusPlus(Descriptor op1, Type resultType, String operationCommand/*, boolean isBeforeExpression*/) {
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommentToCode("binary " + "++" + " expression of " + op1.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", op1.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));
        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$t0", "$t0", "0x1"));
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", variableName));
        Stacks.pushSemanticS(new VariableDescriptor(variableName, resultType, true));
    }

    private void generateCompare(Descriptor firstOperandDes, Descriptor secondOperandDes) {
        continueVName = CodeGeneratorImpl.getVariableName();
        continueLabel = CodeGeneratorImpl.generateNewLabel();
        afterCompL = CodeGeneratorImpl.generateNewLabel();
        SPIMFileWriter.addCommentToCode("compare of real");
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", firstOperandDes.getName()));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t1", secondOperandDes.getName()));
        SPIMFileWriter.addCommandToCode("l.s", Arrays.asList("$f0", "0($t0)"));
        SPIMFileWriter.addCommandToCode("l.s", Arrays.asList("$f1", "0($t1)"));
        SPIMFileWriter.addCommandToCode("c.eq.s", Arrays.asList("$f0", "$f1"));
        SPIMFileWriter.addCommandToCode("bc1t", Arrays.asList(afterCompL));
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$t0", "0"));
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", continueVName));
        Stacks.pushSemanticS(new VariableDescriptor(continueVName, Type.INTEGER_NUMBER, true));
    }


}
