package repo;

import exceptions.TypeCheckException;
import model.adts.IHeap;
import model.states.PrgState;

import java.util.List;


public interface IRepository {


    void logPrgStateExec(PrgState prgState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    IHeap getHeap();
    public void initPrgState() throws TypeCheckException;
}
