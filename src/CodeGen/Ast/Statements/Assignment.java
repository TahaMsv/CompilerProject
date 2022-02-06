package CodeGen.Ast.Statements;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;
import CodeGen.Utils.SemanticErrors.AssignmentError;

import java.util.Arrays;

public class Assignment {

    public void assign() {
        Descriptor rValue = (Descriptor) Stacks.popSemanticS();
        Descriptor lValue = (Descriptor) Stacks.popSemanticS();
        new AssignmentError().checkAssignmentError(lValue, rValue);
        SPIMFileWriter.addCommentToCode("assignment " + lValue.getName() + " = " + rValue.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", lValue.getName()));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t1", rValue.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t1", "0($t1)"));
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t1", "0($t0)"));
    }
}
