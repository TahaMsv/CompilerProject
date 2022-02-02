package CodeGen.Expressions.InputReader;

import CodeGen.CodeGeneratorImpl;
import CodeGen.Utils.SPIMFileWriter;

import java.util.List;

public class InputReader {

    public static void readInt() {
        SPIMFileWriter.addCommentToCode("Read int from console");
        SPIMFileWriter.addCommandToCode("li", List.of("$v0", "5"));
        SPIMFileWriter.addCommandToCode("syscall", List.of());
        SPIMFileWriter.addCommandToCode("move", List.of("$t0", "$v0"));

        String variableName = CodeGeneratorImpl.getVariableName();
        SPIMFileWriter.addCommandToDataSegment(variableName, "word", "0");
        SPIMFileWriter.addCommandToCode("la", List.of("$t1", variableName));
        SPIMFileWriter.addCommandToCode("sw", List.of("$t0", "0($t1)"));
//        SemanticStack.push(new LocalVariableDescriptor(variableName, Type.INTEGER_NUMBER));
    }

    public static void readString() {
        SPIMFileWriter.addCommentToCode("Read string from console");
        SPIMFileWriter.addCommandToCode("li", List.of("$v0", "8"));
        SPIMFileWriter.addCommandToCode("la", List.of("$a0", "strbuffer"));
        SPIMFileWriter.addCommandToCode("li", List.of("$a1", "20"));
        SPIMFileWriter.addCommandToCode("move", List.of("$t0", "$a0"));
        SPIMFileWriter.addCommandToCode("sw", List.of("$t0", "stradr"));
        String variableName = CodeGeneratorImpl.getVariableName();
//        LocalVariableDescriptor lvd = new LocalVariableDescriptor(variableName, Type.STRING);
        SPIMFileWriter.addCommandToDataSegment(variableName, "space", "20");
//        SPIMFileWriter.addCommandToCode("sw", List.of("$t0", lvd.getName()));
//        SemanticStack.push(lvd);
        SPIMFileWriter.addCommandToCode("syscall", List.of());

    }
}
