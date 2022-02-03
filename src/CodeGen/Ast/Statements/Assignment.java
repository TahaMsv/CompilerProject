package CodeGen.Ast.Statements;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;

import java.util.Arrays;

public class Assignment {

    public void assign() {

        Descriptor rValue = (Descriptor) Stacks.popSemanticS();
        Descriptor lValue = (Descriptor) Stacks.popSemanticS();

        SPIMFileWriter.addCommentToCode("assignment " + lValue.getName() + " = " +rValue.getName());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", lValue.getName()));
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t1", rValue.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t1", "0($t1)"));
        SPIMFileWriter.addCommandToCode("sw", Arrays.asList("$t1", "0($t0)"));

        //Todo for array Type
//        if (TypeChecker.isArrayType(des.getType())) {
//            int index = Integer.parseInt(((VariableDescriptor) rightSide).getValue());
//            AssemblyFileWriter.appendCommandToCode("li", "$t0", String.valueOf(index));
//            AssemblyFileWriter.appendCommandToCode("la", "$t1", des.getName());
//            AssemblyFileWriter.appendCommandToCode("li", "$t4", String.valueOf(4)); //TODO: convert 4 to size of
//            AssemblyFileWriter.appendCommandToCode("mul", "$t0", "$t0", "$t4");
//            AssemblyFileWriter.appendCommandToCode("add", "$t1", "$t1", "$t0");
//            AssemblyFileWriter.appendCommandToCode("lw", "$t1", "0($t1)");
//            Descriptor leftSide = (Descriptor) SemanticStack.pop();
//            AssemblyFileWriter.appendCommandToCode("sw", "$t1", leftSide.getName());
//            AssemblyFileWriter.appendDebugLine(leftSide.getName());
//        } else {
//            Descriptor leftSide = (Descriptor) des;
//            AssemblyFileWriter.appendComment("assignment " + leftSide.getName() + " = " + rightSide.getName());
//            AssemblyFileWriter.appendCommandToCode("la", "$t0", leftSide.getName());
//            AssemblyFileWriter.appendCommandToCode("la", "$t1", rightSide.getName());
//            AssemblyFileWriter.appendCommandToCode("lw", "$t1", "0($t1)");
//            AssemblyFileWriter.appendCommandToCode("sw", "$t1", "0($t0)");
//            AssemblyFileWriter.appendDebugLine(leftSide.getName());
//        }
    }
}
