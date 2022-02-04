package CodeGen.Ast.Expressions;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.Type;

import java.util.Arrays;

public class Cast {
    private Descriptor operand;
    private Type type;
    private String operation;

    public Cast(Descriptor operand, Type type, String operation) {
        this.operand = operand;
        this.type = type;
        this.operation = operation;
    }

    public void castRealToInt() {
        convert(operand, this.type, "cvt.w.s", "s.s", "l.s");
    }

    public void castIntToReal() {
        convert(operand, this.type, "cvt.s.w", "s.s", "l.s");
    }

    private void convert(Descriptor firstOperandDes, Type resultType, String operationCommand, String storeCommand, String loadCommand) {
        SPIMFileWriter.addCommentToCode("binary " + this.operation + " expression of " + firstOperandDes.getName());

        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", firstOperandDes.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t0", "0($t0)"));
        SPIMFileWriter.addCommandToCode("mtc1", Arrays.asList("$t0", "$f0"));
        SPIMFileWriter.addCommandToCode(operationCommand, Arrays.asList("$f1", "$f0"));

//        AssemblyFileWriter.appendCommandToData(firstOperandDes.getName(), "word", "0");
        SPIMFileWriter.addCommandToCode(storeCommand, Arrays.asList("$f1", firstOperandDes.getName()));
//        AssemblyFileWriter.appendDebugLine(firstOperandDes.getName());
        Stacks.pushSemanticS(new VariableDescriptor(firstOperandDes.getName(), resultType, true));
    }
}

