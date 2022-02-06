package CodeGen.SymbolTable.Dscp;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.Type;


public class FunctionDescriptor extends Descriptor {
    private String labelName;
    private boolean isLocal;

    public FunctionDescriptor(String addressName, Type type, boolean isLocal) {
        super(addressName, type, isLocal);
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
