package controller;

import exceptions.*;
import model.statements.IStatement;
import model.states.PrgState;

public interface IController {

    void add(PrgState prgState);
    void allSteps() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
}