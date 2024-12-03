package aoc2023;

import one.util.streamex.EntryStream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static util.StringUtil.getLastCharacter;

public class Day15 extends Puzzle2023 {

    public Day15() {
        super(15);
    }

    public static void main(final String[] args) {
        new Day15().printSolutions();
    }

    private int getSumOfHashesOfInitializationSequence(final List<String> steps) {
        return steps.stream()
                .mapToInt(this::calculateHash)
                .sum();
    }

    private int getFocusingPower(final List<String> steps) {
        final var boxes = createBoxes();

        steps.stream()
                .map(this::convertToOperation)
                .forEach(op -> performOperation(op, boxes));

        return calculateFocusingPower(boxes);
    }

    private List<LinkedHashMap<String, Integer>> createBoxes() {
        final List<LinkedHashMap<String, Integer>> boxes = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            boxes.add(new LinkedHashMap<>());
        }
        return boxes;
    }

    private Operation convertToOperation(final String instruction) {
        if (getLastCharacter(instruction).equals("-")) {
            return new Operation(OperationType.REMOVE, instruction.substring(0, instruction.length() - 1), -1);
        } else {
            final var instructionParts = instruction.split("=");
            return new Operation(OperationType.PUT, instructionParts[0], Integer.parseInt(instructionParts[1]));
        }
    }

    private void performOperation(final Operation operation, final List<LinkedHashMap<String, Integer>> boxes) {
        final var hash = calculateHash(operation.label());
        switch (operation.type) {
            case REMOVE -> boxes.get(hash).remove(operation.label());
            case PUT -> boxes.get(hash).put(operation.label(), operation.focalLength());
        }
    }

    private int calculateFocusingPower(final List<LinkedHashMap<String, Integer>> boxes) {
        return EntryStream.of(boxes)
                .mapValues(lenses -> EntryStream.of(lenses.values().stream().toList())
                        .mapKeyValue((slotIndex, focalLength) -> (slotIndex + 1) * focalLength)
                        .mapToInt(i -> i)
                        .sum())
                .mapKeyValue((boxIndex, focusingPower) -> (boxIndex + 1) * focusingPower)
                .mapToInt(i -> i)
                .sum();
    }

    private int calculateHash(final String string) {
        int hashValue = 0;
        for (int i = 0; i < string.length(); i++) {
            final int asciiCode = string.charAt(i);
            hashValue += asciiCode;
            hashValue *= 17;
            hashValue %= 256;
        }
        return hashValue;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getSumOfHashesOfInitializationSequence(getCsvFromFirstInputLine()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getFocusingPower(getCsvFromFirstInputLine()));
    }

    private enum OperationType {REMOVE, PUT}

    private record Operation(OperationType type, String label, int focalLength) {
    }
}
