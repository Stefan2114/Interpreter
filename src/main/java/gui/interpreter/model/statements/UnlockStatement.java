package gui.interpreter.model.statements;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gui.interpreter.exceptions.StatementException;
import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.IntType;
import gui.interpreter.model.values.IntValue;

public class UnlockStatement implements IStatement {

    private String variableName;


    public UnlockStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState prgState) {

        int key = ((IntValue) prgState.getSymTable().getValue(this.variableName)).getValue();
        if (!(prgState.getLockTable().contains(key)))
            throw new StatementException("The variable: " + this.variableName + " is not a lock");


        if (prgState.getLockTable().getValue(key) == prgState.getID()) {
            prgState.getLockTable().insert(key, -1);
        }

        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        if (!(typeEnv.contains(this.variableName)))
            throw new TypeCheckException(
                    "Statement exception: the variable " + this.variableName + " was not found in the TypeEnv");
        IType variableType = typeEnv.getValue(this.variableName);

        if (!(variableType.equals(new IntType())))
            throw new TypeCheckException(
                    "Statement exception: the variable: " + this.variableName + " doesn't have the type IntType");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new UnlockStatement(new String(this.variableName));
    }

    @Override
    public String toString() {

        return "unlock( " + this.variableName + " )";
    }
}
