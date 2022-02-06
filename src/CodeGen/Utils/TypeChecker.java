package CodeGen.Utils;


public class TypeChecker {
    public static boolean checkType(Type firstType, Type secondType) {
        if (firstType == secondType) {
            return true;
        }
        throw new RuntimeException("Type error");
    }

    public static boolean isArrayType(Type type) {
        return type == Type.DOUBLE_ARRAY || type == Type.INT_ARRAY || type == Type.STRING_ARRAY;
    }

    public static void checkArrayType(Type arrayType, Type elementType) {
        if ((arrayType == Type.INT_ARRAY && elementType == Type.INTEGER_NUMBER) || (arrayType == Type.DOUBLE_ARRAY && elementType == Type.REAL_NUMBER)
                || (arrayType == Type.STRING_ARRAY && elementType == Type.STRING)) {
            return;
        }
        throw new RuntimeException("Type error");

    }
}
