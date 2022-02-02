package CodeGen.Ast.DCL;


import CodeGen.Type;

import java.util.List;

public class FunctionDeclaration extends Declaration {

    private List<VariableDeclaration> parameters;

    public FunctionDeclaration(String name, Type type, List<VariableDeclaration> parameters) {
        super(name, type);
        this.parameters = parameters;
    }

}
