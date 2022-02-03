package CodeGen.SymbolTable;

import CodeGen.SymbolTable.SymbolTable;

import java.util.Stack;

public class Stacks {

    private static Stack<SymbolTable> symbolTableStack = new Stack<>();

    private static Stack<Object> semanticStack = new Stack<>();

    public static Object pushSemanticS(Object item) {
        return semanticStack.push(item);
    }

    public static Object popSemanticS() {
        return semanticStack.pop();
    }

    public static Object topSemanticS() {
        return semanticStack.peek();
    }

    public static boolean isSemanticSEmpty() {
        return semanticStack.isEmpty();
    }

    public static String printSemanticSSIze() {
        return "Size of SS: " + semanticStack.size();

    }


    public static SymbolTable pushSymbolTableS(SymbolTable item) {
        return symbolTableStack.push(item);
    }

    public static SymbolTable popSymbolTableS() {
        return symbolTableStack.pop();
    }

    public static SymbolTable topSymbolTableS() {
        return symbolTableStack.peek();
    }

    public static boolean isSymbolTableSEmpty() {
        return symbolTableStack.isEmpty();
    }

    public static String printSymbolTableSIze() {

        return ("Size of SymbolTable: " + symbolTableStack.size());
    }

}
