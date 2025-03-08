package gui.interpreter.model.statements;

import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.adts.MyMap;
import gui.interpreter.model.adts.MyStack;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.IValue;

public class ThreadCreationStatement implements IStatement {

    private IStatement statement;

    public ThreadCreationStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) {
        return new PrgState(this.statement.deepCopy(), new MyStack<IStatement>(),
                new MyMap<String, IValue>(prgState.getSymTable()), prgState.getOutputList(), prgState.getFileTable(),
                prgState.getHeap(), prgState.getLockTable());
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
        return "thread( " + this.statement.toString() + " );";
    }
}
