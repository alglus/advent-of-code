package aoc2022;

import util.Util;

import java.util.List;

public class Day04 {

    /* --- Part One --- */
    public static int campCleanUpPart1(List<String> input) {
        int fullyOverlappingPairs = 0;

        for (String pair : input) {
            String[] sections = pair.split(",");

            Range range1 = extractRangeFrom(sections[0]);
            Range range2 = extractRangeFrom(sections[1]);

            if (sectionsFullyOverlap(range1, range2)) {
                fullyOverlappingPairs++;
            }
        }

        return fullyOverlappingPairs;
    }


    /* --- Part Two --- */
    public static int campCleanUpPart2(List<String> input) {
        int overlappingPairs = 0;

        for (String pair : input) {
            String[] sections = pair.split(",");

            Range range1 = extractRangeFrom(sections[0]);
            Range range2 = extractRangeFrom(sections[1]);

            if (sectionsOverlap(range1, range2)) {
                overlappingPairs++;
            }
        }

        return overlappingPairs;
    }


    private static Range extractRangeFrom(String section) {
        String[] rangeLimits = section.split("-");

        return new Range(
                Integer.parseInt(rangeLimits[0]),
                Integer.parseInt(rangeLimits[1])
        );
    }

    private static boolean sectionsFullyOverlap(Range range1, Range range2) {
        return range1.inside(range2) || range2.inside(range1);
    }

    private static boolean sectionsOverlap(Range range1, Range range2) {
        return range1.overlaps(range2) || range2.overlaps(range1);
    }

    private record Range(int startIncl, int endIncl) {

        public boolean inside(Range other) {
            return this.startIncl >= other.startIncl && this.endIncl <= other.endIncl;
        }

        public boolean overlaps(Range other) {
            return this.contains(other.startIncl) || this.contains(other.endIncl);
        }

        private boolean contains(int point) {
            return point >= startIncl && point <= endIncl;
        }
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_04.txt");

        System.out.println("Part 1: " + campCleanUpPart1(input));
        System.out.println("Part 2: " + campCleanUpPart2(input));
    }
}
