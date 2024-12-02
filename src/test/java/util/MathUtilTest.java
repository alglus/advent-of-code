package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathUtilTest {

    @Test
    void isSignDifferent_true() {
        assertTrue(MathUtil.isSignDifferent(1, -1));
    }

    @Test
    void isSignDifferent_false() {
        assertFalse(MathUtil.isSignDifferent(2, 2));
    }
}