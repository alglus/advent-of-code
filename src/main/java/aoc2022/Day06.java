package aoc2022;

import util.Util;

import java.util.*;

public class Day06 {

    /* --- Part One --- */
    public static int tuningTroublePart1(String input) {
        StartOfPacketDetectingList<Character> datastream = new StartOfPacketDetectingList<>();

        for (Character c : input.toCharArray()) {
            datastream.add(c);

            if (datastream.lastElementsAllDifferent(4)) {
                return datastream.size();
            }
        }

        return -1;
    }


    /* --- Part Two --- */
    public static int tuningTroublePart2(String input) {
        StartOfPacketDetectingList<Character> datastream = new StartOfPacketDetectingList<>();

        for (Character c : input.toCharArray()) {
            datastream.add(c);

            if (datastream.lastElementsAllDifferent(14)) {
                return datastream.size();
            }
        }

        return -1;
    }


    static class StartOfPacketDetectingList<E> extends ArrayList<E> {

        boolean lastElementsAllDifferent(int numberOfLastElements) {

            if (this.size() < numberOfLastElements)
                return false;

            Set<E> setOfLastElements = Set.copyOf(
                    this.subList(this.size() - numberOfLastElements, this.size())
            );

            return setOfLastElements.size() == numberOfLastElements;
        }
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_06.txt");
        String dataStream = input.get(0);

        System.out.println("Part 1: " + tuningTroublePart1(dataStream));
        System.out.println("Part 2: " + tuningTroublePart2(dataStream));
    }
}
