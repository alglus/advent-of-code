package aoc2022;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Day02 extends Puzzle2022 {

    private final Map<String, Shapes> OPPONENT_STRATEGY = new HashMap<>(Map.of(
            "A", Shapes.ROCK,
            "B", Shapes.PAPER,
            "C", Shapes.SCISSORS
    ));
    private final Map<String, Shapes> MY_STRATEGY_PART_1 = new HashMap<>(Map.of(
            "X", Shapes.ROCK,
            "Y", Shapes.PAPER,
            "Z", Shapes.SCISSORS
    ));
    private final Map<String, RoundResults> MY_STRATEGY_PART_2 = new HashMap<>(Map.of(
            "X", RoundResults.LOSS,
            "Y", RoundResults.DRAW,
            "Z", RoundResults.WIN
    ));

    public Day02() {
        super(2);
    }

    public static void main(String[] args) {
        new Day02().printSolutions();
    }

    private RoundPlays convertRoundInputIntoPlays(String roundInput) {
        var plays = roundInput.split(" ");
        return new RoundPlays(plays[0], plays[1]);
    }

    private Shapes getMyRequiredShape(Shapes opponentShape, RoundResults myRequiredResult) {
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

    @Override
    public String solvePart1() {
        long myScore = 0;

        for (String roundInput : getInputLines()) {
            var plays = convertRoundInputIntoPlays(roundInput);

            var round = new RockPaperScissorsRound(
                    OPPONENT_STRATEGY.get(plays.opponentPlay),
                    MY_STRATEGY_PART_1.get(plays.myPlay)
            );

            myScore += round.getMyScore();
        }

        return String.valueOf(myScore);
    }

    @Override
    public String solvePart2() {
        long myScore = 0;

        for (String roundInput : getInputLines()) {
            var plays = convertRoundInputIntoPlays(roundInput);

            var opponentShape = OPPONENT_STRATEGY.get(plays.opponentPlay);

            var myRequiredResult = MY_STRATEGY_PART_2.get(plays.myPlay);
            var myShape = getMyRequiredShape(opponentShape, myRequiredResult);

            var round = new RockPaperScissorsRound(opponentShape, myShape);

            myScore += round.getMyScore();
        }

        return String.valueOf(myScore);
    }

    @Getter
    @AllArgsConstructor
    private enum Shapes {
        ROCK(1), PAPER(2), SCISSORS(3);

        private final int score;
    }

    @Getter
    @AllArgsConstructor
    private enum RoundResults {
        LOSS(0), DRAW(3), WIN(6);

        private final int score;
    }

    private record RoundPlays(String opponentPlay, String myPlay) {
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
}
