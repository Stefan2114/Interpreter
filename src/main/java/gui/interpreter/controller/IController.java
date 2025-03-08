package gui.interpreter.controller;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.statements.IStatement;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.StringValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IController {

    //runs the intiere program
    void allSteps() throws InterruptedException;

    //initialize the program state and checks if the files are compileable
    void initProgramState() throws TypeCheckException, RepoException;

    //runs one step for all current states
    void oneStepForAllPrg() throws InterruptedException;

    //returns the nr of current prgStates of the program
    Integer nrOfPrgStates();

    //returns the list of the prgStates
    List<Integer> getPrgStatesID();

    //returns the heap that is shared by all prgStates
    Map<Integer, IValue> getHeap();

    //returns the keys from the file table that is shared
    Set<StringValue> getFileTableKeys();

    //returns the output list 
    List<String> getOutputList();

    //returns the symTable of the coresponding prgState with the given id
    Map<String, IValue> getSymTableFromPrgState(Integer id);

    //returns the lock table that is shared by the prgStates
    Map<Integer, Integer> getLockTable();

    //returns the exeStack of the coresponding prgState with the given id
    List<IStatement> getExeStackFromPrgState(Integer id);
}
