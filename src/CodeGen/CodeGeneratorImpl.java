package CodeGen;

import CodeGen.Ast.Expressions.Cast;
import CodeGen.Ast.Expressions.InputReader;
import CodeGen.Ast.Expressions.UnaryAndBinaryExpressions;
import CodeGen.Ast.Expressions.constant.IntegerConstant;
import CodeGen.Ast.Expressions.constant.RealConstant;
import CodeGen.Ast.Expressions.constant.StringConstant;
import CodeGen.Ast.Statements.*;
import CodeGen.SymbolTable.Descriptor;

import CodeGen.SymbolTable.Dscp.ArrayDescriptor;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.GlobalSymbolTable;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.DescriptorChecker;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;
import CodeGen.Utils.TypeChecker;
import ScannerAndParser.LexicalScanner;

import java.util.Arrays;

public class CodeGeneratorImpl implements CodeGenerator {
    public static LexicalScanner lexical;
    private static int variableIndex = 0;
    private static int labelIndex = 0;

    public static void initCodeGenerator(LexicalScanner scanner) {
        lexical = scanner;
    }

    @Override
    public void doSemantic(String sem) {
        Descriptor des;
        Type type;
        switch (sem) {
            case "pushType":
                Stacks.pushSemanticS(changeStringToType(lexical.currentSymbol().getToken()));
                printDebug(sem, Stacks.printSemanticSSIze() + " " + Stacks.printSymbolTableSIze());
                break;
            case "pushIdDCL":
                pushIdDCL();
                printDebug(sem, Stacks.printSemanticSSIze() + " " + Stacks.printSymbolTableSIze());
                break;
            case "addDescriptor":
                addDescriptor();
                printDebug(sem, Stacks.printSemanticSSIze() + " " + Stacks.printSymbolTableSIze());
                break;
            case "pushId":
                pushId();
                printDebug(sem, Stacks.printSemanticSSIze() + " " + Stacks.printSymbolTableSIze());
                break;
            case "pushIdArrayDCL":
                pushIdarrayDCl();
                printDebug(sem, "");
                break;
            case "popAndPushArrayType":
                popAndPushArrayType();
                printDebug(sem, "");
                break;
            case "setArrayDescriptor":
                setArrayDescriptor(sem);
                break;
            case "arrayAccess":
                arrayAccess();
                printDebug(sem, "");
                break;
            case "assignment":
                 new Assignment().assign();
                printDebug(sem, "");
                break;
            case "startIf":
                new If((Descriptor) Stacks.popSemanticS()).startIf();
                printDebug(sem, "");
                break;
            case "completeIf":
                If.completeIf();
                printDebug(sem, "");
                break;
            case "else":
                If.elseCode();
                printDebug(sem, "");
                break;
            case "completeElse":
                If.completeElse();
                printDebug(sem, "");
                break;
            case "startConditionWhile":
                While.startCondition();
                printDebug(sem, "");
                break;
            case "whileJumpZero":
                new While((Descriptor) Stacks.popSemanticS()).startWhileLoop();
                printDebug(sem, "");
                break;
            case "completeWhile":
                While.completeWhile();
                printDebug(sem, "");
            case "startForCondition":
                For.startCondition();
                printDebug(sem, "");
                break;
            case "forJumpZero":
                new For((Descriptor) Stacks.popSemanticS()).startForLoop();
                break;
            case "completeFor":
                For.completeFor();
                For.stepStatement();
                For.completeStepOfFor();
                printDebug(sem, "");
                break;
            case "print":
                new Printer().print((Descriptor) Stacks.popSemanticS());
                printDebug(sem, "");
                break;
            case "readInt":
                new InputReader().readInt();
                printDebug(sem, Stacks.printSemanticSSIze() + " " + Stacks.printSymbolTableSIze());
                break;
            case "readString":
                new InputReader().readString();
                printDebug(sem, "");
                break;
            case "divisionAssignment":
                UnaryAndBinaryExpressions.divide(true);
                new Assignment().assign();
                printDebug(sem, "");
                break;
            case "productionAssignment":
                UnaryAndBinaryExpressions.production(true);
                new Assignment().assign();
                printDebug(sem, "");
                break;
            case "additionAssignment":
                UnaryAndBinaryExpressions.add(true);
                new Assignment().assign();
                printDebug(sem, "");
                break;
            case "subtractionAssignment":
                UnaryAndBinaryExpressions.sub(true);
                new Assignment().assign();
                printDebug(sem, "");
                break;
            case "logicalOr":
                UnaryAndBinaryExpressions.logicalOr();
                printDebug(sem, "");
                break;
            case "logicalAnd":
                UnaryAndBinaryExpressions.logicalAnd();
                printDebug(sem, "");
                break;
            case "bitwiseOr":
                UnaryAndBinaryExpressions.bitwiseOr();
                break;
            case "bitwiseXor":
                UnaryAndBinaryExpressions.bitwiseXor();
                printDebug(sem, "");
                break;
            case "bitwiseAnd":
                UnaryAndBinaryExpressions.bitwiseAnd();
                printDebug(sem, "");
                break;
            case "equal":
                UnaryAndBinaryExpressions.equal();
                printDebug(sem, "");
                break;
            case "notEqual":
                UnaryAndBinaryExpressions.notEqual();
                printDebug(sem, "");
                break;
            case "lessEqual":
                UnaryAndBinaryExpressions.lessEqual();
                printDebug(sem, "");
                break;
            case "greaterEqual":
                UnaryAndBinaryExpressions.greaterEqual();
                printDebug(sem, "");
                break;
            case "less":
                UnaryAndBinaryExpressions.less();
                printDebug(sem, "");
                break;
            case "greater":
                UnaryAndBinaryExpressions.greater();
                printDebug(sem, "");
                break;
            case "add":
                UnaryAndBinaryExpressions.add(false);
                printDebug(sem, "");
                break;
            case "sub":
                UnaryAndBinaryExpressions.sub(false);
                printDebug(sem, "");
                break;
            case "mod":
                UnaryAndBinaryExpressions.remainder();
                printDebug(sem, "");
                break;
            case "production":
                UnaryAndBinaryExpressions.production(false);
                printDebug(sem, "");
                break;
            case "division":
                UnaryAndBinaryExpressions.divide(false);
                printDebug(sem, "");
                break;
            case "decrement":
                UnaryAndBinaryExpressions.decrement();
                printDebug(sem, "");
                break;
            case "increment":
                UnaryAndBinaryExpressions.increment();
                printDebug(sem, "");
                break;
            case "not":
                UnaryAndBinaryExpressions.not();
                printDebug(sem, "");
                break;
            case "negativeSign":
                UnaryAndBinaryExpressions.negativeSign();
                printDebug(sem, "");
                break; 
            case "positiveSign":
                UnaryAndBinaryExpressions.positiveSign();
                printDebug(sem, "");
                break; 
            case "castReal":
                des = (Descriptor) Stacks.popSemanticS();
                type = (Type) Stacks.popSemanticS();
                new Cast(des, type, "i2s").castIntToReal();
                printDebug(sem, "");
                break;
            case "castInt":
                des = (Descriptor) Stacks.popSemanticS();
                type = (Type) Stacks.popSemanticS();
                new Cast(des, type, "s2i").castRealToInt();
                printDebug(sem, "");
                break;

            case "stratMethod":
                printDebug(sem, "");
                break; 
            case "jumpReturn":
                printDebug(sem, "");
                break; 
            case "return":
                printDebug(sem, "");
                break; 
            case "returnByValue":
                printDebug(sem, "");
                break; 
            case "break":
                printDebug(sem, "");
                break; 
            case "continue":
                printDebug(sem, "");
                break; 
            case "jumpAndLink":
                printDebug(sem, "");
                break; 

            case "pushString":
                  new StringConstant((String) lexical.currentSymbol().getValue()).readStringConst();
                printDebug(sem, "");
                break; 
            case "pushDecimal":
                IntegerConstant intConst = new IntegerConstant((Integer) lexical.currentSymbol().getValue());
                intConst.readIntConst();
                printDebug(sem, "");
                break; 
            case "pushHexDecimal":
                printDebug(sem, "");
                break; 
            case "pushRealNumber":
                new RealConstant((Float) lexical.currentSymbol().getValue()).readRealConst();
                printDebug(sem, "");
                break; 
            case "pushScientificNumber":
                printDebug(sem, "");
                break; 
            case "propertyAccess":
                printDebug(sem, "");
                break; 
            case "getLength":
                printDebug(sem, "");
                break; 
        }
    }

