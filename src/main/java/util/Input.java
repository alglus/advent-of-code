package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public static char[][] convertCharInputIntoMatrix(List<String> input) {
        if (input == null || input.isEmpty() || input.get(0) == null) {
            throw new IllegalArgumentException("The input is empty");
        }

        var matrix = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            matrix[i] = input.get(i).toCharArray();
        }

        return matrix;
    }

}
