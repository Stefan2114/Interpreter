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
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
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

    public MyIList<String> allSteps() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        PrgState prgState = this.repo.getCurrentPrgState();
        //System.out.println(prgState.toString());
        while(!prgState.getExecStack().isEmpty()){
            oneStep(prgState);
            //System.out.println("-------------------------------------------------------------------");
            //System.out.println(prgState.toString());

        }
        return prgState.getOutputList();
    }

    @Override
    public MyIList<String> prg1() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        IStatement st = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v",
                        new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))) );

        PrgState initPrgState = new PrgState(st, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>());
        add(initPrgState);
        return allSteps();
    }

    @Override
    public MyIList<String> prg2() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        IStatement st = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()), new CompStatement(new AssignStatement("a",
                        new ArithmeticalExpression(new ValueExpression(new IntValue(2)), new ArithmeticalExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticalOperator.MULTIPLY), ArithmeticalOperator.ADD)
                ),new CompStatement(new AssignStatement("b", new ArithmeticalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), ArithmeticalOperator.ADD)),
                        new PrintStatement(new VariableExpression("b"))))
                ));

        PrgState initPrgState = new PrgState(st, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>());
        add(initPrgState);
        return allSteps();
    }

    @Override
    public MyIList<String> prg3() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        PrintStatement pr = new PrintStatement(new VariableExpression("v"));
        CompStatement cm4 = new CompStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), pr);
        CompStatement cm3 = new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))), cm4);
        CompStatement cm2 = new CompStatement(new VarDeclStatement("v", new IntType()), cm3);
        CompStatement cm1 = new CompStatement(new VarDeclStatement("a", new BoolType()), cm2);

        PrgState initPrgState = new PrgState(cm1, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>());
        add(initPrgState);
        return allSteps();
    }
}
