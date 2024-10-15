package Tests;

import ADTs.SentinelSingleLinkedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SentinelSingleLinkedListTest {

    private SentinelSingleLinkedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new SentinelSingleLinkedList<>();
    }

    @Test
    public void testAddFront() {
        list.addFront(10);
        list.addFront(20);
        list.addFront(30);
        assertEquals("[30, 20, 10]", list.toString());
    }

    @Test
    public void testRemoveElement() throws EmptyCollectionException, ElementNotFoundException {
        list.addFront(10);
        list.addFront(20);
        list.addFront(30);
        assertEquals(20, list.remove(20));
        assertEquals("[30, 10]", list.toString());
    }

    @Test
    public void testRemoveFront() throws EmptyCollectionException {
        list.addFront(10);
        list.addFront(20);
        list.addFront(30);
        assertEquals(30, list.removeFront());
        assertEquals("[20, 10]", list.toString());
    }

    @Test
    public void testRemoveFrontSingleElement() throws EmptyCollectionException {
        list.addFront(10);
        assertEquals(10, list.removeFront());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveNonExistentElement() {
        list.addFront(10);
        list.addFront(20);
        assertThrows(ElementNotFoundException.class, () -> list.remove(30));
    }

    @Test
    public void testRemoveFromEmptyList() {
        assertThrows(EmptyCollectionException.class, () -> list.remove(10));
    }

    @Test
    public void testRemoveFrontFromEmptyList() {
        assertThrows(EmptyCollectionException.class, () -> list.removeFront());
    }

    @Test
    public void testAddNullElement() {
        assertThrows(IllegalArgumentException.class, () -> list.addFront(null));
    }

    @Test
    public void testRemoveNullElement() {
        assertThrows(IllegalArgumentException.class, () -> list.remove(null));
    }

    @Test
    public void testFirst() throws EmptyCollectionException {
        list.addFront(10);
        list.addFront(20);
        assertEquals(20, list.first());
    }

    @Test
    public void testFirstFromEmptyList() {
        assertThrows(EmptyCollectionException.class, () -> list.first());
    }

    @Test
    public void testSizeAfterAddAndRemove() throws EmptyCollectionException, ElementNotFoundException {
        assertEquals(0, list.size());
        list.addFront(10);
        list.addFront(20);
        assertEquals(2, list.size());
        list.remove(10);
        assertEquals(1, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.addFront(10);
        assertFalse(list.isEmpty());
    }
}
