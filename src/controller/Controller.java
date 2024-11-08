package controller;

import exceptions.*;
import model.adts.*;
import model.expressions.ArithmeticalExpression;
import model.expressions.ArithmeticalOperator;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.states.PrgState;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repo.IRepository;

import java.io.BufferedReader;

public class Controller implements IController {

    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    private PrgState oneStep(PrgState prgState) throws StatementException, KeyNotFoundException, ExpressionException, EmptyStackException, ControllerException {
        MyIStack<IStatement> execStack = prgState.getExecStack();
        if (execStack.isEmpty()) throw new ControllerException("prgState stack is empty");
        IStatement statement = execStack.pop();
        return statement.execute(prgState);

    }


    public MyIList<String> allSteps() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException, RepoException {
        PrgState prgState = this.repo.getCurrentPrgState();
        this.repo.logPrgStateExec();
        while (!prgState.getExecStack().isEmpty()) {
            oneStep(prgState);
            this.repo.logPrgStateExec();

        }
        return prgState.getOutputList();
    }

}
