package gui.interpreter.controller;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.*;
import gui.interpreter.model.statements.IStatement;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.StringValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IController {

    void allSteps() throws InterruptedException;
    void initProgramState() throws TypeCheckException, RepoException;
    void oneStepForAllPrg() throws InterruptedException;
    Integer nrOfPrgStates();
    List<Integer> getPrgStatesID();
    Map<Integer, IValue> getHeap();
    Set<StringValue> getFileTableKeys();
    List<String> getOutputList();
    Map<String, IValue> getSymTableFromPrgState(Integer id);
    Map<Integer, Integer> getLockTable();
    List<IStatement> getExeStackFromPrgState(Integer id);
}
