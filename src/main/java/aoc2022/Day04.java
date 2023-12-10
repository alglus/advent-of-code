package aoc2022;

import util.Range;

public class Day04 extends Puzzle2022 {

    public Day04() {
        super(4);
    }

    public static void main(String[] args) {
        new Day04().printSolutions();
    }

    private Range<Integer> extractRangeFrom(String section) {
        var rangeLimits = section.split("-");

        return new Range<>(
                Integer.parseInt(rangeLimits[0]),
                Integer.parseInt(rangeLimits[1])
        );
    }

    private boolean sectionsFullyOverlap(Range<Integer> range1, Range<Integer> range2) {
        return range1.inside(range2) || range2.inside(range1);
    }

    private boolean sectionsOverlap(Range<Integer> range1, Range<Integer> range2) {
        return range1.overlaps(range2) || range2.overlaps(range1);
    }

    @Override
    public String solvePart1() {
        int fullyOverlappingPairs = 0;

        for (String pair : getInputLines()) {
            String[] sections = pair.split(",");

            var range1 = extractRangeFrom(sections[0]);
            var range2 = extractRangeFrom(sections[1]);

            if (sectionsFullyOverlap(range1, range2)) {
                fullyOverlappingPairs++;
            }
        }

        return String.valueOf(fullyOverlappingPairs);
    }

    @Override
    public String solvePart2() {
        int overlappingPairs = 0;

        for (String pair : getInputLines()) {
            String[] sections = pair.split(",");

            var range1 = extractRangeFrom(sections[0]);
            var range2 = extractRangeFrom(sections[1]);

            if (sectionsOverlap(range1, range2)) {
                overlappingPairs++;
            }
        }

        return String.valueOf(overlappingPairs);
    }

}
