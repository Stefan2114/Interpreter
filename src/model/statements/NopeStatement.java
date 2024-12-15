package model.statements;

import exceptions.StatementException;
import model.adts.MyIMap;
import model.states.PrgState;
import model.types.IType;

public class NopeStatement implements IStatement {

    @Override
    public PrgState execute(PrgState prgState) {
        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws StatementException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new NopeStatement();
    }


    @Override
    public String toString() {
        return "NopeStatement";
    }
}
