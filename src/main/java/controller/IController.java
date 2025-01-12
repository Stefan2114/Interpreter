package controller;

import exceptions.*;
import model.adts.*;
import model.statements.IStatement;
import model.states.PrgState;
import model.values.IValue;

import java.util.List;

public interface IController {

    void allSteps() throws InterruptedException;
    void initProgramState() throws TypeCheckException;
    void oneStepForAllPrg() throws InterruptedException;
    Integer nrOfPrgStates();
    List<Integer> getPrgStatesID();
    IHeap getHeap();
    IFileTable getFileTable();
    MyIList<String> getOutputList();
    MyIMap<String, IValue> getSymTableFromPrgState(Integer id);
    MyIStack<IStatement> getExeStackFromPrgState(Integer id);
}
