package gui.interpreter.model.adts;

import java.util.Map;

public interface ILockTable {

    // allocates a new address in the heap with the coresponding value. It returns
    // that address
    int allocate(Integer value);

    // returns true if there is a record for that key in the table, false otherwise
    boolean contains(Integer key);

    // returns the Integer that is located at the coresponding key
    Integer getValue(Integer key);

    // inserts the new value into the table at the given key
    void insert(Integer key, Integer value);

    // returns the current content of the table
    Map<Integer, Integer> getContent();

}
