package util;

public class Matrix<T> {
    public final T[][] matrix;
    public final int width;
    public final int heigth;

    private Matrix(T[][] matrix, int width, int heigth) {
        this.matrix = matrix;
        this.width = width;
        this.heigth = heigth;
    }

    public static <T> Matrix<T> of(T[][] matrix) {
        return new Matrix<>(matrix, matrix[0].length, matrix.length);
    }

    public T at(int x, int y) {
        return matrix[y][x];
    }

    public T at(Point point) {
        return at(point.x(), point.y());
    }

    public boolean isAtRightBorder(int x) {
        return x == width - 1;
    }
}
