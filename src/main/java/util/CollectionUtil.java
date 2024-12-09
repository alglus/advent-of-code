package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionUtil {

    public static <T> List<T> moveElementToIndex(final List<T> list, final int fromIndex, final int toIndex) {
        if (fromIndex < 0 || toIndex < 0 || fromIndex >= list.size() || toIndex >= list.size()) {
            throw new IndexOutOfBoundsException();
        }

        final List<T> newList = new ArrayList<>(list);
        T elementToMove = list.get(fromIndex);

        final boolean moveToFront = fromIndex < toIndex;

        for (int i = toIndex;
             moveToFront ? i >= fromIndex : i <= fromIndex;
             i += (moveToFront ? -1 : 1)
        ) {
            final T replacedElement = list.get(i);
            newList.set(i, elementToMove);
            elementToMove = replacedElement;
        }

        return newList;
    }

    public static <T> List<T> moveElementAfterIndex(final List<T> list, final int fromIndex, final int index) {
        if (fromIndex < 0 || index < 0 || fromIndex >= list.size() || index >= list.size()) {
            throw new IndexOutOfBoundsException();
        }

        final List<T> newList = new ArrayList<>(list);
        T elementToMove = list.get(fromIndex);

        for (int i = index; i <= fromIndex; i++) {
            final T replacedElement = list.get(i);
            newList.set(i, elementToMove);
            elementToMove = replacedElement;
        }

        return newList;
    }

    public static <T> List<T> initializeAndPopulateList(final int listSize, final T initValue) {
        final List<T> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(initValue);
        }
        return list;
    }

    public static Set<Integer> setFromRange(final int startInclusive, final int endInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive).boxed().collect(Collectors.toSet());
    }

    public static Set<Integer> setFromUnorderedRangeEnds(final int end1Inclusive, final int end2Inclusive) {
        if (end1Inclusive < end2Inclusive) {
            return setFromRange(end1Inclusive, end2Inclusive);
        } else {
            return setFromRange(end2Inclusive, end1Inclusive);
        }
    }

    public static <T> Set<T> intersection(final Set<T> set1, final Set<T> set2) {
        final var intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection;
    }
}
