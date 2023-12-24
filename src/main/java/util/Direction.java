package util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Direction {
    R(new Step(1, 0)), L(new Step(-1, 0)), U(new Step(0, -1)), D(new Step(0, 1));

    public final Step step;

    public static Direction getOpposite(Direction direction) {
        return switch (direction) {
            case R -> L;
            case L -> R;
            case U -> D;
            case D -> U;
        };
    }

    public Direction opposite() {
        return Direction.getOpposite(this);
    }

    public boolean isOpposite(Direction direction) {
        return direction == opposite();
    }

}

