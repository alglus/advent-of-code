package util;

public record Point(int x, int y) {

    public static Point at(final int x, final int y) {
        return new Point(x, y);
    }

    public Point add(final Step step) {
        return new Point(x + step.x(), y + step.y());
    }

    public <T> boolean isOutsideOf(final Matrix<T> matrix) {
        return x < 0 || x >= matrix.width || y < 0 || y >= matrix.height;
    }

}
