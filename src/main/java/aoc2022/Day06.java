package aoc2022;

import java.util.ArrayList;
import java.util.Set;

public class Day06 extends Puzzle2022 {

    public Day06() {
        super(6);
    }

    public static void main(String[] args) {
        new Day06().printSolutions();
    }

    @Override
    public String solvePart1() {
        StartOfPacketDetectingList<Character> datastream = new StartOfPacketDetectingList<>();

        for (Character c : getInputLine().toCharArray()) {
            datastream.add(c);

            if (datastream.lastElementsAllDifferent(4)) {
                return String.valueOf(datastream.size());
            }
        }

        return null;
    }

    @Override
    public String solvePart2() {
        StartOfPacketDetectingList<Character> datastream = new StartOfPacketDetectingList<>();

        for (Character c : getInputLine().toCharArray()) {
            datastream.add(c);

            if (datastream.lastElementsAllDifferent(14)) {
                return String.valueOf(datastream.size());
            }
        }

        return null;
    }

    private static class StartOfPacketDetectingList<E> extends ArrayList<E> {

        boolean lastElementsAllDifferent(int numberOfLastElements) {

            if (this.size() < numberOfLastElements)
                return false;

            Set<E> setOfLastElements = Set.copyOf(
                    this.subList(this.size() - numberOfLastElements, this.size())
            );

            return setOfLastElements.size() == numberOfLastElements;
        }
    }
}
