package aoc2022;

import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 {

    /* --- Part One --- */
    public static long rockPaperScissorsPart1(List<String> input) {

        long myScore = 0;

        for (String roundInput : input) {
            RoundPlays plays = convertRoundInputIntoPlays(roundInput);

            RockPaperScissorsRound round = new RockPaperScissorsRound(
                    OPPONENT_STRATEGY.get(plays.opponentPlay),
                    MY_STRATEGY_PART_1.get(plays.myPlay)
            );

            myScore += round.getMyScore();
        }

        return myScore;
    }


    /* --- Part Two --- */
    public static long rockPaperScissorsPart2(List<String> input) {

        long myScore = 0;

        for (String roundInput : input) {
            RoundPlays plays = convertRoundInputIntoPlays(roundInput);

            Shapes opponentShape = OPPONENT_STRATEGY.get(plays.opponentPlay);

            RoundResults myRequiredResult = MY_STRATEGY_PART_2.get(plays.myPlay);
            Shapes myShape = getMyRequiredShape(opponentShape, myRequiredResult);

            RockPaperScissorsRound round = new RockPaperScissorsRound(opponentShape, myShape);

            myScore += round.getMyScore();
        }

        return myScore;
    }


    private static final Map<String, Shapes> OPPONENT_STRATEGY = new HashMap<>(Map.of(
            "A", Shapes.ROCK,
            "B", Shapes.PAPER,
            "C", Shapes.SCISSORS
    ));

    private static final Map<String, Shapes> MY_STRATEGY_PART_1 = new HashMap<>(Map.of(
            "X", Shapes.ROCK,
            "Y", Shapes.PAPER,
            "Z", Shapes.SCISSORS
    ));

    private static final Map<String, RoundResults> MY_STRATEGY_PART_2 = new HashMap<>(Map.of(
            "X", RoundResults.LOSS,
            "Y", RoundResults.DRAW,
            "Z", RoundResults.WIN
    ));


    private record RoundPlays(String opponentPlay, String myPlay) {
    }

    private static RoundPlays convertRoundInputIntoPlays(String roundInput) {
        String[] plays = roundInput.split(" ");
        return new RoundPlays(plays[0], plays[1]);
    }

    private static Shapes getMyRequiredShape(Shapes opponentShape, RoundResults myRequiredResult) {
        return switch (myRequiredResult) {
            case LOSS -> switch (opponentShape) {
                case ROCK -> Shapes.SCISSORS;
                case PAPER -> Shapes.ROCK;
                case SCISSORS -> Shapes.PAPER;
            };
            case DRAW -> opponentShape;
            case WIN -> switch (opponentShape) {
                case ROCK -> Shapes.PAPER;
                case PAPER -> Shapes.SCISSORS;
                case SCISSORS -> Shapes.ROCK;
            };
        };
    }


    private record RockPaperScissorsRound(Shapes opponentShape, Shapes myShape) {

        public int getMyScore() {
            return getMyRoundScore() + getMyShapeScore();
        }

        private int getMyRoundScore() {
            RoundResults result = switch (myShape) {
                case ROCK -> switch (opponentShape) {
                    case ROCK -> RoundResults.DRAW;
                    case PAPER -> RoundResults.LOSS;
                    case SCISSORS -> RoundResults.WIN;
                };
                case PAPER -> switch (opponentShape) {
                    case ROCK -> RoundResults.WIN;
                    case PAPER -> RoundResults.DRAW;
                    case SCISSORS -> RoundResults.LOSS;
                };
                case SCISSORS -> switch (opponentShape) {
                    case ROCK -> RoundResults.LOSS;
                    case PAPER -> RoundResults.WIN;
                    case SCISSORS -> RoundResults.DRAW;
                };
            };

            return result.getScore();
        }

        private int getMyShapeScore() {
            return myShape.getScore();
        }
    }

    private enum Shapes {
        ROCK(1), PAPER(2), SCISSORS(3);

        private final int score;

        Shapes(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }

    private enum RoundResults {
        LOSS(0), DRAW(3), WIN(6);

        private final int score;

        RoundResults(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_02.txt");

        System.out.println("Part 1: " + rockPaperScissorsPart1(input));
        System.out.println("Part 2: " + rockPaperScissorsPart2(input));
    }
}
