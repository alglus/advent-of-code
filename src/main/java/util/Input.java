package util;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Input {

    public static List<String> getLinesFromFile(String path) {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error reading file at path: " + path);
        }

        return lines;
    }

    public static String getFirstLineFromFile(String path) {
        return getLinesFromFile(path).get(0);
    }

    public static List<String> getCsv(String string) {
        return Arrays.stream(string.split(",")).toList();
    }

    public static List<String> removeEmptyLines(List<String> input) {
        return input.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toCollection(ArrayList::new)); // return mutable list
    }

    public static int[][] convertNumbersInputIntoMatrix(List<String> input) {
        if (input == null || input.isEmpty() || input.get(0) == null) {
            throw new IllegalArgumentException("The input is empty");
        }

        var matrix = new int[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i);

            for (int j = 0; j < line.length(); j++) {
                int number = Integer.parseInt(line.substring(j, j + 1));
                matrix[i][j] = number;
            }
        }

        return matrix;
    }

    public static Character[][] convertCharInputIntoMatrix(List<String> input) {
        if (input == null || input.isEmpty() || input.get(0) == null) {
            throw new IllegalArgumentException("The input is empty");
        }

        var matrix = new Character[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            matrix[i] = ArrayUtils.toObject(input.get(i).toCharArray());
        }

        return matrix;
    }

    public static List<List<String>> splitByBlankLines(List<String> input) {
        List<List<String>> listOfStringLists = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            var string = input.get(i);

            if (string.isBlank() || i == input.size() - 1) {
                listOfStringLists.add(stringList);
                stringList = new ArrayList<>();
            } else {
                stringList.add(string);
            }
        }

        return listOfStringLists;
    }

}
