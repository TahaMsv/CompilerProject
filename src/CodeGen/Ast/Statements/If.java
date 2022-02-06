package CodeGen.Ast.Statements;


import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.Stacks;
import CodeGen.Utils.SPIMFileWriter;

import java.util.Arrays;

public class If {
    private Descriptor value;
    public If(Descriptor value) {
        this.value = value;
    }
    public void startIf() {
        Stacks.pushStartIFS(CodeGeneratorImpl.generateNewLabel());
        SPIMFileWriter.addCommandToCode("la", Arrays.asList("$t0", value.getName()));
        SPIMFileWriter.addCommandToCode("lw", Arrays.asList("$t1", "0($t0)"));
        SPIMFileWriter.addCommandToCode("beqz", Arrays.asList("$t1", Stacks.topStartIFS()));
        SPIMFileWriter.addCommandToCode("j", Arrays.asList("$t1", Stacks.topStartElseS()));
        SPIMFileWriter.addLabel(Stacks.popStartIFS());
    }
    public static void completeIf() {
        SPIMFileWriter.addLabel(Stacks.popStartElseS());
    }
    public static void elseCode() {
        Stacks.pushStartElseS(CodeGeneratorImpl.generateNewLabel());
        SPIMFileWriter.addCommandToCode("j", Arrays.asList( Stacks.topStartElseS()));
        SPIMFileWriter.addLabel(Stacks.popStartElseS());
    }
    public static void completeElse() {
        SPIMFileWriter.addLabel(Stacks.popStartElseS());
    }
}
