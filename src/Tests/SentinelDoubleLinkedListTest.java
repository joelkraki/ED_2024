package Tests;

import ADTs.SentinelDoubleLinkedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidParameterTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SentinelDoubleLinkedListTest {

    private SentinelDoubleLinkedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new SentinelDoubleLinkedList<>();
    }

    @Test
    public void testAddFront() {
        list.addFront(10);
        list.addFront(20);
        list.addFront(30);
        assertEquals("[30, 20, 10]", list.toString());
    }

    @Test
    public void testAddLast() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        assertEquals("[10, 20, 30]", list.toString());
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
    public void testRemoveLast() throws EmptyCollectionException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        assertEquals(30, list.removeLast());
        assertEquals("[10, 20]", list.toString());
    }

    @Test
    public void testRemoveElement() throws EmptyCollectionException, ElementNotFoundException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        assertEquals(20, list.remove(20));
        assertEquals("[10, 30]", list.toString());
    }

    @Test
    public void testRemoveElementNotFound() {
        list.addLast(10);
        list.addLast(20);
        assertThrows(ElementNotFoundException.class, () -> list.remove(30));
    }

    @Test
    public void testRemoveFromEmptyList() {
        assertThrows(EmptyCollectionException.class, () -> list.removeFront());
        assertThrows(EmptyCollectionException.class, () -> list.removeLast());
    }

    @Test
    public void testGetArray() throws ElementNotFoundException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        Integer[] array = new Integer[2];
        Integer[] resultArray = list.getArray(array, 10, 20);
        assertArrayEquals(new Integer[]{10, 20}, resultArray);
    }

    @Test
    public void testGetFullArray() throws ElementNotFoundException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        Integer[] array = new Integer[3];
        Integer[] resultArray = list.getFullArray(array);
        assertArrayEquals(new Integer[]{10, 20, 30}, resultArray);
    }

    @Test
    public void testGetArrayFrom() throws ElementNotFoundException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        Integer[] array = new Integer[2];
        Integer[] resultArray = list.getArrayFrom(array, 20);
        assertArrayEquals(new Integer[]{20, 30}, resultArray);
    }

    @Test
    public void testGetArrayTo() throws ElementNotFoundException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        Integer[] array = new Integer[2];
        Integer[] resultArray = list.getArrayTo(array, 20);
        assertArrayEquals(new Integer[]{10, 20}, resultArray);
    }

    @Test
    public void testGetPairList() throws InvalidParameterTypeException {
        SentinelDoubleLinkedList<Integer> intList = new SentinelDoubleLinkedList<>();
        intList.addLast(10);
        intList.addLast(15);
        intList.addLast(20);

        SentinelDoubleLinkedList<Integer> pairList = intList.getPairList();
        assertEquals("[10, 20]", pairList.toString());
    }

    @Test
    public void testGetPairListInvalidType() {
        SentinelDoubleLinkedList<String> stringList = new SentinelDoubleLinkedList<>();
        stringList.addLast("A");
        stringList.addLast("B");

        assertThrows(InvalidParameterTypeException.class, stringList::getPairList);
    }

    @Test
    public void testCountAndRemove() throws EmptyCollectionException {
        list.addLast(10);
        list.addLast(20);
        list.addLast(10);
        list.addLast(30);
        int count = list.countAndRemove(10);
        assertEquals(2, count);
        assertEquals("[20, 30]", list.toString());
    }

    @Test
    public void testCountAndRemoveNotFound() throws EmptyCollectionException {
        list.addLast(10);
        list.addLast(20);
        int count = list.countAndRemove(30);
        assertEquals(0, count);
        assertEquals("[10, 20]", list.toString());
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.addLast(10);
        list.addLast(20);
        assertEquals(2, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.addLast(10);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testToString() {
        list.addLast(10);
        list.addLast(20);
        assertEquals("[10, 20]", list.toString());
    }
}
