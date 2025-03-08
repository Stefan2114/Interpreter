package gui.interpreter.model.adts;

import java.util.Map;

import gui.interpreter.exceptions.KeyNotFoundException;

import java.util.HashMap;

public class MyMap<K, V> implements MyIMap<K, V> {
    private Map<K, V> map;

    public MyMap() {
        map = new HashMap<K, V>();
    }

    public MyMap(MyIMap<K, V> oldMap) {
        this.map = new HashMap<>(oldMap.getContent());
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException();
        }
        this.map.remove(key);
    }

    @Override
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException();
        }
        return this.map.get(key);
    }

    @Override
    public boolean contains(K otherKey) {

        return this.map.containsKey(otherKey);
    }

    @Override
    public Map<K, V> getContent() {
        return this.map;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (K key : this.map.keySet()) {
            str.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return str.toString();
    }
}
