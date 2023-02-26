package aoc2022;

import util.Range;
import util.Util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    /* --- Part One --- */
    public static int cathodeRayTubePart1(List<String> input) {

        Deque<Integer> importantCycles = new ArrayDeque<>(List.of(20, 60, 100, 140, 180, 220));
        int nextImportantCycle = importantCycles.removeFirst();

        int cycle = 0;
        int signalStrengthSum = 0;
        int registerValue = 1;

        for (String inputLine : input) {
            Instruction instruction = convertToInstruction(inputLine);

            cycle += instruction.type().getCycles();

            if (cycle >= nextImportantCycle) {
                signalStrengthSum += nextImportantCycle * registerValue;

                if (importantCycles.isEmpty())
                    break;

                nextImportantCycle = importantCycles.removeFirst();
            }

            registerValue += instruction.value();
        }

        return signalStrengthSum;
    }


    /* --- Part Two --- */
    public static List<String> cathodeRayTubePart2(List<String> input) {

        int crtWidth = 40;
        int crtHeight = 6;

        int cycle = 0;
        int spritePosition = 1;

        Range sprite = getSpritePixelsRange(spritePosition);
        List<StringBuilder> image = initializeImage(crtHeight);

        for (String inputLine : input) {
            Instruction instruction = convertToInstruction(inputLine);

            for (int i = 0; i < instruction.type().getCycles(); i++) {
                int imageRow = cycle / crtWidth;
                int currentPixel = cycle % crtWidth;

                if (sprite.spans(currentPixel)) {
                    image.get(imageRow).append("#");
                } else {
                    image.get(imageRow).append(".");
                }

                cycle++;
            }

            spritePosition += instruction.value();
            sprite = getSpritePixelsRange(spritePosition);
        }

        return image.stream().map(StringBuilder::toString).toList();
    }


    private record Instruction(Type type, String register, int value) {
    }

    private static Instruction convertToInstruction(String input) {
        if (input.equals("noop")) {

            return new Instruction(Type.NOOP, null, 0);

        } else {

            Matcher regex = Pattern.compile("add(\\w) (-?\\d+)").matcher(input);

            if (!regex.matches())
                throw new IllegalArgumentException("There seems to be an error in the input.");

            String registerName = regex.group(1);
            int value = Integer.parseInt(regex.group(2));

            return new Instruction(Type.ADD, registerName, value);
        }
    }

    private static Range getSpritePixelsRange(int spritePosition) {
        return new Range(spritePosition - 1, spritePosition + 1);
    }

    private static List<StringBuilder> initializeImage(int imageHeight) {
        List<StringBuilder> image = new ArrayList<>(imageHeight);
        for (int i = 0; i < imageHeight; i++) {
            image.add(new StringBuilder());
        }
        return image;
    }

    private enum Type {
        ADD(2), NOOP(1);

        private final int cycles;

        Type(int cycles) {
            this.cycles = cycles;
        }

        public int getCycles() {
            return cycles;
        }
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_10.txt");

        System.out.println("Part 1: " + Day10.cathodeRayTubePart1(input));

        System.out.println("Part 2:");
        Util.print(Day10.cathodeRayTubePart2(input));
    }
}
