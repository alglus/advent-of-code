package aoc2022;

public class Day04 extends Puzzle2022 {

    public Day04() {
        super(4);
    }

    public static void main(String[] args) {
        new Day04().printSolutions();
    }

    private Range extractRangeFrom(String section) {
        var rangeLimits = section.split("-");

        return new Range(
                Integer.parseInt(rangeLimits[0]),
                Integer.parseInt(rangeLimits[1])
        );
    }

    private boolean sectionsFullyOverlap(Range range1, Range range2) {
        return range1.inside(range2) || range2.inside(range1);
    }

    private boolean sectionsOverlap(Range range1, Range range2) {
        return range1.overlaps(range2) || range2.overlaps(range1);
    }

    @Override
    public String solvePart1() {
        int fullyOverlappingPairs = 0;

        for (String pair : getInputLines()) {
            String[] sections = pair.split(",");

            Range range1 = extractRangeFrom(sections[0]);
            Range range2 = extractRangeFrom(sections[1]);

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

            Range range1 = extractRangeFrom(sections[0]);
            Range range2 = extractRangeFrom(sections[1]);

            if (sectionsOverlap(range1, range2)) {
                overlappingPairs++;
            }
        }

        return String.valueOf(overlappingPairs);
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
}
