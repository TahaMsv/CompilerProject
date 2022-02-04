package CodeGen;

import CodeGen.Ast.Expressions.Cast;
import CodeGen.Ast.Expressions.InputReader;
import CodeGen.Ast.Expressions.UnaryAndBinaryExpressions;
import CodeGen.Ast.Statements.*;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.GlobalSymbolTable;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;
import ScannerAndParser.LexicalScanner;

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

                printDebug(sem, Stacks.printSemanticSSIze()+" " + Stacks.printSymbolTableSIze());
                break;
            case "pushIdDCL":
                //TODO  check types
//                DescriptorChecker.checkNotContainsDescriptor(lexical.currentSymbol().getToken());
//                DescriptorChecker.checkNotContainsDescriptorGlobal(lexical.currentSymbol().getToken());

                Stacks.pushSemanticS(lexical.currentSymbol().getToken());

                printDebug(sem, Stacks.printSemanticSSIze()+" " + Stacks.printSymbolTableSIze());
                break;

            case "addDescriptor":
                String name = (String) Stacks.popSemanticS();
                Object t1 = Stacks.popSemanticS();

                if (t1 instanceof Type) {
                    Type t = (Type) t1;
//                    if (TypeChecker.isArrayType(t)) {
//                        ArrayDescriptor lad = (ArrayDescriptor) SymbolTableStack.top().getDescriptor(name);
//                        lad.setRealName(name);
//                    } else {
                    if (!Stacks.topSymbolTableS().contains(name)) {
                        VariableDescriptor lvd = new VariableDescriptor(getVariableName(), t, true);
                        Stacks.topSymbolTableS().addDescriptor(name, lvd);
                        if (t != Type.STRING) {
                            SPIMFileWriter.addCommandToDataSegment(lvd.getName(), "word", "0");
                        } else {
                            SPIMFileWriter.addCommandToDataSegment(lvd.getName(), "space", "20");

                        }
                    } else {
//                        try {
////                            throw new NameError(name, true);
//                        } catch (Exception e2) {
//                            System.err.println(e2.getMessage());
//                        }
                    }
//                    }
                }

                printDebug(sem, Stacks.printSemanticSSIze()+" " + Stacks.printSymbolTableSIze());
                break;

            case "pushId":
                System.out.println(lexical.currentSymbol().getToken());
                if (!Stacks.isSymbolTableSEmpty() && Stacks.topSymbolTableS().contains(lexical.currentSymbol().getToken())) {
                    Stacks.pushSemanticS(Stacks.topSymbolTableS().getDescriptor(lexical.currentSymbol().getToken()));
                } else {
                    try {
                        Stacks.pushSemanticS(GlobalSymbolTable.getSymbolTable().getDescriptor(lexical.currentSymbol().getToken()));
                    } catch (Exception e1) {
//                        try {
//                            try {
//                                System.out.println(407);
//                                throw new NameError(lexical.currentSymbol.getToken(), false);
//                            } catch (Exception e2) {
//                                System.err.println(e2.getMessage());
//                                System.out.println(410);
//                            }
//                        } catch (Exception e2) {
//                            System.out.println(414);
//                            System.err.println(e2.getMessage());
//                        }
                    }
                }
                printDebug(sem, Stacks.printSemanticSSIze()+" " + Stacks.printSymbolTableSIze());
                break;
            case "arrayAccess":
                printDebug(sem, "");
                break; //TODO
            case "arrayDcl":
                printDebug(sem, "");
                break; //TODO
            case "stratMethod":
                printDebug(sem, "");
                break; //TODO
            case "jumpReturn":
                printDebug(sem, "");
                break; //TODO
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
            case "startWhileCondition":
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
            case "startLoop":
                printDebug(sem, "");
                break; //TODO
            case "completeLoop":
                printDebug(sem, "");
                break; //TODO
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
            case "return":
                printDebug(sem, "");
                break; //TODO
            case "returnByValue":
                printDebug(sem, "");
                break; //TODO
            case "print":
                new Printer().print((Descriptor) Stacks.popSemanticS());
                printDebug(sem, "");
                break;
            case "break":
                printDebug(sem, "");
                break; //TODO
            case "continue":
                printDebug(sem, "");
                break; //TODO
            case "jumpAndLink":
                printDebug(sem, "");
                break; //TODO
            case "readInt":
                new InputReader().readInt();
                printDebug(sem, Stacks.printSemanticSSIze()+" " + Stacks.printSymbolTableSIze());
                break;
            case "readString":
                new InputReader().readString();
                printDebug(sem, "");
                break;
            case "setArrayDescriptor":
                printDebug(sem, "");
                break; //TODO
            case "divisionAssignment":
                printDebug(sem, "");
                break; //TODO
            case "productionAssignment":
                printDebug(sem, "");
                break; //TODO
            case "additionAssignment":
                printDebug(sem, "");
                break; //TODO
            case "subtractionAssignment":
                printDebug(sem, "");
                break; //TODO
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
                UnaryAndBinaryExpressions.add();
                printDebug(sem, "");
                break;
            case "sub":
                UnaryAndBinaryExpressions.sub();
                printDebug(sem, "");
                break;
            case "mod":
                UnaryAndBinaryExpressions.remainder();
                printDebug(sem, "");
                break;
            case "production":
                UnaryAndBinaryExpressions.production();
                printDebug(sem, "");
                break;
            case "division":
                UnaryAndBinaryExpressions.divide();
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
                break; //TODO
            case "positiveSign":
                UnaryAndBinaryExpressions.positiveSign();
                printDebug(sem, "");
                break; //TODO
            case "castReal":
                des = (Descriptor) Stacks.popSemanticS();
                 type = (Type) Stacks.popSemanticS();
               new Cast(des, type,"i2s").castIntToReal();
//
//                    String srcType = des.getType().toString();
//                    String destType = type.toString();
//                    new CastError(srcType, destType).error(); //Todo typeCasting error
//
                printDebug(sem, "");
                break;
            case "castInt":
                 des = (Descriptor) Stacks.popSemanticS();
                  type = (Type) Stacks.popSemanticS();
                new Cast(des, type,"s2i").castRealToInt();
                printDebug(sem, "");
                break;
            case "pushString":
                printDebug(sem, "");
                break; //TODO
            case "pushDecimal":
                printDebug(sem, "");
                break; //TODO
            case "pushHexDecimal":
                printDebug(sem, "");
                break; //TODO
            case "pushRealNumber":
                printDebug(sem, "");
                break; //TODO
            case "pushScientificNumber":
                printDebug(sem, "");
                break; //TODO
            case "propertyAccess":
                printDebug(sem, "");
                break; //TODO
            case "getLength":
                printDebug(sem, "");
                break; //TODO
            case "popAndPushArrayType":
                printDebug(sem, "");
                break; //TODO
            case "pushArrayIDDcl":
                printDebug(sem, "");
                break; //TODO

        }
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

//    Type changeArrayTypeToElementType(Type arrType) {
//        Type res;
//        switch (arrType) {
//            case DOUBLE_ARRAY:
//                res = Type.REAL_NUMBER;
//                break;
//            case INT_ARRAY:
//                res = Type.INTEGER_NUMBER;
//                break;
//            case STRING_ARRAY:
//                res = Type.STRING;
//                break;
//            default:
//                res = null;
//        }
//        return res;
//    }


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
