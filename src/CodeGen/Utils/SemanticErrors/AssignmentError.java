package CodeGen.Utils.SemanticErrors;

import CodeGen.SymbolTable.Descriptor;
import CodeGen.Utils.Type;

public class AssignmentError extends RuntimeException {

    public void checkAssignmentError(Descriptor lValue, Descriptor Rvalue) {
        if (lValue.getType() == Type.REAL_NUMBER && lValue.getType() == Type.INTEGER_NUMBER) {
            return;
        }
        if (lValue.getType() != lValue.getType()) {
            throw new RuntimeException("Error message: Assignment Exception");
        }
    }

}
