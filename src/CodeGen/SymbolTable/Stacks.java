package CodeGen.SymbolTable;

import CodeGen.SymbolTable.SymbolTable;

import java.util.Stack;

public class Stacks {

    private static Stack<SymbolTable> symbolTableStack = new Stack<>();
    private static Stack<Object> semanticStack = new Stack<>();
    private static Stack<String> startIF = new Stack<>();
    private static Stack<String> startElse = new Stack<>();


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

    public static String pushStartIFS(String item) {
        return startIF.push(item);
    }

    public static String popStartIFS() {
        return startIF.pop();
    }

    public static String topStartIFS() {
        return startIF.peek();
    }

    public static boolean isStartIFSEmpty() {
        return startIF.isEmpty();
    }

    public static String printStartIFSSIze() {
        return "Size of SS: " + startIF.size();

    }

    public static String pushStartElseS(String item) {
        return startElse.push(item);
    }

    public static String popStartElseS() {
        return startElse.pop();
    }

    public static String topStartElseS() {
        return startElse.peek();
    }

    public static boolean isStartElseSEmpty() {
        return startElse.isEmpty();
    }

    public static String printStartElseSSIze() {
        return "Size of SS: " + startElse.size();

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
