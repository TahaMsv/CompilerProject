package CodeGen.Ast.Expressions.constant;


import CodeGen.CodeGenerator;
import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.GlobalSymbolTable;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;

public class StringConstant  {
    protected String stringConst;

    public StringConstant(String stringConst) {
        this.stringConst = stringConst;
    }

    public void readStringConst() {
        System.out.println(stringConst);
        VariableDescriptor descriptor = (VariableDescriptor) GlobalSymbolTable.getSymbolTable().getDescriptor("$$" + stringConst);
        boolean hasDescriptor = descriptor != null;
        if (!hasDescriptor) {
            String variableName = CodeGeneratorImpl.getVariableName();
            descriptor = new VariableDescriptor(variableName, Type.STRING,false);
            descriptor.setValue(stringConst);
            Stacks.pushSemanticS(descriptor);
            GlobalSymbolTable.getSymbolTable().addDescriptor("$$" + stringConst, descriptor);
            SPIMFileWriter.addCommandToDataSegment(variableName, "asciiz", "\"" + stringConst + "\"");
        }
    }
}
