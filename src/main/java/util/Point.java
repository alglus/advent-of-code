package util;

import java.util.Objects;

public record Point(int x, int y) {

    public static Point at(final int x, final int y) {
        return new Point(x, y);
    }

    public Point add(final Step step) {
        return new Point(x + step.x(), y + step.y());
    }

    public Point add(final Step step, final int numberOfSteps) {
        return new Point(x + numberOfSteps * step.x(), y + numberOfSteps * step.y());
    }

    public <T> boolean isOutsideOf(final Matrix<T> matrix) {
        return x < 0 || x >= matrix.width || y < 0 || y >= matrix.height;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Point otherPoint = (Point) o;
        return x == otherPoint.x && y == otherPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
