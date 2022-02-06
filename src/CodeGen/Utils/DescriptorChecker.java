package CodeGen.Utils;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.SymbolTable.GlobalSymbolTable;
import CodeGen.SymbolTable.Stacks;

public class DescriptorChecker {
    public static void IsInSymbolTable(Descriptor descriptor , boolean findInGlobal) {
        if(findInGlobal){
            if (!GlobalSymbolTable.getSymbolTable().containsDescriptor(descriptor.getName())) {
                try {
//                throw new RuntimeException("Global Symbol Table Stack does not contain " + descriptor.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            if (!Stacks.topSymbolTableS().containsDescriptor(descriptor.getName())) {
                try {
//                    throw new RuntimeException("Symbol Table Stack does not contain " + descriptor.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void isNotInSymbolTable(String name, boolean findInGlobal) throws Exception {
        if(findInGlobal){
            if (!GlobalSymbolTable.getSymbolTable().isEmpty()) {
                if (GlobalSymbolTable.getSymbolTable().contains(name)) {
                    try {
//                     throw new RuntimeException("Global Symbol Table Stack contains " + name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            if (!Stacks.isSymbolTableSEmpty()) {
                if (Stacks.topSymbolTableS().contains(name)) {
//                    throw new RuntimeException("Symbol Table Stack contains " + name);
                }
            }
        }

    }

}
