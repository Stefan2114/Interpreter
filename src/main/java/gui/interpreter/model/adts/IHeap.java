package gui.interpreter.model.adts;

import gui.interpreter.model.values.IValue;

import java.util.Map;

public interface IHeap {

    // allocates a new address in the heap with the coresponding value. It returns
    // that address
    int allocate(IValue value);

    // returns true if there is a record for that key in the table, false otherwise
    boolean contains(Integer key);

    // returns the IValue that is located at the coresponding key
    IValue getValue(Integer key);

    // inserts the new value into the table at the given key
    void insert(Integer key, IValue value);

    // ovverides the old content of the heap with a new one
    void setContent(Map<Integer, IValue> map);

    // returns the current content of the ehap
    Map<Integer, IValue> getContent();

}
