package aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class Day02Tests {

    private final int SCORE_WIN = 6;
    private final int SCORE_DRAW = 3;
    private final int SCORE_LOSS = 0;

    private final int SCORE_ROCK = 1;
    private final int SCORE_PAPER = 2;
    private final int SCORE_SCISSORS = 3;

    @Test
    public void score_in_empty_game_is_zero() {
        var day02 = spy(Day02.class);
        when(day02.getInputLines()).thenReturn(Collections.emptyList());

        assertEquals("0", day02.solvePart1());
    }

    @Test
    public void part1_for_my_shape_rock() {
        var day02 = spy(Day02.class);

        var inputWin = List.of("C X");
        var inputDraw = List.of("A X");
        var inputLoss = List.of("B X");

        when(day02.getInputLines()).thenReturn(inputWin);
        assertEquals(String.valueOf(SCORE_ROCK + SCORE_WIN), day02.solvePart1());

        when(day02.getInputLines()).thenReturn(inputDraw);
        assertEquals(String.valueOf(SCORE_ROCK + SCORE_DRAW), day02.solvePart1());

        when(day02.getInputLines()).thenReturn(inputLoss);
        assertEquals(String.valueOf(SCORE_ROCK + SCORE_LOSS), day02.solvePart1());
    }

    @Test
    public void part1_for_my_shape_paper() {
        var day02 = spy(Day02.class);

        var inputWin = List.of("A Y");
        var inputDraw = List.of("B Y");
        var inputLoss = List.of("C Y");

        when(day02.getInputLines()).thenReturn(inputWin);
        assertEquals(String.valueOf(SCORE_PAPER + SCORE_WIN), day02.solvePart1());

        when(day02.getInputLines()).thenReturn(inputDraw);
        assertEquals(String.valueOf(SCORE_PAPER + SCORE_DRAW), day02.solvePart1());

        when(day02.getInputLines()).thenReturn(inputLoss);
        assertEquals(String.valueOf(SCORE_PAPER + SCORE_LOSS), day02.solvePart1());
    }

    @Test
    public void part1_for_my_shape_scissors() {
        var day02 = spy(Day02.class);

        var inputWin = List.of("B Z");
        var inputDraw = List.of("C Z");
        var inputLoss = List.of("A Z");

        when(day02.getInputLines()).thenReturn(inputWin);
        assertEquals(String.valueOf(SCORE_SCISSORS + SCORE_WIN), day02.solvePart1());

        when(day02.getInputLines()).thenReturn(inputDraw);
        assertEquals(String.valueOf(SCORE_SCISSORS + SCORE_DRAW), day02.solvePart1());

        when(day02.getInputLines()).thenReturn(inputLoss);
        assertEquals(String.valueOf(SCORE_SCISSORS + SCORE_LOSS), day02.solvePart1());
    }

    @Test
    public void part2_for_result_win() {
        var day02 = spy(Day02.class);

        var inputRock = List.of("C Z");
        var inputPaper = List.of("A Z");
        var inputScissors = List.of("B Z");

        when(day02.getInputLines()).thenReturn(inputRock);
        assertEquals(String.valueOf(SCORE_WIN + SCORE_ROCK), day02.solvePart2());

        when(day02.getInputLines()).thenReturn(inputPaper);
        assertEquals(String.valueOf(SCORE_WIN + SCORE_PAPER), day02.solvePart2());

        when(day02.getInputLines()).thenReturn(inputScissors);
        assertEquals(String.valueOf(SCORE_WIN + SCORE_SCISSORS), day02.solvePart2());
    }

    @Test
    public void part2_for_result_draw() {
        var day02 = spy(Day02.class);

        var inputRock = List.of("A Y");
        var inputPaper = List.of("B Y");
        var inputScissors = List.of("C Y");

        when(day02.getInputLines()).thenReturn(inputRock);
        assertEquals(String.valueOf(SCORE_DRAW + SCORE_ROCK), day02.solvePart2());

        when(day02.getInputLines()).thenReturn(inputPaper);
        assertEquals(String.valueOf(SCORE_DRAW + SCORE_PAPER), day02.solvePart2());

        when(day02.getInputLines()).thenReturn(inputScissors);
        assertEquals(String.valueOf(SCORE_DRAW + SCORE_SCISSORS), day02.solvePart2());
    }

    @Test
    public void part2_for_result_loss() {
        var day02 = spy(Day02.class);

        var inputRock = List.of("B X");
        var inputPaper = List.of("C X");
        var inputScissors = List.of("A X");

        when(day02.getInputLines()).thenReturn(inputRock);
        assertEquals(String.valueOf(SCORE_LOSS + SCORE_ROCK), day02.solvePart2());

        when(day02.getInputLines()).thenReturn(inputPaper);
        assertEquals(String.valueOf(SCORE_LOSS + SCORE_PAPER), day02.solvePart2());

        when(day02.getInputLines()).thenReturn(inputScissors);
        assertEquals(String.valueOf(SCORE_LOSS + SCORE_SCISSORS), day02.solvePart2());
    }

    @Test
    public void aoc_example_part1() {
        assertEquals("15", new Day02().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("12", new Day02().asTest().solvePart2());
    }
}
