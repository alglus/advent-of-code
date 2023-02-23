package aoc2022;

import util.Position;
import util.Step;
import util.Util;

import java.util.*;

import static java.lang.Math.abs;

public class Day09 {

    /* --- Part One --- */
    public static int ropeBridgePart1(List<String> input) {

        Position head = new Position(0, 0);
        Position tail = new Position(0, 0);

        Set<String> visitedPositions = new HashSet<>();

        for (String inputLine : input) {
            Instruction instruction = getInstruction(inputLine);

            for (int s = 0; s < instruction.steps; s++) {

                head.move(instruction.direction().step);
                adjustTail(tail, head);

                visitedPositions.add(tail.toString());
            }
        }

        return visitedPositions.size();
    }


    /* --- Part Two --- */
    public static int ropeBridgePart2(List<String> input) {

        Position head = new Position(0, 0);

        List<Position> rope = newRope(9);

        Set<String> visitedPositions = new HashSet<>();

        for (String inputLine : input) {
            Instruction instruction = getInstruction(inputLine);

            for (int s = 0; s < instruction.steps; s++) {
                head.move(instruction.direction().step);
                Position newHead = head;

                for (Position knot : rope) {
                    adjustTail(knot, newHead);
                    newHead = knot;
                }

                visitedPositions.add(rope.get(rope.size() - 1).toString());
            }
        }

        return visitedPositions.size();
    }


    private record Instruction(Directions direction, int steps) {
    }

    private static Instruction getInstruction(String input) {
        String[] instructionInput = input.split(" ");

        return new Instruction(
                Directions.valueOf(instructionInput[0]),
                Integer.parseInt(instructionInput[1])
        );
    }

    private static void adjustTail(Position tail, Position head) {
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

    private static void moveDiagonallyY(Position tail, int yDistance) {
        if (yDistance >= 1) tail.move(new Step(0, 1));
        if (yDistance <= -1) tail.move(new Step(0, -1));
    }

    private static void moveDiagonallyX(Position tail, int xDistance) {
        if (xDistance >= 1) tail.move(new Step(1, 0));
        if (xDistance <= -1) tail.move(new Step(-1, 0));
    }

    private enum Directions {
        R(1, 0), L(-1, 0), U(0, -1), D(0, 1);

        private final Step step;

        Directions(int stepX, int stepY) {
            this.step = new Step(stepX, stepY);
        }
    }

    private static List<Position> newRope(int length) {
        List<Position> rope = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            rope.add(new Position(0, 0));
        }

        return rope;
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_09.txt");

        System.out.println("Part 1: " + Day09.ropeBridgePart1(input));
        System.out.println("Part 2: " + Day09.ropeBridgePart2(input));
    }
}
