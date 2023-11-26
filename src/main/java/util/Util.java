package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static boolean divisible(long numerator, long denominator) {
        return numerator % denominator == 0;
    }

    public static String getLastCharacter(String string) {
        return string.substring(string.length() - 1);
    }

    public static String concatenate(String delimiter, int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(delimiter));
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

}
