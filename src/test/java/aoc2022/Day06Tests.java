package aoc2022;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.Input;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class Day06Tests {

    private static Stream<Arguments> getTestDatastreamsPart1() {
        var input = Input.getLinesFromFile("src/test/resources/aoc2022/input/day_06.txt");

        return Stream.of(
                Arguments.of(input.get(0), "7"),
                Arguments.of(input.get(1), "5"),
                Arguments.of(input.get(2), "6"),
                Arguments.of(input.get(3), "10"),
                Arguments.of(input.get(4), "11")
        );
    }

    private static Stream<Arguments> getTestDatastreamsPart2() {
        var input = Input.getLinesFromFile("src/test/resources/aoc2022/input/day_06.txt");

        return Stream.of(
                Arguments.of(input.get(0), "19"),
                Arguments.of(input.get(1), "23"),
                Arguments.of(input.get(2), "23"),
                Arguments.of(input.get(3), "29"),
                Arguments.of(input.get(4), "26")
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDatastreamsPart1")
    public void aoc_example_part1(String input, String expectedPosition) {
        var day06 = spy(Day06.class);
        when(day06.getInputLine()).thenReturn(input);

        assertEquals(expectedPosition, day06.solvePart1());
    }

    @ParameterizedTest
    @MethodSource("getTestDatastreamsPart2")
    public void aoc_example_part2(String input, String expectedPosition) {
        var day06 = spy(Day06.class);
        when(day06.getInputLine()).thenReturn(input);

        assertEquals(expectedPosition, day06.solvePart2());
    }
}
