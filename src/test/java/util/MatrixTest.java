package util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void hasPatternAt() {
        final Matrix<Character> matrix = Matrix.of(new Character[][]{
                {'.', '.', '.'},
                {'.', 'x', '.'},
                {'.', '.', 'x'}
        });
        final Matrix<Character> pattern = Matrix.of(new Character[][]{
                {'x', null},
                {null, 'x'}
        });
        final List<Point> pointsWithNoPattern = List.of(
                Point.at(0,0), Point.at(1,0), Point.at(2,0),
                Point.at(0,1), Point.at(2,1),
                Point.at(0,2), Point.at(1,2), Point.at(2,2)
        );

        assertTrue(matrix.hasPatternAt(1, 1, pattern));

        for (final Point point : pointsWithNoPattern) {
            assertFalse(matrix.hasPatternAt(point.x(), point.y(), pattern));
        }
    }
}