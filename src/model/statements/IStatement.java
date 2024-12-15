package model.statements;


import exceptions.TypeCheckException;
import model.adts.MyIMap;
import model.states.PrgState;
import model.types.IType;

public interface IStatement {
    PrgState execute(PrgState prgState);
    MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException;
    IStatement deepCopy();
}
