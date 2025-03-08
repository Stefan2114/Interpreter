package gui.interpreter.model.states;

import gui.interpreter.model.adts.*;
import gui.interpreter.model.statements.IStatement;
import gui.interpreter.model.values.IValue;

public class PrgState {

    private MyIStack<IStatement> execStack;
    private MyIMap<String, IValue> symTable;
    private MyIList<String> outputList;
    private IStatement initialState;
    private IFileTable fileTable;
    private IHeap heap;
    private ILockTable lockTable;
    private int id;
    private static int lastIndex = 0;

    private synchronized int getNewIndex() {
        lastIndex++;
        return lastIndex;
    }

    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIMap<String, IValue> symTable,
            MyIList<String> outputList, IFileTable fileTable, IHeap heap, ILockTable lockTable) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.initialState = initState.deepCopy();
        this.execStack.push(this.initialState);
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.id = getNewIndex();

    }

    public MyIStack<IStatement> getExecStack() {
        return this.execStack;
    }

    public MyIMap<String, IValue> getSymTable() {
        return this.symTable;
    }

    public MyIList<String> getOutputList() {
        return this.outputList;
    }

    public IFileTable getFileTable() {
        return this.fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public ILockTable getLockTable() {
        return this.lockTable;
    }

    public void setSymTable(MyIMap<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setHeap(IHeap heap) {
        this.heap = heap;
    }

    public boolean isNotCompleted() {
        return !(this.getExecStack().isEmpty());
    }

    public Integer getID() {
        return this.id;
    }

    public PrgState oneStep() {

        if (this.getExecStack().isEmpty())
            return null;
        IStatement statement = this.getExecStack().pop();
        return statement.execute(this);

    }

    @Override
    public String toString() {
        return "PrgState with id: " + this.id + "\nExecStack:\n" + this.execStack.toString() +
                "\nSymTable:\n" + this.symTable.toString() + "\nOut:\n" + this.outputList.toString() +
                "\nFileTable:\n" + this.fileTable.toString() + "\nHeap:\n" + this.heap.toString() +
                "\nLockTable:\n" + this.lockTable.toString()
                + '\n';
    }

}
