package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilTest {

    @Test
    void copyOfExcluding() {
        final Integer[] array = {1, 2, 3, 4, 5};
        final int indexToExclude = 2;
        final Integer[] expectedArray = {1, 2, 4, 5};

        final Integer[] resultArray = Util.copyOfExcluding(array, indexToExclude, Integer.class);

        assertArrayEquals(expectedArray, resultArray);
    }

    @Test
    void copyOfExcluding_emptyArray() {
        final String[] array = {};
        final String[] expectedArray = {};

        final String[] resultArray = Util.copyOfExcluding(array, 0, String.class);

        assertArrayEquals(expectedArray, resultArray);
    }

    @Test
    void copyOfExcluding_indexOutOfBounds() {
        final Integer[] array = {1, 2, 3, 4, 5};
        final int indexToExclude = 42;

        assertThrows(IllegalArgumentException.class, () -> Util.copyOfExcluding(array, indexToExclude, Integer.class));
    }
}