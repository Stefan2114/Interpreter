package repo;

import exceptions.RepoException;
import model.adts.IHeap;
import model.states.PrgState;
import model.adts.MyIList;

import java.util.List;


public interface IRepository {


    void logPrgStateExec(PrgState prgState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    IHeap getHeap();
}
