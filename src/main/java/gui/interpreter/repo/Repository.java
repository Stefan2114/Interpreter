package gui.interpreter.repo;

import gui.interpreter.exceptions.RepoException;
import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.*;
import gui.interpreter.model.statements.IStatement;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.IValue;

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
    private ILockTable lockTable;
    private MyIList<String> outputList;
    private IFileTable fileTable;
    private IStatement initStatement;

    public Repository(IStatement initStatement, String logFilePath) {

        this.initStatement = initStatement;
        this.logFilePath = logFilePath;
        this.prgStateList = new ArrayList<>();
        this.heap = new Heap();
        this.fileTable = new FileTable();
        this.outputList = new MyList<>();
        this.lockTable = new LockTable();

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
    public void initPrgState() throws TypeCheckException, RepoException {

        initLogFile();
        initStatement.typeCheck(new MyMap<String, IType>());

        PrgState prgState = new PrgState(initStatement, new MyStack<IStatement>(), new MyMap<String, IValue>(), this.outputList, this.fileTable, this.heap, this.lockTable);
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
    public IFileTable getFileTable() {
        return this.fileTable;
    }

    @Override
    public MyIList<String> getOutputList() {
        return this.outputList;
    }

    @Override
    public ILockTable getLockTable() {
        return this.lockTable;
    }

    @Override
    public void close() {
        if (this.logFile != null)
            this.logFile.close();
    }
}
