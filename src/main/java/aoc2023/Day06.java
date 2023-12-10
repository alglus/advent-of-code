package aoc2023;

import util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day06 extends Puzzle2023 {

    public Day06() {
        super(6);
    }

    public static void main(String[] args) {
        new Day06().printSolutions();
    }

    private long getMultiplicationOfRecordBeatingWays(List<Long> allowedRaceTimes, List<Long> recordDistances) {
        var numberOfRaces = allowedRaceTimes.size();

        return IntStream.range(0, numberOfRaces)
                .mapToLong(race -> {
                    var maxRaceTime = allowedRaceTimes.get(race);
                    return LongStream.range(0, maxRaceTime + 1)
                            .map(chargingTime -> getTravelledDistance(chargingTime, maxRaceTime))
                            .filter(distance -> distance > recordDistances.get(race))
                            .count();
                })
                .reduce(1, (multiplication, recordBreakingTimes) -> multiplication * recordBreakingTimes);
    }

    private long getTravelledDistance(long chargingTime, long allowedRaceTime) {
        return chargingTime * (allowedRaceTime - chargingTime);
    }

    private List<Long> parseInputAsSeveralRaces(String inputLine) {
        return Arrays.stream(StringUtil.splitByWhitespaces(inputLine))
                .skip(1)
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
    }

    private List<Long> parseInputAsOneRace(String inputLine) {
        var number = Long.parseLong(Arrays.stream(StringUtil.splitByWhitespaces(inputLine))
                .skip(1)
                .reduce("", (joinedNumber, newNumberPart) -> String.join("", joinedNumber, newNumberPart)));

        return List.of(number);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getMultiplicationOfRecordBeatingWays(
                parseInputAsSeveralRaces(getInputLines().get(0)),
                parseInputAsSeveralRaces(getInputLines().get(1))
        ));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getMultiplicationOfRecordBeatingWays(
                parseInputAsOneRace(getInputLines().get(0)),
                parseInputAsOneRace(getInputLines().get(1))
        ));
    }
}
