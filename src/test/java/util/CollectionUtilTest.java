package util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionUtilTest {

    @Test
    void moveElementToIndex_moveToBack() {
        final List<String> list = new ArrayList<>(List.of("a", "b", "c", "d", "e"));
        final int fromIndex = 3;
        final int toIndex = 1;
        final List<String> expectedList = List.of("a", "d", "b", "c", "e");

        final List<String> result = CollectionUtil.moveElementToIndex(list, fromIndex, toIndex);

        assertEquals(expectedList, result);
    }

    @Test
    void moveElementToIndex_moveToFront() {
        final List<String> list = new ArrayList<>(List.of("a", "b", "c", "d", "e"));
        final int fromIndex = 0;
        final int beforeIndex = 3;
        final List<String> expectedList = List.of("b", "c", "d", "a", "e");

        final List<String> result = CollectionUtil.moveElementToIndex(list, fromIndex, beforeIndex);

        assertEquals(expectedList, result);
    }

    @Test
    void moveElementToIndex_emptyList_throwsOutOfBoundsException() {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> CollectionUtil.moveElementToIndex(Collections.emptyList(), 0, 0));
    }

    @Test
    void moveElementBeforeIndex_oneElementList() {
        final List<String> list = new ArrayList<>(List.of("a"));
        final List<String> expectedList = List.of("a");

        final List<String> result = CollectionUtil.moveElementToIndex(list, 0, 0);

        assertEquals(expectedList, result);
    }
}