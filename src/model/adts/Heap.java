package model.adts;

import exceptions.KeyNotFoundException;
import model.values.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap implements IHeap {

    private Map<Integer, IValue> map;

    public Heap() {
        this.map = new HashMap<>();
    }


    @Override
    public int allocate(IValue value) {
        int i = 1;
        while (this.map.containsKey(i))
            i++;
        this.map.put(i, value);
        return i;
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
