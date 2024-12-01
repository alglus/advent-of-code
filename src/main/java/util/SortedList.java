package util;

import java.util.*;

public class SortedList<T extends Comparable<T>> extends AbstractList<T> implements List<T> {
    private final List<T> list = new ArrayList<>();
    private boolean isSorted = false;

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        sort();
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        sort();
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        sort();
        return list.toArray(a);
    }

    @Override
    public boolean add(final T value) {
        isSorted = false;
        return list.add(value);
    }

    @Override
    public boolean remove(final Object o) {
        return list.remove(o);
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        isSorted = false;
        return list.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> c) {
        isSorted = false;
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(final int index) {
        sort();
        return list.get(index);
    }

    @Override
    public T set(final int index, final T element) {
        sort();
        return list.set(index, element);
    }

    @Override
    public void add(final int index, final T element) {
        isSorted = false;
        list.add(index, element);
    }

    @Override
    public T remove(final int index) {
        sort();
        return list.remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        sort();
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        sort();
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        sort();
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        sort();
        return list.listIterator();
    }

    @Override
    public List<T> subList(final int fromIndex, final int toIndex) {
        sort();
        return list.subList(fromIndex, toIndex);
    }

    private void sort() {
        if (!isSorted) {
            Collections.sort(list);
            isSorted = true;
        }
    }
}
