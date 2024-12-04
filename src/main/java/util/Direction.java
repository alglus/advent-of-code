package util;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public enum Direction {
    R(new Step(1, 0)), L(new Step(-1, 0)), U(new Step(0, -1)), D(new Step(0, 1)),
    UR(new Step(1, -1)), DR(new Step(1, 1)), DL(new Step(-1, 1)), UL(new Step(-1, -1));

    public final Step step;

    public static Direction getOpposite(final Direction direction) {
        return switch (direction) {
            case R -> L;
            case L -> R;
            case U -> D;
            case D -> U;
            case UR -> DL;
            case DR -> UL;
            case DL -> UR;
            case UL -> DR;
        };
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
}

