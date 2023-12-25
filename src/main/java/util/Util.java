package util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    public static boolean divisible(long numerator, long denominator) {
        return numerator % denominator == 0;
    }

    public static List<String> splitByCommaIgnoringSquareBrackets(String string) {
        var substring = new StringBuilder();
        List<String> list = new ArrayList<>();
        int level = 0;

        for (char c : string.toCharArray()) {
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

        if (level != 0) throw new IllegalArgumentException("The passed list is unbalanced!");

        return list;
    }

    public static int toInt(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean areAllTrue(Collection<Boolean> values) {
        for (boolean value : values) {
            if (value == false)
                return false;
        }
        return true;
    }

    public static <T> List<T> initializeAndPopulateList(int listSize, T initValue) {
        List<T> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(initValue);
        }
        return list;
    }

    public static Set<Integer> setFromRange(int startInclusive, int endInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive).boxed().collect(Collectors.toSet());
    }

    public static Set<Integer> setFromUnorderedRangeEnds(int end1Inclusive, int end2Inclusive) {
        if (end1Inclusive < end2Inclusive) {
            return setFromRange(end1Inclusive, end2Inclusive);
        } else {
            return setFromRange(end2Inclusive, end1Inclusive);
        }
    }

    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        var intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection;
    }

}
