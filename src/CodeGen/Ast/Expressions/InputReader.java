package CodeGen.Ast.Expressions;

import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Dscp.VariableDescriptor;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.Type;
import CodeGen.Utils.SPIMFileWriter;

import java.util.Arrays;
import java.util.Collections;


public class InputReader {

    public void readInt() {
        SPIMFileWriter.addCommentToCode("Read int from console");
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$v0", "5"));
        SPIMFileWriter.addCommandToCode("syscall", Collections.emptyList());
        SPIMFileWriter.addCommandToCode("move", Arrays.asList("$t0", "$v0"));
        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t1", variableName));
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", "0($t1)"));
        VariableDescriptor vd = new VariableDescriptor(variableName, Type.INTEGER_NUMBER, true);
        Stacks.pushSemanticS(vd);
    }

    public void readString() {
        SPIMFileWriter.addCommentToCode("Read string from console");
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$v0", "8"));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$a0", "strbuffer"));
        SPIMFileWriter.addCommandToCode("li", Arrays.asList("$a1", "20"));
        SPIMFileWriter.addCommandToCode("move", Arrays.asList("$t0", "$a0"));
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", "stradr"));
        String variableName = CodeGeneratorImpl.getVariableName();
        VariableDescriptor vd = new VariableDescriptor(variableName, Type.STRING, true);
        SPIMFileWriter.addCommandToDataSegment(variableName, "space", "20");
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t0", vd.getName()));
        Stacks.pushSemanticS(vd);
        SPIMFileWriter.addCommandToCode("syscall", Collections.emptyList());

    }
}
