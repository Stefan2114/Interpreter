package model.adts;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class MyList<T> implements MyIList<T> {

    private List<T> list;

    public MyList() {
        this.list = new CopyOnWriteArrayList<>();
    }

    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public List<T> getContent() {
        return this.list;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T elem : this.list) {
            str.append(elem).append("\n");
        }

        return str.toString();
    }
}
