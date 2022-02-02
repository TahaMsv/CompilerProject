package CodeGen.Ast.DCL;


import CodeGen.Type;

public abstract class VariableDeclaration extends Declaration {

    private boolean isLocal;

    public VariableDeclaration(String name, Type type, boolean isLocal) {
        super(name, type);
        this.isLocal = isLocal;
    }
}
