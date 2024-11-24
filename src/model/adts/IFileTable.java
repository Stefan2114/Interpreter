package model.adts;

import exceptions.KeyNotFoundException;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public interface IFileTable {

    void insert(StringValue key, BufferedReader value);

    void remove(StringValue key) throws KeyNotFoundException;

    BufferedReader getValue(StringValue key) throws KeyNotFoundException;

    boolean contains(StringValue key);



}
