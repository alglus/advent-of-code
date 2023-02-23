package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Util {

    private static final String PATH_TO_PUZZLE_FILES = "src/main/resources/";
    private static final String PATH_TO_TEST_FILES = "src/test/resources/";


    public static List<String> getLinesFromPuzzleFile(String fileName) {
        return getLinesFromFile(PATH_TO_PUZZLE_FILES + fileName);
    }

    public static List<String> getLinesFromTestFile(String fileName) {
        return getLinesFromFile(PATH_TO_TEST_FILES + fileName);
    }

    private static List<String> getLinesFromFile(String path) {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error reading file at path: " + path);
        }

        return lines;
    }

    public static int[][] convertNumbersListIntoMatrix(List<String> input) {

        if (input == null || input.isEmpty() || input.get(0) == null)
            throw new IllegalArgumentException("The input is empty");


        int[][] matrix = new int[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {

            String line = input.get(i);

            for (int j = 0; j < line.length(); j++) {

                int number = Integer.parseInt(line.substring(j, j + 1));
                matrix[i][j] = number;
            }
        }

        return matrix;
    }

    public static void print(List<String> list) {
        list.forEach(System.out::println);
    }
}
