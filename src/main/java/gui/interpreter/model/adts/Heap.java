package gui.interpreter.model.adts;

import gui.interpreter.exceptions.KeyNotFoundException;
import gui.interpreter.model.values.IValue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


public class Heap implements IHeap {

    private Map<Integer, IValue> map;
    private int currentPos;

    public Heap() {
        this.map = new ConcurrentHashMap<>();
        this.currentPos = 0;
    }

    private synchronized int getNewPos(){
        this.currentPos++;
        return currentPos;
    }


    @Override
    public int allocate(IValue value) {
        int pos = getNewPos();

        //could be a problem at resize if is not a concurrentHashMap although I have getNewPos synchronized
        this.map.put(pos, value);
        return pos;
    }

    @Override
    public boolean contains(Integer key) {
        return this.map.containsKey(key);
    }

    @Override
    public IValue getValue(Integer key) throws KeyNotFoundException {
        if (!(this.map.containsKey(key)))
            throw new KeyNotFoundException();

        return this.map.get(key);
    }

    @Override
    public void insert(Integer key, IValue value) {
        this.map.put(key, value);
    }

    @Override
    public void setContent(Map<Integer, IValue> map) {
        this.map = map;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return this.map;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer key : this.map.keySet())
            str.append(key).append(" -> ").append(this.map.get(key)).append('\n');
        return str.toString();
    }

}
