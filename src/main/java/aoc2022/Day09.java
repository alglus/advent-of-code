package aoc2022;

import util.Directions;
import util.Position;
import util.Step;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

public class Day09 extends Puzzle2022 {

    public Day09() {
        super(9);
    }

    public static void main(String[] args) {
        new Day09().printSolutions();
    }

    private Instruction getInstruction(String input) {
        var instructionInput = input.split(" ");

        return new Instruction(
                Directions.valueOf(instructionInput[0]),
                Integer.parseInt(instructionInput[1])
        );
    }

    private void adjustTail(Position tail, Position head) {
        int xDistance = head.xDistanceTo(tail);
        int yDistance = head.yDistanceTo(tail);

        if (abs(xDistance) == 2) {
            int xStep = xDistance > 0 ? 1 : -1;

            tail.move(new Step(xStep, 0));
            moveDiagonallyY(tail, yDistance);

        } else if (abs(yDistance) == 2) {
            int yStep = yDistance > 0 ? 1 : -1;

            tail.move(new Step(0, yStep));
            moveDiagonallyX(tail, xDistance);
        }
    }

    private void moveDiagonallyY(Position tail, int yDistance) {
        if (yDistance >= 1) tail.move(new Step(0, 1));
        if (yDistance <= -1) tail.move(new Step(0, -1));
    }

    private void moveDiagonallyX(Position tail, int xDistance) {
        if (xDistance >= 1) tail.move(new Step(1, 0));
        if (xDistance <= -1) tail.move(new Step(-1, 0));
    }

    private List<Position> newRope(int length) {
        List<Position> rope = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            rope.add(new Position(0, 0));
        }

        return rope;
    }

    @Override
    public String solvePart1() {
        var head = new Position(0, 0);
        var tail = new Position(0, 0);

        Set<String> visitedPositions = new HashSet<>();

        for (String inputLine : getInputLines()) {
            var instruction = getInstruction(inputLine);

            for (int s = 0; s < instruction.steps; s++) {

                head.move(instruction.direction().step());
                adjustTail(tail, head);

                visitedPositions.add(tail.toString());
            }
        }

        return String.valueOf(visitedPositions.size());
    }

    @Override
    public String solvePart2() {
        var head = new Position(0, 0);
        var rope = newRope(9);

        Set<String> visitedPositions = new HashSet<>();

        for (String inputLine : getInputLines()) {
            var instruction = getInstruction(inputLine);

            for (int s = 0; s < instruction.steps; s++) {
                head.move(instruction.direction().step());
                var newHead = head;

                for (Position knot : rope) {
                    adjustTail(knot, newHead);
                    newHead = knot;
                }

                visitedPositions.add(rope.get(rope.size() - 1).toString());
            }
        }

        return String.valueOf(visitedPositions.size());
    }

    private record Instruction(Directions direction, int steps) {
    }
}
