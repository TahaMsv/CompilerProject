package CodeGen.Ast.DCL;


import CodeGen.Utils.Type;

public class ArrayDeclaration extends Declaration {
    private int numberOfDimensions;
    public ArrayDeclaration(String name, Type type, int numberOfDimensions

    ) {
        super(name, type);
        this.numberOfDimensions = numberOfDimensions;
    }

}
