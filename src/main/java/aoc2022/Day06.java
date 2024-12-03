package aoc2022;

import java.util.ArrayList;
import java.util.Set;

public class Day06 extends Puzzle2022 {

    public Day06() {
        super(6);
    }

    public static void main(final String[] args) {
        new Day06().printSolutions();
    }

    @Override
    public String solvePart1() {
        final StartOfPacketDetectingList<Character> datastream = new StartOfPacketDetectingList<>();

        for (final Character c : getFirstInputLine().toCharArray()) {
            datastream.add(c);

            if (datastream.lastElementsAllDifferent(4)) {
                return String.valueOf(datastream.size());
            }
        }

        return null;
    }

    @Override
    public String solvePart2() {
        final StartOfPacketDetectingList<Character> datastream = new StartOfPacketDetectingList<>();

        for (final Character c : getFirstInputLine().toCharArray()) {
            datastream.add(c);

            if (datastream.lastElementsAllDifferent(14)) {
                return String.valueOf(datastream.size());
            }
        }

        return null;
    }

    private static class StartOfPacketDetectingList<E> extends ArrayList<E> {

        boolean lastElementsAllDifferent(final int numberOfLastElements) {

            if (this.size() < numberOfLastElements) {
                return false;
            }

            final Set<E> setOfLastElements = Set.copyOf(
                    this.subList(this.size() - numberOfLastElements, this.size())
            );

            return setOfLastElements.size() == numberOfLastElements;
        }
    }
}
