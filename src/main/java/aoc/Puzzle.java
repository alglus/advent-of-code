package aoc;

import util.Input;

import java.util.List;

public abstract class Puzzle {
    private final int year;
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

    protected void printSolutions() {
        System.out.println("Part 1: " + solvePart1());
        System.out.println("Part 2: " + solvePart2());
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
