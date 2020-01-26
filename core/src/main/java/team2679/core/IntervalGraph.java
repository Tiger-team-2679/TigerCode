package team2679.core;

import java.util.ArrayList;
import java.util.Iterator;

public class IntervalGraph<T> implements Iterable<T> {

    public ArrayList<T> list;
    public double step;

    public IntervalGraph(ArrayList<T> list, double step) {
        this.list = list;
        this.step = step;
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }
}
