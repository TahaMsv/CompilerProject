package CodeGen.Ast.Expressions.constant;

import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.GlobalSymbolTable;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;

import java.util.Arrays;

public class RealConstant {
    protected float realConst;

    public RealConstant(float realConst) {
        this.realConst = realConst;
    }

    public void readRealConst() {
        System.out.println(realConst);
        VariableDescriptor descriptor = (VariableDescriptor) GlobalSymbolTable.getSymbolTable().getDescriptor("$" + realConst);
        boolean hasDescriptor = descriptor != null;
        if (!hasDescriptor) {
            String variableName = CodeGeneratorImpl.getVariableName();
            descriptor = new VariableDescriptor(variableName, Type.REAL_NUMBER, false);
            descriptor.setValue(String.valueOf(realConst));
            SPIMFileWriter.addCommandToCode("li.s", Arrays.asList( "$f0", String.valueOf(realConst)));
            SPIMFileWriter.addCommandToCode("s.s", Arrays.asList( "$f0", variableName));
            GlobalSymbolTable.getSymbolTable().addDescriptor("$" + realConst, descriptor);
            SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        }
        Stacks.pushSemanticS(descriptor);
    }
}
