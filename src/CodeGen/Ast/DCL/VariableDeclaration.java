package CodeGen.Ast.DCL;


import CodeGen.Utils.Type;

public abstract class VariableDeclaration extends Declaration {
    private boolean isLocal;

    public VariableDeclaration(String name, Type type, boolean isLocal) {
        super(name, type);
        this.isLocal = isLocal;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
