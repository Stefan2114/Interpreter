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

public class LockStatement implements IStatement {

    private String variableName;
    private static final Lock mutex = new ReentrantLock();


    public LockStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState prgState) {

        int key = ((IntValue) prgState.getSymTable().getValue(this.variableName)).getValue();
        if (!(prgState.getLockTable().contains(key)))
            throw new StatementException("The variable: " + this.variableName + " is not a lock");
        
        mutex.lock();
        if (prgState.getLockTable().getValue(key) == -1) {
            prgState.getLockTable().insert(key, prgState.getID());
        } else {
            prgState.getExecStack().push(this);//maybe a deepcopy will fix this
        }
        mutex.unlock();

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
        return new LockStatement(new String(this.variableName));
    }

    @Override
    public String toString() {

        return "lock( " + this.variableName + " )";
    }
}
