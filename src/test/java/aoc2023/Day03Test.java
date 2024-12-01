package aoc2023;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

public class Day03Test {

    @Test
    public void micro_test_part1() {
        Day03 day03 = spy(Day03.class);
        Mockito.when(day03.getInputLines()).thenReturn(List.of("42"));

        assertEquals("0", day03.solvePart1());
    }

    @Test
    public void small_test_part1() {
        Day03 day03 = spy(Day03.class);
        Mockito.when(day03.getInputLines()).thenReturn(List.of(".42."));

        assertEquals("0", day03.solvePart1());
    }

    @Test
    public void another_small_test_part1() {
        Day03 day03 = spy(Day03.class);
        Mockito.when(day03.getInputLines()).thenReturn(List.of(".@42", "...."));

        assertEquals("42", day03.solvePart1());
    }

    @Test
    public void yet_another_small_test_part1() {
        Day03 day03 = spy(Day03.class);
        Mockito.when(day03.getInputLines()).thenReturn(List.of(".42..1", "@..2..", ".3..4."));
        //.42..1
        //@..2..
        //.3..4.
        assertEquals("45", day03.solvePart1());
    }

    @Test
    public void aoc_example_part1() {
        assertEquals("4361", new Day03().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("467835", new Day03().asTest().solvePart2());
    }

}
