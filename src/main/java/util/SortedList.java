package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SortedList<T extends Comparable<T>> {

    private final List<T> list = new ArrayList<>();
    private boolean isSorted = false;

    public void add(final T value) {
        list.add(value);
        isSorted = false;
    }

    public T get(final int index) {
        sort();
        return list.get(index);
    }

    public Stream<T> stream() {
        sort();
        return list.stream();
    }

    private void sort() {
        if (!isSorted) {
            Collections.sort(list);
            isSorted = true;
        }
    }
}
