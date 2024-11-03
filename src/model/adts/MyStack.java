package model.adts;
import exceptions.EmptyStackException;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }


    @Override
    public void push(T elem) {
        this.stack.push(elem);
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.stack.isEmpty())
            throw new EmptyStackException();
        return this.stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T elem : this.stack)
            str.append(elem).append("\n\n");

        return "MyStack contains:\n" + str;
    }
}