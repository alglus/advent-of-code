package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void add_numberOfSteps() {
        final Point startingPoint = new Point(0,0);
        final int numberOfSteps = 3;
        final Step step = Direction.DR.step;
        final Point expectedEndPoint = new Point(3,3);

        final Point endPoint = startingPoint.add(step, numberOfSteps);

        assertEquals(expectedEndPoint, endPoint);
    }
}