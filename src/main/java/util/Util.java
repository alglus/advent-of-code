package util;

import lombok.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Util {

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


    @SuppressWarnings("unchecked")
    public static <T> T[] copyOfArrayExcluding(@NonNull final T[] array, final int excludedIndex,
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
