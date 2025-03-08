package gui.interpreter.model.statements;

import gui.interpreter.exceptions.StatementException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;

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
