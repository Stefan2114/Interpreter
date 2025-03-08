package gui.interpreter.model.adts;

import java.util.Map;

public interface MyIMap<K, V> {

    // inserts the new value into the map at the given key
    void insert(K key, V value);

    // removes from the map the given key
    void remove(K key);

    // returns the value found in the map at the given key
    V getValue(K key);

    // return true if there exists a record for the given key in the map
    boolean contains(K key);

    // returns the content of the map
    Map<K, V> getContent();

}
