package controller;

import exceptions.*;
import model.adts.MyIStack;
import model.statements.IStatement;
import model.states.PrgState;
import repo.IRepository;

public class Controller implements IController{

    private IRepository repo;
    public Controller(IRepository repo){
        this.repo = repo;
    }

    private PrgState oneStep(PrgState prgState) throws StatementException, KeyNotFoundException, ExpressionException, EmptyStackException, ControllerException {
        MyIStack<IStatement> execStack = prgState.getExecStack();
        if(execStack.isEmpty()) throw new ControllerException("prgState stack is empty");
        IStatement statement = execStack.pop();
        return statement.execute(prgState);

    }

    @Override
    public void add(PrgState prgState) {
        this.repo.addPrgState(prgState);
    }

    public void allSteps() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        PrgState prgState = this.repo.getCurrentPrgState();
        System.out.println(prgState.toString());
        while(!prgState.getExecStack().isEmpty()){
            oneStep(prgState);
            System.out.println("-------------------------------------------------------------------");
            System.out.println(prgState.toString());

        }
    }
}
