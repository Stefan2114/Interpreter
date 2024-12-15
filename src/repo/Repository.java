package repo;

import exceptions.RepoException;
import exceptions.TypeCheckException;
import model.adts.*;
import model.statements.IStatement;
import model.states.PrgState;
import model.types.IType;
import model.values.IValue;

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
    private IStatement initStatement;

    public Repository(IStatement initStatement, String logFilePath) throws RepoException {

        this.initStatement = initStatement;
        this.logFilePath = logFilePath;
        this.heap = new Heap();
        this.prgStateList = new ArrayList<>();
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
    public void initPrgState() throws TypeCheckException {

        initStatement.typeCheck(new MyMap<String, IType>());

        PrgState prgState = new PrgState(initStatement, new MyStack<IStatement>(), new MyMap<String, IValue>(), new MyList<String>(), new FileTable(), this.heap);
        this.prgStateList.add(prgState);
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
        if (this.logFile != null)
            this.logFile.close();
    }
}
