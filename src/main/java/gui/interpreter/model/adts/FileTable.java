package gui.interpreter.model.adts;

import gui.interpreter.exceptions.KeyNotFoundException;
import gui.interpreter.model.values.StringValue;

import java.io.BufferedReader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class FileTable implements IFileTable {

    private Map<StringValue, BufferedReader> map;

    public FileTable() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(StringValue key, BufferedReader value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(StringValue key) throws KeyNotFoundException {
        if (!this.map.containsKey(key))
            throw new KeyNotFoundException();

        this.map.remove(key);
    }

    @Override
    public BufferedReader getValue(StringValue key) throws KeyNotFoundException {

        if (!this.map.containsKey(key))
            throw new KeyNotFoundException();
        return this.map.get(key);
    }

    @Override
    public boolean contains(StringValue key) {
        return this.map.containsKey(key);
    }

    @Override
    public Set<StringValue> getKeys() {
        return this.map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (StringValue key : this.map.keySet())
            str.append(key.toString()).append(" -> ").append(this.map.get(key)).append('\n');
        return str.toString();

    }
}
