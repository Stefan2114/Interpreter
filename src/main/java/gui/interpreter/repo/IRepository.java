package gui.interpreter.repo;

import gui.interpreter.exceptions.RepoException;
import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.IFileTable;
import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.ILockTable;
import gui.interpreter.model.adts.MyIList;
import gui.interpreter.model.states.PrgState;

import java.util.List;

public interface IRepository {

    // writes in a log file the state of the program
    void logPrgStateExec(PrgState prgState);

    // returns a list of the current programs
    List<PrgState> getPrgList();

    // sets a new program list
    void setPrgList(List<PrgState> newList);

    // returns the heap that is shared by all programs
    IHeap getHeap();

    // return the file table that is shared by all programs
    IFileTable getFileTable();

    // returns the output list that is shared by all programs
    MyIList<String> getOutputList();

    // returns the lock table that is shared by all programs
    ILockTable getLockTable();

    // initializes the program state and checks if the program is ready for
    // compilation
    public void initPrgState() throws TypeCheckException, RepoException;
}
