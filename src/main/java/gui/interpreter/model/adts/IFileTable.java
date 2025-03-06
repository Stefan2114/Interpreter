package gui.interpreter.model.adts;

import gui.interpreter.model.values.StringValue;

import java.io.BufferedReader;
import java.util.Set;

public interface IFileTable {

    void insert(StringValue key, BufferedReader value);

    void remove(StringValue key);

    BufferedReader getValue(StringValue key);

    boolean contains(StringValue key);

    Set<StringValue> getKeys();


}
