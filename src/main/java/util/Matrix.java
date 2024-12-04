package util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrix<T> {
    public final T[][] matrix;
    public final int width;
    public final int height;

    private Matrix(final T[][] matrix, final int width, final int height) {
        this.matrix = matrix;
        this.width = width;
        this.height = height;
    }

    public static <T> Matrix<T> of(final T[][] matrix) {
        return new Matrix<>(matrix, matrix[0].length, matrix.length);
    }

    public static <T> Matrix<T> create(final Class<T> clazz, final int width, final int height, final T initValue) {
        @SuppressWarnings("unchecked") final var matrix = (T[][]) Array.newInstance(clazz, height, width);
        Arrays.stream(matrix).forEach(ts -> Arrays.fill(ts, initValue));
        return new Matrix<>(matrix, width, height);
    }

    public T at(final int x, final int y) {
        return matrix[y][x];
    }

    public T at(final Point point) {
        return at(point.x(), point.y());
    }

    public void set(final int x, final int y, final T value) {
        matrix[y][x] = value;
    }

    public void set(final Point point, final T value) {
        set(point.x(), point.y(), value);
    }

    public boolean isAtRightBorder(final int x) {
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

    public String toString(final Function<T, String> mapValueToString) {
        return Arrays.stream(matrix)
                .map(array -> Arrays.stream(array)
                        .map(mapValueToString)
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }
}
