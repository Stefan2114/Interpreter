package gui.interpreter.model.adts;

import java.util.List;

public interface MyIStack<T> {

    // pushes the elem on top of the stack
    void push(T elem);

    // pops the top element of the stack
    T pop();

    // cheks if the stack is empty
    boolean isEmpty();

    // returns the content of the stack
    List<T> getContent();

}
