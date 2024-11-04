package view;

import controller.Controller;
import controller.IController;
import exceptions.*;
import model.adts.MyIList;
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
        MyIList<String> list = this.controller.prg1();
        System.out.println(list.toString());
    }

    private void prg2() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {

        MyIList<String> list = this.controller.prg2();
        System.out.println(list.toString());
    }

    private void prg3() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {

        MyIList<String> list = this.controller.prg3();
        System.out.println(list.toString());
    }

}
