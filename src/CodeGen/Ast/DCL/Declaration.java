package CodeGen.Ast.DCL;

import CodeGen.Type;

public class Declaration {
    private String name;
    private Type type;

    public Declaration(String name) {
        this.name = name;
    }

    public Declaration(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
