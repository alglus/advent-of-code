package util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {

    public static String getFirstCharacter(String string) {
        return string.substring(0, 1);
    }

    public static String getLastCharacter(String string) {
        return string.substring(string.length() - 1);
    }

    public static String concatenate(String delimiter, int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(delimiter));
    }

    public static String leaveOnlyDigits(String string) {
        return string.replaceAll("\\D+", "");
    }

    public static boolean isDigitAt(String string, int index) {
        var character = string.charAt(index);
        return isDigit(character);
    }

    public static boolean isDigit(char character) {
        return character >= 48 && character <= 57;
    }

    public static String replaceAt(String string, String replacement, int startIndex, int endIndex) {
        return new StringBuilder(string)
                .replace(startIndex, endIndex, replacement)
                .toString();
    }

}
