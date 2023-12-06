package util;

public class Matrix {
    public final char[][] matrix;
    public final int width;
    public final int heigth;

    private Matrix(char[][] matrix, int width, int heigth) {
        this.matrix = matrix;
        this.width = width;
        this.heigth = heigth;
    }

    public static Matrix of(char[][] matrix) {
        return new Matrix(matrix, matrix[0].length, matrix.length);
    }

    public char xy(int x, int y) {
        return matrix[y][x];
    }

    public boolean isAtRightBorder(int x) {
        return x == width - 1;
    }
}
