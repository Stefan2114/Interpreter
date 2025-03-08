package gui.interpreter.model.adts;

import gui.interpreter.model.values.StringValue;

import java.io.BufferedReader;
import java.util.Set;

public interface IFileTable {


    //inserts the new value into the table at the given key
    void insert(StringValue key, BufferedReader value);

    //removes the given key from the table
    void remove(StringValue key);

    //returns the bufferedReader that is located at the coresponding key
    BufferedReader getValue(StringValue key);

    //returns true if there is a record for that key in the table, false otherwise
    boolean contains(StringValue key);

    //returns the set of keys from the table
    Set<StringValue> getKeys();


}
