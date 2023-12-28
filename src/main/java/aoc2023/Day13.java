package aoc2023;

import lombok.AllArgsConstructor;
import lombok.Getter;
import util.StringUtil;

import java.util.List;
import java.util.function.Function;

public class Day13 extends Puzzle2023 {

    public Day13() {
        super(13);
    }

    public static void main(String[] args) {
        new Day13().printSolutions();
    }

    private int getNotesSummary(List<List<String>> patterns, Function<List<String>, Reflection> convertToReflection) {
        return patterns.stream()
                .map(convertToReflection)
                .mapToInt(Reflection::getSummary)
                .sum();
    }

    private Reflection convertToReflection(List<String> pattern) {
        //0: #...##..#
        //1: #....#..#       ↱ i=3,j=2 -> (i-j)=1
        //2: ..##..###    ↱ i=3,j=1 -> (i-j)=2
        //3: #####.##. i=3,j=0 -> (i-j)=3
        //4: #####.##. i=3,j=0 -> (i+1+j)=4
        //5: ..##..###    ↳ i=3,j=1 -> (i+1+j)=5
        //6: #....#..#       ↳ i=3,j=2 -> (i+1+j)=6
        for (int i = 0; i < pattern.size() - 1; i++) {
            var isHorizontal = true;

            for (int j = 0; j <= Math.min(i, pattern.size() - 1 - (i + 1)); j++) {
                if (!pattern.get(i - j).equals(pattern.get(i + 1 + j))) {
                    isHorizontal = false;
                    break;
                }
            }
            if (isHorizontal) {
                return new HorizontalReflection(i + 1);
            }
        }

        for (int i = 0; i < pattern.get(0).length() - 1; i++) {
            var isVertical = true;

            for (int j = 0; j <= Math.min(i, pattern.get(0).length() - 1 - (i + 1)); j++) {
                for (String s : pattern) {
                    if (s.charAt(i - j) != (s.charAt(i + 1 + j))) {
                        isVertical = false;
                        break;
                    }
                }
            }
            if (isVertical) {
                return new VerticalReflection(i + 1);
            }
        }

        throw new IllegalStateException("No Replection found!");
    }


    private Reflection fixSmudgeAndConvertToReflection(List<String> pattern) {
        for (int i = 0; i < pattern.size() - 1; i++) {
            var isHorizontal = true;
            var smudgeFound = false;

            for (int j = 0; j <= Math.min(i, pattern.size() - 1 - (i + 1)); j++) {
                var rowDiffs = StringUtil.findDiffIndexes(pattern.get(i - j), pattern.get(i + 1 + j));

                if (!smudgeFound) {
                    if (rowDiffs.size() == 1) {
                        // We let the first two rows with just one smudge count as being a mirror of each other,
                        // without worrying, whether character '.' or '#' needs to be replaced.
                        smudgeFound = true;
                    }
                    if (rowDiffs.size() > 1) {
                        isHorizontal = false;
                        break;
                    }
                } else {
                    if (!rowDiffs.isEmpty()) {
                        isHorizontal = false;
                        break;
                    }
                }
            }

            // We need to make sure, that a smudge has been found, otherwise we could be counting the reflection line
            // from part1, which is present even without fixing the pattern.
            if (isHorizontal && smudgeFound) {
                return new HorizontalReflection(i + 1);
            }
        }

        for (int i = 0; i < pattern.get(0).length() - 1; i++) {
            var isVertical = true;
            var smudgeFound = false;

            for (int j = 0; j <= Math.min(i, pattern.get(0).length() - 1 - (i + 1)); j++) {
                int columnDiffs = 0;
                for (String s : pattern) {
                    if (s.charAt(i - j) != (s.charAt(i + 1 + j))) {
                        columnDiffs++;
                    }
                }
                if (!smudgeFound) {
                    if (columnDiffs == 1) {
                        smudgeFound = true;
                    }
                    if (columnDiffs > 1) {
                        isVertical = false;
                        break;
                    }
                } else {
                    if (columnDiffs > 0) {
                        isVertical = false;
                        break;
                    }
                }
            }
            if (isVertical && smudgeFound) {
                return new VerticalReflection(i + 1);
            }
        }

        throw new IllegalStateException("No Replection found!");
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getNotesSummary(getInputLinesSplitByBlankLine(), this::convertToReflection));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getNotesSummary(getInputLinesSplitByBlankLine(), this::fixSmudgeAndConvertToReflection));
    }

    @Getter
    @AllArgsConstructor
    private abstract static class Reflection {
        private int numberOfRowsOrColsBeforeReflectionLine;

        public abstract int getSummary();
    }


    private static class VerticalReflection extends Reflection {
        public VerticalReflection(int numberOfRowsOrColsBeforeReflectionLine) {
            super(numberOfRowsOrColsBeforeReflectionLine);
        }

        @Override
        public int getSummary() {
            return getNumberOfRowsOrColsBeforeReflectionLine();
        }
    }

    private static class HorizontalReflection extends Reflection {
        public HorizontalReflection(int numberOfRowsOrColsBeforeReflectionLine) {
            super(numberOfRowsOrColsBeforeReflectionLine);
        }

        @Override
        public int getSummary() {
            return 100 * getNumberOfRowsOrColsBeforeReflectionLine();
        }
    }
}
