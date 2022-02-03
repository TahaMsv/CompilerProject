package CodeGen;

import CodeGen.Ast.Expressions.InputReader;
import CodeGen.Ast.Statements.Assignment;
import CodeGen.Ast.Statements.Printer;
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
                printDebug(sem, "");
                break; //TODO
            case "completeIf":
                printDebug(sem, "");
                break; //TODO
            case "else":
                printDebug(sem, "");
                break; //TODO
            case "completeElse":
                printDebug(sem, "");
                break; //TODO
            case "startWhileCondition":
                printDebug(sem, "");
                break; //TODO
            case "startLoop":
                printDebug(sem, "");
                break; //TODO
            case "completeLoop":
                printDebug(sem, "");
                break; //TODO
            case "startForCondition":
                printDebug(sem, "");
                break; //TODO
            case "completeFor":
                printDebug(sem, "");
                break; //TODO
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
                printDebug(sem, "");
                break; //TODO
            case "logicalAnd":
                printDebug(sem, "");
                break; //TODO
            case "bitwiseOr":
                printDebug(sem, "");
                break; //TODO
            case "bitwiseXor":
                printDebug(sem, "");
                break; //TODO
            case "bitwiseAnd":
                printDebug(sem, "");
                break; //TODO
            case "equal":
                printDebug(sem, "");
                break; //TODO
            case "notEqual":
                printDebug(sem, "");
                break; //TODO
            case "lessEqual":
                printDebug(sem, "");
                break; //TODO
            case "greaterEqual":
                printDebug(sem, "");
                break; //TODO
            case "less":
                printDebug(sem, "");
                break; //TODO
            case "greater":
                printDebug(sem, "");
                break; //TODO
            case "add":
                printDebug(sem, "");
                break; //TODO
            case "sub":
                printDebug(sem, "");
                break; //TODO
            case "mod":
                printDebug(sem, "");
                break; //TODO
            case "production":
                printDebug(sem, "");
                break; //TODO
            case "division":
                printDebug(sem, "");
                break; //TODO
            case "decrement":
                printDebug(sem, "");
                break; //TODO
            case "increment":
                printDebug(sem, "");
                break; //TODO
            case "not":
                printDebug(sem, "");
                break; //TODO
            case "negativeSign":
                printDebug(sem, "");
                break; //TODO
            case "positiveSign":
                printDebug(sem, "");
                break; //TODO
            case "castReal":
                printDebug(sem, "");
                break; //TODO
            case "castInt":
                printDebug(sem, "");
                break; //TODO
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
