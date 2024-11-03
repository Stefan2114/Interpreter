package model.states;
import model.adts.MyIList;
import model.adts.MyIMap;
import model.adts.MyIStack;
import model.statements.IStatement;
import model.values.IValue;

public class PrgState {

    private MyIStack<IStatement> execStack;
    private MyIMap<String, IValue> symTable;
    private MyIList<String> outputList;
    private IStatement initialState;

    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIMap<String,IValue> symTable, MyIList<String> outputList){
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.initialState = initState.deepCopy();
        this.execStack.push(this.initialState);

    }

    public MyIStack<IStatement> getExecStack() {
        return this.execStack;
    }
    public MyIMap<String, IValue> getSymTable() {
        return this.symTable;
    }

    public MyIList<String> getOutputList(){
        return this.outputList;
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


    @Override
    public String toString(){
        return this.execStack.toString() + '\n' + this.symTable.toString() + '\n' + this.outputList.toString() + '\n';
    }

}
