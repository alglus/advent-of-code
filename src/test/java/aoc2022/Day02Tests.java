package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Tests {

    private final int SCORE_WIN = 6;
    private final int SCORE_DRAW = 3;
    private final int SCORE_LOSS = 0;

    private final int SCORE_ROCK = 1;
    private final int SCORE_PAPER = 2;
    private final int SCORE_SCISSORS = 3;

    @Test
    public void score_in_empty_game_is_zero() {
        List<String> input = Collections.emptyList();
        long scorePart1 = Day02.rockPaperScissorsPart1(input);
        long scorePart2 = Day02.rockPaperScissorsPart1(input);

        assertEquals(0, scorePart1);
        assertEquals(0, scorePart2);
    }

    @Test
    public void part1_for_my_shape_rock() {
        List<String> inputWin = List.of("C X");
        List<String> inputDraw = List.of("A X");
        List<String> inputLoss = List.of("B X");

        assertEquals(SCORE_ROCK + SCORE_WIN, Day02.rockPaperScissorsPart1(inputWin));
        assertEquals(SCORE_ROCK + SCORE_DRAW, Day02.rockPaperScissorsPart1(inputDraw));
        assertEquals(SCORE_ROCK + SCORE_LOSS, Day02.rockPaperScissorsPart1(inputLoss));
    }

    @Test
    public void part1_for_my_shape_paper() {
        List<String> inputWin = List.of("A Y");
        List<String> inputDraw = List.of("B Y");
        List<String> inputLoss = List.of("C Y");

        assertEquals(SCORE_PAPER + SCORE_WIN, Day02.rockPaperScissorsPart1(inputWin));
        assertEquals(SCORE_PAPER + SCORE_DRAW, Day02.rockPaperScissorsPart1(inputDraw));
        assertEquals(SCORE_PAPER + SCORE_LOSS, Day02.rockPaperScissorsPart1(inputLoss));
    }

    @Test
    public void part1_for_my_shape_scissors() {
        List<String> inputWin = List.of("B Z");
        List<String> inputDraw = List.of("C Z");
        List<String> inputLoss = List.of("A Z");

        assertEquals(SCORE_SCISSORS + SCORE_WIN, Day02.rockPaperScissorsPart1(inputWin));
        assertEquals(SCORE_SCISSORS + SCORE_DRAW, Day02.rockPaperScissorsPart1(inputDraw));
        assertEquals(SCORE_SCISSORS + SCORE_LOSS, Day02.rockPaperScissorsPart1(inputLoss));
    }

    @Test
    public void part2_for_result_win() {
        List<String> inputRock = List.of("C Z");
        List<String> inputPaper = List.of("A Z");
        List<String> inputScissors = List.of("B Z");

        assertEquals(SCORE_WIN + SCORE_ROCK, Day02.rockPaperScissorsPart2(inputRock));
        assertEquals(SCORE_WIN + SCORE_PAPER, Day02.rockPaperScissorsPart2(inputPaper));
        assertEquals(SCORE_WIN + SCORE_SCISSORS, Day02.rockPaperScissorsPart2(inputScissors));
    }

    @Test
    public void part2_for_result_draw() {
        List<String> inputRock = List.of("A Y");
        List<String> inputPaper = List.of("B Y");
        List<String> inputScissors = List.of("C Y");

        assertEquals(SCORE_DRAW + SCORE_ROCK, Day02.rockPaperScissorsPart2(inputRock));
        assertEquals(SCORE_DRAW + SCORE_PAPER, Day02.rockPaperScissorsPart2(inputPaper));
        assertEquals(SCORE_DRAW + SCORE_SCISSORS, Day02.rockPaperScissorsPart2(inputScissors));
    }

    @Test
    public void part2_for_result_loss() {
        List<String> inputRock = List.of("B X");
        List<String> inputPaper = List.of("C X");
        List<String> inputScissors = List.of("A X");

        assertEquals(SCORE_LOSS + SCORE_ROCK, Day02.rockPaperScissorsPart2(inputRock));
        assertEquals(SCORE_LOSS + SCORE_PAPER, Day02.rockPaperScissorsPart2(inputPaper));
        assertEquals(SCORE_LOSS + SCORE_SCISSORS, Day02.rockPaperScissorsPart2(inputScissors));
    }

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_02.txt");
        long score = Day02.rockPaperScissorsPart1(input);

        assertEquals(15, score);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_02.txt");
        long score = Day02.rockPaperScissorsPart2(input);

        assertEquals(12, score);
    }
}
