package gui.interpreter.model.adts;

import gui.interpreter.exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockTable implements  ILockTable{

    private Map<Integer, Integer> map;
    private int currentPos;

    public LockTable() {
        this.map = new ConcurrentHashMap<>();
        this.currentPos = 0;
    }

    private synchronized int getNewPos(){
        this.currentPos++;
        return currentPos;
    }


    @Override
    public int allocate(Integer value) {
        int pos = getNewPos();

        this.map.put(pos, value);
        return pos;
    }

    @Override
    public boolean contains(Integer key) {
        return this.map.containsKey(key);
    }

    @Override
    public Integer getValue(Integer key) throws KeyNotFoundException {
        if (!(this.map.containsKey(key)))
            throw new KeyNotFoundException();

        return this.map.get(key);
    }

    @Override
    public void insert(Integer key, Integer value) {
        this.map.put(key, value);
    }


    @Override
    public Map<Integer, Integer> getContent() {
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
