package model.states;

import model.adts.*;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class PrgState {

    private MyIStack<IStatement> execStack;
    private MyIMap<String, IValue> symTable;
    private MyIList<String> outputList;
    private IStatement initialState;
    private IFileTable fileTable;
    private IHeap heap;

    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIMap<String, IValue> symTable, MyIList<String> outputList, IFileTable fileTable, IHeap heap) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.initialState = initState.deepCopy();
        this.execStack.push(this.initialState);
        this.fileTable = fileTable;
        this.heap = heap;

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

    public void setExecStack(MyIStack<IStatement> execStack) {
        this.execStack = execStack;
    }

    public void setSymTable(MyIMap<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOutputList(MyIList<String> outputList) {
        this.outputList = outputList;
    }

    public void setHeap(IHeap heap) {
        this.heap = heap;
    }

    @Override
    public String toString() {
        return "ExecStack:\n" + this.execStack.toString() + "\nSymTable:\n" + this.symTable.toString() + "\nOut:\n" + this.outputList.toString() + "\nFileTable:\n" + this.fileTable.toString() + "\nHeap:\n" + this.heap.toString() + '\n';
    }

}
