package model.statements;

import exceptions.TypeCheckException;
import model.adts.MyIMap;
import model.adts.MyMap;
import model.adts.MyStack;
import model.states.PrgState;
import model.types.IType;
import model.values.IValue;

public class ThreadCreationStatement implements IStatement {

    private IStatement statement;

    public ThreadCreationStatement(IStatement statement) {
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState prgState) {
        return new PrgState(this.statement.deepCopy(), new MyStack<IStatement>(), new MyMap<String, IValue>(prgState.getSymTable()), prgState.getOutputList(), prgState.getFileTable(), prgState.getHeap());
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {

        this.statement.typeCheck(new MyMap<String, IType>(typeEnv));
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new ThreadCreationStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "thread(" + this.statement.toString() + ");";
    }
}
