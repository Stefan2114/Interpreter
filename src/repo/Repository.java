package repo;

import exceptions.RepoException;
import model.adts.IHeap;
import model.adts.MyIList;
import model.states.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository, AutoCloseable {

    private List<PrgState> prgStateList;
    private String logFilePath;
    private PrintWriter logFile;
    private IHeap heap;

    public Repository(PrgState prgState, String logFilePath) throws RepoException {
        this.prgStateList = new ArrayList<>();
        this.prgStateList.add(prgState);
        this.logFilePath = logFilePath;
        this.heap = prgState.getHeap();
        initLogFile();
    }

    private void initLogFile() throws RepoException {
        try {
            new FileWriter(this.logFilePath).close();
            this.logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new RepoException("File: " + this.logFilePath + " couldn't be created or edited");
        }
    }



    @Override
    public void logPrgStateExec(PrgState prgState) {
        logFile.println(prgState.toString());
        logFile.flush();
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStateList;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        this.prgStateList = newList;
    }

    public IHeap getHeap() {
        return heap;
    }

    @Override
    public void close() {
        if(this.logFile != null)
            this.logFile.close();
    }
}
