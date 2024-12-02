package util;

import lombok.NonNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    public static boolean divisible(final long numerator, final long denominator) {
        return numerator % denominator == 0;
    }

    public static List<String> splitByCommaIgnoringSquareBrackets(final String string) {
        var substring = new StringBuilder();
        final List<String> list = new ArrayList<>();
        int level = 0;

        for (final char c : string.toCharArray()) {
            if (c == ',') {
                if (level == 0) {
                    list.add(substring.toString());
                    substring = new StringBuilder();
                } else {
                    substring.append(c);
                }
            } else {
                substring.append(c);
                if (c == '[') level++;
                if (c == ']') level--;
            }
        }
        list.add(substring.toString());

        if (level != 0) {
            throw new IllegalArgumentException("The passed list is unbalanced!");
        }

        return list;
    }

    public static int toInt(final String string, final int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean areAllTrue(final Collection<Boolean> values) {
        for (final boolean value : values) {
            if (value == false) return false;
        }
        return true;
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

    @SuppressWarnings("unchecked")
    public static <T> T[] copyOfExcluding(@NonNull final T[] array, final int excludedIndex,
                                          @NonNull final Class<T> clazz) {
        if (array.length == 0) {
            return Arrays.copyOf(array, 0);
        }

        if (excludedIndex >= array.length) {
            throw new IllegalArgumentException(String.format(
                    "The excluded index '%d' is out of bounds: [0,%d]", excludedIndex, array.length));
        }

        final T[] newArray = (T[]) Array.newInstance(clazz, Math.max(array.length - 1, 0));

        System.arraycopy(array, 0, newArray, 0, excludedIndex);
        System.arraycopy(array, excludedIndex + 1, newArray, excludedIndex, array.length - excludedIndex - 1);

        return newArray;
    }
}