    private void arrayAccess() {
        VariableDescriptor index = (VariableDescriptor) Stacks.popSemanticS();
        Descriptor arrName = (Descriptor) Stacks.popSemanticS();
        SPIMFileWriter.addCommentToCode("array access with name " + arrName.getName() + " at " + index.getValue());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", arrName.getName()));
        SPIMFileWriter.addCommandToCode("li", Arrays.asList( "$t4", "4"));
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$t1", String.valueOf(index.getValue())));
        SPIMFileWriter.addCommandToCode("mul", Arrays.asList("$t1", "$t1", "$t4"));
        SPIMFileWriter.addCommandToCode("add", Arrays.asList("$t0", "$t0", "$t1"));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "$t0", "0($t0)"));
        VariableDescriptor vd = new VariableDescriptor(getVariableName(), changeArrayTypeToElementType(arrName.getType()) , true);
        SPIMFileWriter.addCommandToDataSegment(vd.getName(), "word", "0");
        Stacks.pushSemanticS(vd);
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", vd.getName()));
    }

    private void setArrayDescriptor(String sem) {
        Type newArrayType = (Type) Stacks.popSemanticS();
        VariableDescriptor sizeDescriptor = (VariableDescriptor) Stacks.popSemanticS();
        ArrayDescriptor nameOfArrayDes = (ArrayDescriptor) Stacks.popSemanticS();
        nameOfArrayDes.setSize(Integer.parseInt(sizeDescriptor.getValue()));
        if (nameOfArrayDes.getIsLocal()) {
            DescriptorChecker.IsInSymbolTable(nameOfArrayDes,false);
            DescriptorChecker.IsInSymbolTable(nameOfArrayDes,true);
        } else {

        }
        TypeChecker.checkArrayType(nameOfArrayDes.getType(), newArrayType);
        ArrayDescriptor ad = new ArrayDescriptor(nameOfArrayDes.getName(), newArrayType , true);
        ad.setSize(Integer.parseInt(sizeDescriptor.getValue()));
        if (nameOfArrayDes.getIsLocal()) {
            Stacks.topSymbolTableS().addDescriptor(nameOfArrayDes.getName(), ad);
        } else {
            GlobalSymbolTable.getSymbolTable().addDescriptor(nameOfArrayDes.getName(), ad);
        }
        SPIMFileWriter.addCommandToDataSegment(nameOfArrayDes.getName(), "space", String.valueOf(4 * Integer.parseInt(sizeDescriptor.getValue())));
        printDebug(sem, "");
    }

    private void pushIdDCL() {
        try {
            DescriptorChecker.isNotInSymbolTable(lexical.currentSymbol().getToken(),false);
            DescriptorChecker.isNotInSymbolTable(lexical.currentSymbol().getToken(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stacks.pushSemanticS(lexical.currentSymbol().getToken());
    }

    private void addDescriptor() {
        String name = (String) Stacks.popSemanticS();
        Object t1 = Stacks.popSemanticS();

        if (t1 instanceof Type) {
            Type t = (Type) t1;
            if (TypeChecker.isArrayType(t)) {
                ArrayDescriptor lad = (ArrayDescriptor) Stacks.topSymbolTableS().getDescriptor(name);
                lad.setName(name);
            } else {
                if (!Stacks.topSymbolTableS().contains(name)) {
                    VariableDescriptor lvd = new VariableDescriptor(getVariableName(), t, true);
                    Stacks.topSymbolTableS().addDescriptor(name, lvd);
                    if (t != Type.STRING) {
                        SPIMFileWriter.addCommandToDataSegment(lvd.getName(), "word", "0");
                    } else {
                        SPIMFileWriter.addCommandToDataSegment(lvd.getName(), "space", "20");

                    }
                }
            }
        }
    }

    private void pushId() {
        System.out.println(lexical.currentSymbol().getToken());
        if (!Stacks.isSymbolTableSEmpty() && Stacks.topSymbolTableS().contains(lexical.currentSymbol().getToken())) {
            Stacks.pushSemanticS(Stacks.topSymbolTableS().getDescriptor(lexical.currentSymbol().getToken()));
        } else {
            try {
                Stacks.pushSemanticS(GlobalSymbolTable.getSymbolTable().getDescriptor(lexical.currentSymbol().getToken()));
            } catch (Exception e1) {
            }
        }
    }

    private void pushIdarrayDCl() {
        try {
            DescriptorChecker.isNotInSymbolTable(lexical.currentSymbol().getToken() ,false);
            DescriptorChecker.isNotInSymbolTable(lexical.currentSymbol().getToken(),true);

        } catch (Exception e) {
            e.printStackTrace();
        }
       Type arrType = (Type) Stacks.topSemanticS();
        if (!Stacks.topSymbolTableS().contains(lexical.currentSymbol().getToken())) {
            Descriptor lad = new ArrayDescriptor(getVariableName(), arrType, true);
            Stacks.topSymbolTableS().addDescriptor(lexical.currentSymbol().getToken(), lad);
            Stacks.pushSemanticS(lexical.currentSymbol().getToken());
        }
    }

    private void popAndPushArrayType() {
        Type type;
        Object o = Stacks.popSemanticS();
        type = (Type) o;
        Type resType = null;
        switch (type) {
            case INTEGER_NUMBER:
                resType = Type.INT_ARRAY;
                break;
            case REAL_NUMBER:
                resType = Type.DOUBLE_ARRAY;
                break;
            case STRING:
                resType = Type.STRING_ARRAY;
                break;
        }
        Stacks.pushSemanticS(resType);
    }

    static void printDebug(String sem, String message) {
        System.out.println("Sem : " + sem);
        System.out.println("message : " + message);
        System.out.println("////////////////////////////");
    }

    private static int getVariableIndex() {
        return variableIndex;
    }

    private static void incrementVariableIndex() {
        ++variableIndex;
    }


    public static String getVariableName() {
        incrementVariableIndex();
        return "adr" + getVariableIndex();
    }

    public static String generateNewLabel() {
        ++labelIndex;
        return "lbl" + labelIndex;
    }

    Type changeArrayTypeToElementType(Type arrType) {
        Type res;
        switch (arrType) {
            case DOUBLE_ARRAY:
                res = Type.REAL_NUMBER;
                break;
            case INT_ARRAY:
                res = Type.INTEGER_NUMBER;
                break;
            case STRING_ARRAY:
                res = Type.STRING;
                break;
            default:
                res = null;
        }
        return res;
    }


    Type changeStringToType(String type) {
        Type res;
        switch (type) {
            case "bool":
            case "int":
                res = Type.INTEGER_NUMBER;
                break;
            case "double":
                res = Type.REAL_NUMBER;
                break;
            case "real":
                res = Type.REAL_NUMBER;
                break;
            case "string":
                res = Type.STRING;
                break;
            default:
                res = null;
        }
        return res;
    }
}
