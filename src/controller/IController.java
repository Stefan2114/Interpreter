package controller;

import exceptions.*;
import model.adts.MyIList;

public interface IController {

    void allSteps() throws InterruptedException, TypeCheckException;
}
