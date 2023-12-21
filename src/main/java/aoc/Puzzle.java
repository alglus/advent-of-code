package aoc;

import lombok.Getter;
import util.Input;

import java.util.List;

public abstract class Puzzle {
    @Getter
    private final int year;
    @Getter
    private final int day;
    private boolean isTest = false;
    private String inputFileSuffix = "";
    private String inputPath = null;

    public Puzzle(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public Puzzle asTest() {
        this.isTest = true;
        return this;
    }

    public Puzzle withInputFileSuffix(String inputFileSuffix) {
        this.inputFileSuffix = inputFileSuffix;
        return this;
    }

    public abstract String solvePart1();

    public abstract String solvePart2();

    public String getInputLine() {
        return Input.getFirstLineFromFile(getInputPath());
    }

    public List<String> getInputLines() {
        return Input.getLinesFromFile(getInputPath());
    }

    public Character[][] getInputAsCharMatrix() {
        return Input.convertCharInputIntoMatrix(getInputLines());
    }

    public void printSolutions() {
        printPart1();
        printPart2();
    }

    protected void printPart1() {
        System.out.printf("aoc%d.day%02d.part1: %s\n", year, day, solvePart1());
    }

    protected void printPart2() {
        System.out.printf("aoc%d.day%02d.part2: %s\n", year, day, solvePart2());
    }

    private String getInputPath() {
        if (inputPath == null) {
            inputPath = String.format("src/%s/resources/aoc%d/input/day_%02d%s.txt",
                    isTest ? "test" : "main",
                    year, day, inputFileSuffix);
        }
        return inputPath;
    }

}
