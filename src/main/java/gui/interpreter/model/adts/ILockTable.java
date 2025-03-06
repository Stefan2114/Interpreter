package gui.interpreter.model.adts;


import java.util.Map;

public interface ILockTable {

    int allocate(Integer value);

    boolean contains(Integer key);

    Integer getValue(Integer key);

    void insert(Integer key, Integer value);

    Map<Integer, Integer> getContent();
}


