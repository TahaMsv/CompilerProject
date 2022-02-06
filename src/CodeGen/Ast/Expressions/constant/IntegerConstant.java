package CodeGen.Ast.Expressions.constant;

import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.GlobalSymbolTable;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;

import java.util.Arrays;

public class IntegerConstant {
    protected int intConst;

    public IntegerConstant(int intConst) {
        this.intConst = intConst;
    }

    public   void readIntConst() {
        VariableDescriptor descriptor = (VariableDescriptor) GlobalSymbolTable.getSymbolTable().getDescriptor("$" + intConst);
        boolean hasDescriptor = descriptor != null;
        if (!hasDescriptor) {
            String variableName = CodeGeneratorImpl.getVariableName();
            descriptor = new VariableDescriptor(variableName, Type.INTEGER_NUMBER, false);
            descriptor.setValue(String.valueOf(intConst));
            SPIMFileWriter.addCommandToCode("li", Arrays.asList("$t0", String.valueOf(intConst)));
            SPIMFileWriter.addCommandToCode("li", Arrays.asList( "$t0", variableName));
            GlobalSymbolTable.getSymbolTable().addDescriptor("$" + intConst, descriptor);
            SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        }
        Stacks.pushSemanticS(descriptor);
    }
}
