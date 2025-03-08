package gui.interpreter.model.adts;

import java.util.List;

public interface MyIList<T> {

    //adds the new elem into the list
    void add(T elem);

    //returns the content of the list
    List<T> getContent();
}
