package CodeGen.SymbolTable.Dscp;

import CodeGen.Type;


class ArrayDescriptor extends Descriptor {
    private int size;
    private String realName;
    private boolean isLocal;

    public ArrayDescriptor(String name, Type type, boolean isLocal) {
        super(name, type, isLocal);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSize() {
        return size;
    }

    public String getRealName() {
        return realName;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
