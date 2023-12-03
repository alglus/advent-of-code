package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

}
