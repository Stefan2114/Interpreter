package controller;

import exceptions.*;
import exceptions.EmptyStackException;
import model.adts.*;
import model.expressions.ArithmeticalExpression;
import model.expressions.ArithmeticalOperator;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.states.PrgState;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.*;
import repo.IRepository;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements IController {

    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }


    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> usedAddr, Map<Integer, IValue> heap) {

        return heap.entrySet().stream()
                .filter(e -> usedAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .toList();
    }



    private Integer getMissingUsedAddress(List<Integer> usedAddr, Map<Integer, IValue> heap){

        return heap.entrySet().stream()
                .filter(e -> {
                    return (usedAddr.contains(e.getKey()) && e.getValue() instanceof RefValue
                            && !(usedAddr.contains(((RefValue) e.getValue()).getAddress())));
                })
                .map(e -> ((RefValue) e.getValue()).getAddress())
                .findFirst()
                .orElse(null);
    }


    private List<Integer> getAllAddresses(List<Integer> symTableAddresses, Map<Integer, IValue> heapContent){

        List<Integer> addresses = new ArrayList<>(symTableAddresses);
        Integer missingAddress = getMissingUsedAddress(addresses, heapContent);
        if (missingAddress != null) {
            addresses.add(missingAddress);
        }

        while(missingAddress != null) {
            missingAddress = getMissingUsedAddress(addresses, heapContent);
            if (missingAddress != null) {
                addresses.add(missingAddress);
            }
        }

        return addresses;
    }



    private PrgState oneStep(PrgState prgState) throws StatementException, KeyNotFoundException, ExpressionException, EmptyStackException, ControllerException {
        MyIStack<IStatement> execStack = prgState.getExecStack();
        if (execStack.isEmpty()) throw new ControllerException("prgState stack is empty");
        IStatement statement = execStack.pop();
        return statement.execute(prgState);

    }


    public void allSteps() throws RepoException, EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {
        PrgState prgState = this.repo.getCurrentPrgState();
        this.repo.logPrgStateExec();
        while (!prgState.getExecStack().isEmpty()) {
            oneStep(prgState);
            this.repo.logPrgStateExec();

            List<Integer> symTableAddresses = getAddrFromSymTable(prgState.getSymTable().getContent().values());
            Map<Integer, IValue> heapContent = prgState.getHeap().getContent();
            List<Integer> addresses = getAllAddresses(symTableAddresses, heapContent);
            prgState.getHeap().setContent(unsafeGarbageCollector(addresses, heapContent));
            this.repo.logPrgStateExec();

        }
    }

}
