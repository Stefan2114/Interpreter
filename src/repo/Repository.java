package repo;

import model.adts.MyIList;
import model.states.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<PrgState> prgStateList;
    private int currentStatePosition;

    public Repository(){
        this.prgStateList = new ArrayList<>();
        this.currentStatePosition = -1;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        this.prgStateList.add(prgState);
        this.currentStatePosition++;
    }

    @Override
    public PrgState getCurrentPrgState() {
        return this.prgStateList.get(this.currentStatePosition);
    }

    @Override
    public void logPrgStateExec() {

    }

    @Override
    public List<PrgState> getStates() {
        return this.prgStateList;
    }
}
