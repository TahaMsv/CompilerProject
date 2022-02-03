package CodeGen;

import CodeGen.Ast.Expressions.InputReader;
import CodeGen.Ast.Statements.Assignment;
import CodeGen.Ast.Statements.Printer;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Stacks;

public class CodeGeneratorImpl implements CodeGenerator {

    private static int variableIndex = 0;
    private static int labelIndex = 0;

    @Override
    public void doSemantic(String sem) {

        switch (sem) {
            case "addDescriptor":
                printDebug(sem, "");
                break; //TODO
            case "pushType":
                printDebug(sem, "");
                break; //TODO
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
                printDebug(sem, "");
                Descriptor rValue = (Descriptor) Stacks.popSemanticS();
                Descriptor lValue = (Descriptor) Stacks.popSemanticS();
                new Assignment().assign(lValue, rValue);
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
                printDebug(sem, "");
                new Printer().print((Descriptor) Stacks.popSemanticS());
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
                printDebug(sem, "");
                new InputReader().readInt();
                break;
            case "readString":
                printDebug(sem, "");
                new InputReader().readString();
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
            case "pushIdDcl":
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


}
