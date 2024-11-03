package view;

import exceptions.*;

public interface IView {
    void start() throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException;
}
