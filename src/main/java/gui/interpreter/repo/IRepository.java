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


    void logPrgStateExec(PrgState prgState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    IHeap getHeap();
    IFileTable getFileTable();
    MyIList<String> getOutputList();
    ILockTable getLockTable();
    public void initPrgState() throws TypeCheckException, RepoException;
}
