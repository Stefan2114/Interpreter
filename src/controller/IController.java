package controller;

import exceptions.*;
import model.adts.MyIList;
import model.adts.MyList;
import model.statements.IStatement;
import model.states.PrgState;

public interface IController {

    void add(PrgState prgState);
    MyIList<String> allSteps() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
    MyIList<String> prg1() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
    MyIList<String> prg2() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
    MyIList<String> prg3() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
}