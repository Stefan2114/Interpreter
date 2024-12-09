package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adts.MyList;
import model.adts.MyMap;
import model.adts.MyStack;
import model.states.PrgState;
import model.values.IValue;

public class ThreadCreationStatement implements IStatement {

    private IStatement statement;

    public ThreadCreationStatement(IStatement statement) {
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException {
        return new PrgState(this.statement.deepCopy(), new MyStack<IStatement>(), prgState.getSymTable().deepCopy(), prgState.getOutputList(), prgState.getFileTable(), prgState.getHeap());
    }

    @Override
    public IStatement deepCopy() {
        return new ThreadCreationStatement(this.statement.deepCopy());
    }

    @Override
    public String toString(){
        return "thread(" + this.statement.toString() + ");";
    }
}
