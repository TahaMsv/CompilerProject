package CodeGen.SymbolTable.Dscp;
import CodeGen.Type;


public class FunctionDescriptor extends Descriptor {
    private String labelFrom;
    private boolean isLocal;

    public FunctionDescriptor(String addressName, Type type, boolean isLocal) {
        super(addressName, type, isLocal);
    }

    public void setLabelFrom(String labelFrom) {
        this.labelFrom = labelFrom;
    }
}
