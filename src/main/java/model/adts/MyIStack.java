package model.adts;

import exceptions.EmptyStackException;
import model.statements.IStatement;

import java.util.List;

public interface MyIStack<T> {

    void push(T elem);

    T pop() throws EmptyStackException;

    boolean isEmpty();

    List<T> getContent();

}
