package CodeGen.SymbolTable.Dscp;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.Type;


public class ArrayDescriptor extends Descriptor {
    private int size;
    private String name;
    private boolean isLocal;

    public ArrayDescriptor(String name, Type type, boolean isLocal) {
        super(name, type, isLocal);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
