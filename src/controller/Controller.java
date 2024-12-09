package controller;

import exceptions.*;
import model.adts.*;
import model.statements.*;
import model.states.PrgState;
import model.values.*;
import repo.IRepository;
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
    }


    //could be a problem for immutable list
    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void garbageCollector(List<PrgState> prgList) {


        Map<Integer, IValue> heap = this.repo.getHeap().getContent();
        Set<Integer> usedAddr = getAllAddresses(prgList, heap);

        Map<Integer, IValue> newHeap =  heap.entrySet().stream()
                .filter(e -> usedAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.repo.getHeap().setContent(newHeap);
    }


    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }


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


    private void oneStepForAllPrg(List<PrgState> prgList) throws ControllerRuntimeException, InterruptedException {

        prgList.forEach(prg -> this.repo.logPrgStateExec(prg) );

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .toList();


        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException e) {
                        throw new ControllerRuntimeException(e.getCause());
                    }catch (InterruptedException e) {
                        throw new ControllerRuntimeException(e);
                    }

                })
                .filter(Objects::nonNull)
                .toList();

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> this.repo.logPrgStateExec(prg) );
        this.repo.setPrgList(prgList);

    }


    public void allSteps() throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(this.repo.getPrgList());
        while(prgList.size() > 0){

            ////// the garbage collector removes the unbounded variables so we could use that space again, but after all the prgStates are finished should it delete the remaining variables?????
            garbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(this.repo.getPrgList());

        }
        this.executor.shutdownNow();
        repo.setPrgList(prgList);
    }

}
