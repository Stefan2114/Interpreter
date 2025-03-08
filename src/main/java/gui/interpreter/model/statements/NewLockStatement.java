package gui.interpreter.model.statements;

import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.IntType;
import gui.interpreter.model.values.IntValue;

public class NewLockStatement implements IStatement {

    private String variableName;

    public NewLockStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState prgState) {

        int key = prgState.getLockTable().allocate(-1);
        prgState.getSymTable().insert(this.variableName, new IntValue(key));

        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        if (!(typeEnv.contains(this.variableName)))
            throw new TypeCheckException(
                    "Statement exception: the variable: " + this.variableName + " is not in the typeEnv");

        IType variableType = typeEnv.getValue(this.variableName);

        if (!(variableType.equals(new IntType())))
            throw new TypeCheckException(
                    "Statement exception: the variable: " + this.variableName + " doesn't have the type IntType");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(new String(this.variableName));
    }

    @Override
    public String toString() {
        return "newLock( " + this.variableName + " )";
    }
}
