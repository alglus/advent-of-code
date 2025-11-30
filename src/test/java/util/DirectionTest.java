package util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Direction.*;

class DirectionTest {

    @ParameterizedTest
    @EnumSource(Direction.class)
    void opposite(Direction direction) {
        Direction expectedOpposite = switch (direction) {
            case R -> L;
            case L -> R;
            case U -> D;
            case D -> U;
            case UR -> DL;
            case DR -> UL;
            case DL -> UR;
            case UL -> DR;
        };

        assertEquals(expectedOpposite, direction.opposite());
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void getOpposite(Direction direction) {
        Direction expectedOpposite = switch (direction) {
            case R -> L;
            case L -> R;
            case U -> D;
            case D -> U;
            case UR -> DL;
            case DR -> UL;
            case DL -> UR;
            case UL -> DR;
        };

        assertEquals(expectedOpposite, Direction.getOpposite(direction));
    }
}