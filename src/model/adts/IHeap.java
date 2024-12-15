package model.adts;

import exceptions.KeyNotFoundException;
import model.values.IValue;

import java.util.Map;
import java.util.Set;

public interface IHeap {

    int allocate(IValue value);

    boolean contains(Integer key);

    IValue getValue(Integer key) throws KeyNotFoundException;

    void insert(Integer key, IValue value);

    void setContent(Map<Integer, IValue> map);

    Map<Integer, IValue> getContent();
}
