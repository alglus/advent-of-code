package aoc2022;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.Util;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Tests {

    private static Stream<Arguments> getTestDatastreamsPart1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_06.txt");

        return Stream.of(
                Arguments.of(input.get(0), 7),
                Arguments.of(input.get(1), 5),
                Arguments.of(input.get(2), 6),
                Arguments.of(input.get(3), 10),
                Arguments.of(input.get(4), 11)
        );
    }

    private static Stream<Arguments> getTestDatastreamsPart2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_06.txt");

        return Stream.of(
                Arguments.of(input.get(0), 19),
                Arguments.of(input.get(1), 23),
                Arguments.of(input.get(2), 23),
                Arguments.of(input.get(3), 29),
                Arguments.of(input.get(4), 26)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDatastreamsPart1")
    public void aoc_example_part1(String input, int expectedPosition) {

        int firstMarkerPosition = Day06.tuningTroublePart1(input);

        assertEquals(expectedPosition, firstMarkerPosition);
    }

    @ParameterizedTest
    @MethodSource("getTestDatastreamsPart2")
    public void aoc_example_part2(String input, int expectedPosition) {

        int firstMarkerPosition = Day06.tuningTroublePart2(input);

        assertEquals(expectedPosition, firstMarkerPosition);
    }
}
