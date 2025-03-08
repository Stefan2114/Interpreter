package gui.interpreter.controller;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.statements.IStatement;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.values.*;
import gui.interpreter.repo.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.concurrent.Executors;

public class Controller implements IController {

    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.executor = Executors.newFixedThreadPool(2);

    }


    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }


    //keeps the addresses from the heap that are still used
    private void garbageCollector() {


        Map<Integer, IValue> heap = this.repo.getHeap().getContent();
        List<PrgState> prgList = this.repo.getPrgList();
        Set<Integer> usedAddr = getAllAddresses(prgList, heap);

        Map<Integer, IValue> newHeap = heap.entrySet().stream()
                .filter(e -> usedAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.repo.getHeap().setContent(newHeap);
    }


    //returns the addresses from the symTable
    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }


    //get the first layer of missed used addresses
    private Integer getMissingUsedAddress(Set<Integer> usedAddr, Map<Integer, IValue> heap) {

        return heap.entrySet().stream()
                .filter(e -> {
                    return (usedAddr.contains(e.getKey()) && e.getValue() instanceof RefValue
                            && !(usedAddr.contains(((RefValue) e.getValue()).getAddress())));
                })
                .map(e -> ((RefValue) e.getValue()).getAddress())
                .findFirst()
                .orElse(null);
    }


    //gets all the addresses used 
    private Set<Integer> getAllAddresses(List<PrgState> prgList, Map<Integer, IValue> heapContent) {

        Set<Integer> addresses = new HashSet<>();
        prgList.forEach(prg -> {
            List<Integer> symAddresses = getAddrFromSymTable(prg.getSymTable().getContent().values());
            addresses.addAll(symAddresses);
        });

        Integer missingAddress = getMissingUsedAddress(addresses, heapContent);
        if (missingAddress != null) {
            addresses.add(missingAddress);
        }

        while (missingAddress != null) {
            missingAddress = getMissingUsedAddress(addresses, heapContent);
            if (missingAddress != null) {
                addresses.add(missingAddress);
            }
        }

        return addresses;
    }


    public void oneStepForAllPrg() throws InterruptedException {

        List<PrgState> prgList = this.repo.getPrgList();
        prgList.forEach(prg -> this.repo.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .toList();


        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException e) {
                        throw new ControllerException(e.getCause());
                    } catch (InterruptedException e) {
                        throw new ControllerException(e);
                    }

                })
                .filter(Objects::nonNull)
                .toList();

        garbageCollector();
        prgList = removeCompletedPrg(prgList);
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> this.repo.logPrgStateExec(prg));
        this.repo.setPrgList(prgList);

    }


    public void allSteps() throws InterruptedException {

        List<PrgState> prgList = this.repo.getPrgList();
        while (prgList.size() > 0) {
            oneStepForAllPrg();
            prgList = this.repo.getPrgList();
        }
        this.executor.shutdownNow();
    }


    public void initProgramState() throws TypeCheckException, RepoException {
        this.repo.initPrgState();
    }

    public Integer nrOfPrgStates() {
        return this.repo.getPrgList().size();
    }

    public List<Integer> getPrgStatesID() {
        return this.repo.getPrgList().stream()
                .map(e -> e.getID())
                .toList();
    }

    @Override
    public Map<Integer, IValue> getHeap() {
        return this.repo.getHeap().getContent();
    }

    @Override
    public Set<StringValue> getFileTableKeys() {
        return this.repo.getFileTable().getKeys();
    }

    @Override
    public List<String> getOutputList() {
        return this.repo.getOutputList().getContent();
    }


    @Override
    public Map<String, IValue> getSymTableFromPrgState(Integer id) {
        return this.repo.getPrgList().stream()
                .filter(e -> e.getID().equals(id))
                .map(e -> e.getSymTable().getContent())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map<Integer, Integer> getLockTable() {
        return this.repo.getLockTable().getContent();
    }

    @Override
    public List<IStatement> getExeStackFromPrgState(Integer id) {
        return this.repo.getPrgList().stream()
                .filter(e -> e.getID().equals(id))
                .map(e -> e.getExecStack().getContent())
                .findFirst()
                .orElse(null);
    }


}
