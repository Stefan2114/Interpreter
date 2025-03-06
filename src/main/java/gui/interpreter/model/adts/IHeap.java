package gui.interpreter.model.adts;

import gui.interpreter.model.values.IValue;

import java.util.Map;

public interface IHeap {

    int allocate(IValue value);

    boolean contains(Integer key);

    IValue getValue(Integer key);

    void insert(Integer key, IValue value);

    void setContent(Map<Integer, IValue> map);

    Map<Integer, IValue> getContent();
}
