package util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <T> Matrix<T> create(Class<T> clazz, int width, int heigth, T initValue) {
        @SuppressWarnings("unchecked")
        var matrix = (T[][]) Array.newInstance(clazz, heigth, width);
        Arrays.stream(matrix).forEach(ts -> Arrays.fill(ts, initValue));
        return new Matrix<>(matrix, width, heigth);
    }

    public T at(int x, int y) {
        return matrix[y][x];
    }

    public T at(Point point) {
        return at(point.x(), point.y());
    }

    public void set(int x, int y, T value) {
        matrix[y][x] = value;
    }

    public void set(Point point, T value) {
        set(point.x(), point.y(), value);
    }

    public boolean isAtRightBorder(int x) {
        return x == width - 1;
    }

    public Stream<T> stream() {
        return Arrays.stream(matrix).flatMap(Arrays::stream);
    }

    @Override
    public String toString() {
        return Arrays.stream(matrix)
                .map(array -> Arrays.stream(array)
                        .map(String::valueOf)
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }

    public String toString(Function<T, String> mapValueToString) {
        return Arrays.stream(matrix)
                .map(array -> Arrays.stream(array)
                        .map(mapValueToString)
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }
}
