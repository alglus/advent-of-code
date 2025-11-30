package util;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;

@AllArgsConstructor
public enum Direction {
    R(new Step(1, 0)), L(new Step(-1, 0)), U(new Step(0, -1)), D(new Step(0, 1)),
    UR(new Step(1, -1)), DR(new Step(1, 1)), DL(new Step(-1, 1)), UL(new Step(-1, -1));

    public final Step step;
    private final BiFunction<Direction, Direction, RuntimeException> NO_90_DEGREE =
            (thisDir, otherDir) -> new IllegalStateException("The direction " + otherDir +
                    " has no 90° angle to " + thisDir);

    public static Direction getOpposite(final Direction direction) {
        return direction.rotate90DegreesClockwise().rotate90DegreesClockwise();
    }

    public static List<Direction> cardinal() {
        return List.of(R, L, U, D);
    }

    public static List<Direction> intermediate() {
        return List.of(UR, DR, DL, UL);
    }

    public Direction opposite() {
        return Direction.getOpposite(this);
    }

    public boolean isOpposite(final Direction direction) {
        return direction == opposite();
    }

    public Direction rotate90DegreesClockwise() {
        return switch (this) {
            case R -> D;
            case L -> U;
            case U -> R;
            case D -> L;
            case UR -> DR;
            case DR -> DL;
            case DL -> UL;
            case UL -> UR;
        };
    }

    public int get90DegreeRotationsFrom(final Direction otherDirection) {
        return switch (this) {
            case R -> switch (otherDirection) {
                case R -> 0;
                case L -> 2;
                case U, D -> 1;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case L -> switch (otherDirection) {
                case R -> 2;
                case L -> 0;
                case U, D -> 1;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case U -> switch (otherDirection) {
                case R, L -> 1;
                case U -> 0;
                case D -> 2;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case D -> switch (otherDirection) {
                case R, L -> 1;
                case U -> 2;
                case D -> 0;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case UR -> switch (otherDirection) {
                case UR -> 0;
                case DR, UL -> 1;
                case DL -> 2;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case DR -> switch (otherDirection) {
                case UR, DL -> 1;
                case DR -> 0;
                case UL -> 2;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case DL -> switch (otherDirection) {
                case UR -> 2;
                case DR, UL -> 1;
                case DL -> 0;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
            case UL -> switch (otherDirection) {
                case UR, DL -> 1;
                case DR -> 2;
                case UL -> 0;
                default -> throw NO_90_DEGREE.apply(this, otherDirection);
            };
        };
    }
}

