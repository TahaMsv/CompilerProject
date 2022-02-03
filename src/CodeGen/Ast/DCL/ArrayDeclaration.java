package CodeGen.Ast.DCL;


import CodeGen.Utils.Type;

public class ArrayDeclaration extends Declaration {
    private int numberOfDimensions;
//    private List<Expression> dimensions;

    public ArrayDeclaration(String name, Type type, int numberOfDimensions
//            ,                            List<Expression> dimensions
    ) {
        super(name, type);
        this.numberOfDimensions = numberOfDimensions;
//        this.dimensions = dimensions;
    }

}
