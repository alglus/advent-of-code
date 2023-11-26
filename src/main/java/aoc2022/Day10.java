package aoc2022;

import lombok.Getter;
import util.Range;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Puzzle2022 {

    public Day10() {
        super(10);
    }

    public static void main(String[] args) {
        var day10 = new Day10();
        System.out.println("Part 1: " + day10.solvePart1());
        System.out.println("Part 2:\n" + day10.solvePart2());
    }

    private Instruction convertToInstruction(String input) {
        if (input.equals("noop")) {
            return new Instruction(Type.NOOP, null, 0);

        } else {
            Matcher regex = Pattern.compile("add(\\w) (-?\\d+)").matcher(input);

            if (!regex.matches()) {
                throw new IllegalArgumentException("There seems to be an error in the input.");
            }

            String registerName = regex.group(1);
            int value = Integer.parseInt(regex.group(2));

            return new Instruction(Type.ADD, registerName, value);
        }
    }

    private Range getSpritePixelsRange(int spritePosition) {
        return new Range(spritePosition - 1, spritePosition + 1);
    }

    private List<StringBuilder> initializeImage(int imageHeight) {
        List<StringBuilder> image = new ArrayList<>(imageHeight);
        for (int i = 0; i < imageHeight; i++) {
            image.add(new StringBuilder());
        }
        return image;
    }

    @Override
    public String solvePart1() {
        Deque<Integer> importantCycles = new ArrayDeque<>(List.of(20, 60, 100, 140, 180, 220));
        int nextImportantCycle = importantCycles.removeFirst();

        int cycle = 0;
        int signalStrengthSum = 0;
        int registerValue = 1;

        for (String inputLine : getInputLines()) {
            var instruction = convertToInstruction(inputLine);

            cycle += instruction.type().getCycles();

            if (cycle >= nextImportantCycle) {
                signalStrengthSum += nextImportantCycle * registerValue;

                if (importantCycles.isEmpty()) {
                    break;
                }

                nextImportantCycle = importantCycles.removeFirst();
            }

            registerValue += instruction.value();
        }

        return String.valueOf(signalStrengthSum);
    }

    @Override
    public String solvePart2() {
        int crtWidth = 40;
        int crtHeight = 6;

        int cycle = 0;
        int spritePosition = 1;

        var sprite = getSpritePixelsRange(spritePosition);
        var image = initializeImage(crtHeight);

        for (String inputLine : getInputLines()) {
            var instruction = convertToInstruction(inputLine);

            for (int i = 0; i < instruction.type().getCycles(); i++) {
                int imageRow = cycle / crtWidth;
                int currentPixel = cycle % crtWidth;

                if (sprite.contains(currentPixel)) {
                    image.get(imageRow).append("#");
                } else {
                    image.get(imageRow).append(".");
                }

                cycle++;
            }

            spritePosition += instruction.value();
            sprite = getSpritePixelsRange(spritePosition);
        }

        return String.join("\n", image.stream().map(StringBuilder::toString).toList());
    }

    @Getter
    private enum Type {
        ADD(2), NOOP(1);

        private final int cycles;

        Type(int cycles) {
            this.cycles = cycles;
        }
    }

    private record Instruction(Type type, String register, int value) {
    }
}
