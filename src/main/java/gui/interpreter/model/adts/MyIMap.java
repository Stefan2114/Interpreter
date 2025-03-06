package gui.interpreter.model.adts;

import java.util.Map;


public interface MyIMap<K, V> {
    void insert(K key, V value);

    void remove(K key);

    V getValue(K key);

    boolean contains(K key);

    Map<K, V> getContent();

}
