package CodeGen.SymbolTable.Dscp;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.Type;


public class VariableDescriptor extends Descriptor {
    private String value;
    private boolean isLocal;

    public VariableDescriptor(String name, Type type, boolean isLocal) {
        super(name, type, isLocal);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
