package view;

import controller.Controller;
import controller.IController;
import exceptions.*;
import model.adts.MyList;
import model.adts.MyMap;
import model.adts.MyStack;
import model.expressions.*;
import model.statements.*;
import model.states.PrgState;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

import java.beans.Expression;
import java.util.Scanner;

public class View implements IView{

    private IController controller;

    public View(IController controller){
        this.controller = controller;
    }


    @Override
    public void start() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        System.out.println("This is my ToyLanguage");
        while(true){
            System.out.println("Options: ");
            System.out.println("5. Program 1");
            System.out.println("4. Program 2");
            System.out.println("3. Program 3");
            System.out.println("4. Exit");
            System.out.println("What would you want to see?");
            System.out.print(">>>>>>>>>");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            switch(input){
                case "1" ->{
                    prg1();
                }
                case "2" ->{
                    prg2();
                }
                case "3" ->{
                    prg3();
                }
                case "4" ->{
                    break;
                }
                default ->{
                    System.out.println("Invalid input");
                }
            }

        }
    }


    private void prg1() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        IStatement st = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v",
                        new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))) );

        PrgState initPrgState = new PrgState(st, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>());
        this.controller.add(initPrgState);
        this.controller.allSteps();
    }

    private void prg2() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {


        IStatement st = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()), new CompStatement(new AssignStatement("a",
                        new ArithmeticalExpression(new ValueExpression(new IntValue(2)), new ArithmeticalExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticalOperator.MULTIPLY), ArithmeticalOperator.ADD)
                ),new CompStatement(new AssignStatement("b", new ArithmeticalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), ArithmeticalOperator.ADD)),
                        new PrintStatement(new VariableExpression("b"))))
                ));

        PrgState initPrgState = new PrgState(st, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>());
        this.controller.add(initPrgState);
        this.controller.allSteps();
    }

    private void prg3() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {

        PrintStatement pr = new PrintStatement(new VariableExpression("v"));
        CompStatement cm4 = new CompStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), pr);
        CompStatement cm3 = new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))), cm4);
        CompStatement cm2 = new CompStatement(new VarDeclStatement("v", new IntType()), cm3);
        CompStatement cm1 = new CompStatement(new VarDeclStatement("a", new BoolType()), cm2);

        PrgState initPrgState = new PrgState(cm1, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>());
        this.controller.add(initPrgState);
        this.controller.allSteps();
    }

}
