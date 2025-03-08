package gui.interpreter.model.statements;

import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;

public interface IStatement {
    PrgState execute(PrgState prgState);

    MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException;

    IStatement deepCopy();
}
