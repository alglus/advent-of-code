package aoc2024;

import util.MathUtil;
import util.StringUtil;
import util.Util;

import java.util.Arrays;
import java.util.List;

public class Day02 extends Puzzle2024 {

    public Day02() {
        super(2);
    }

    public static void main(final String[] args) {
        new Day02().printSolutions();
    }

    private static boolean isReportSafe(final String[] levels) {
        int previousLevel = Integer.parseInt(levels[0]);
        Integer previousDifference = null;

        for (final String level : Arrays.copyOfRange(levels, 1, levels.length)) {
            final int currentLevel = Integer.parseInt(level);
            final int levelsDifference = currentLevel - previousLevel;

            if (previousDifference == null) {
                previousDifference = levelsDifference;
            } else {
                if (MathUtil.isSignDifferent(previousDifference, levelsDifference)) {
                    return false;
                }
            }

            if (Math.abs(levelsDifference) < 1 || Math.abs(levelsDifference) > 3) {
                return false;
            }

            previousLevel = currentLevel;
        }

        return true;
    }

    private static int countSafeReports(final boolean isReportSafe) {
        return isReportSafe ? 1 : 0;
    }

    /**
     * Using a brute-force approach
     */
    private static boolean isReportSafeWithProblemDampener(final String[] levels) {
        if (isReportSafe(levels)) {
            return true;
        }

        for (int levelToExclude = 0; levelToExclude < levels.length; levelToExclude++) {
            final String[] levelsWithoutExcludedLevel = Util.copyOfExcluding(levels, levelToExclude, String.class);

            if (isReportSafe(levelsWithoutExcludedLevel)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countNumberOfSafeReports(getInputLines()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countNumberOfSafeReportsUsingTheProblemDampener(getInputLines()));
    }

    private int countNumberOfSafeReports(final List<String> input) {
        return input.stream()
                .map(StringUtil::splitByWhitespaces)
                .map(Day02::isReportSafe)
                .mapToInt(Day02::countSafeReports)
                .sum();
    }

    private int countNumberOfSafeReportsUsingTheProblemDampener(final List<String> input) {
        return input.stream()
                .map(StringUtil::splitByWhitespaces)
                .map(Day02::isReportSafeWithProblemDampener)
                .mapToInt(Day02::countSafeReports)
                .sum();
    }
}
