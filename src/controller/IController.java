package controller;

import exceptions.*;
import model.adts.MyIList;

public interface IController {

    void allSteps() throws EmptyStackException, RepoException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
}
