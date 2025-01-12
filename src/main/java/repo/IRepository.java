package repo;

import exceptions.TypeCheckException;
import model.adts.IFileTable;
import model.adts.IHeap;
import model.adts.MyIList;
import model.states.PrgState;

import java.util.List;


public interface IRepository {


    //void logPrgStateExec(PrgState prgState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    IHeap getHeap();
    IFileTable getFileTable();
    MyIList<String> getOutputList();
    public void initPrgState() throws TypeCheckException;
}
