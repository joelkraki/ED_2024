package Tests;

import ADTs.ArrayOrderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.NonComparableElementException;
import org.junit.jupiter.api.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayOrderedListTest {

    private ArrayOrderedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new ArrayOrderedList<>();
    }

    @Test
    void testAddElement() throws NonComparableElementException {
        list.add(3);
        list.add(1);
        list.add(2);

        assertEquals(3, list.size());
        assertEquals("[1, 2, 3]", list.toString());  // Ensure elements are sorted
    }

    @Test
    void testRemoveFirst() throws EmptyCollectionException, NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(1, list.removeFirst());
        assertEquals("[2, 3]", list.toString());
    }

    @Test
    void testRemoveLast() throws EmptyCollectionException, NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.removeLast());
        assertEquals("[1, 2]", list.toString());
    }

    @Test
    void testRemoveSpecificElement() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(2, list.remove(2));
        assertEquals("[1, 3]", list.toString());
    }

    @Test
    void testRemoveThrowsElementNotFoundException() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThrows(ElementNotFoundException.class, () -> list.remove(5));
    }

    @Test
    void testRemoveFirstEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> list.removeFirst());
    }

    @Test
    void testRemoveLastEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> list.removeLast());
    }

    @Test
    void testFirst() throws EmptyCollectionException, NonComparableElementException {
        list.add(3);
        list.add(1);
        list.add(2);

        assertEquals(1, list.first());
    }

    @Test
    void testFirstThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> list.first());
    }

    @Test
    void testLast() throws EmptyCollectionException, NonComparableElementException {
        list.add(3);
        list.add(1);
        list.add(2);

        assertEquals(3, list.last());
    }

    @Test
    void testLastThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> list.last());
    }

    @Test
    void testContains() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertTrue(list.contains(2));
        assertFalse(list.contains(5));
    }

    @Test
    void testIsEmpty() throws NonComparableElementException {
        assertTrue(list.isEmpty());

        list.add(1);
        assertFalse(list.isEmpty());
    }

    @Test
    void testSize() throws NonComparableElementException {
        assertEquals(0, list.size());

        list.add(1);
        assertEquals(1, list.size());

        list.add(2);
        assertEquals(2, list.size());
    }

    @Test
    void testIterator() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorConcurrentModificationException() throws NonComparableElementException {
        list.add(1);
        list.add(2);

        Iterator<Integer> iterator = list.iterator();

        list.add(3);  // Modify the list while iterating

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testExpandCapacity() throws NonComparableElementException {
        ArrayOrderedList<Integer> smallList = new ArrayOrderedList<>(2);
        smallList.add(1);
        smallList.add(2);
        smallList.add(3);  // Should trigger capacity expansion

        assertEquals(3, smallList.size());
        assertEquals("[1, 2, 3]", smallList.toString());
    }

    @Test
    void testIteratorRemoveBeforeNext() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);  // Remove called before next()
    }

    @Test
    void testIteratorRemoveAfterNext() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());
        iterator.remove();  // Valid removal
        assertEquals("[2, 3]", list.toString());  // Check that the first element was removed
    }

    @Test
    void testIteratorRemoveThrowsConcurrentModificationException() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();
        list.add(4);  // Modify the list

        assertThrows(ConcurrentModificationException.class, iterator::remove);
    }

    @Test
    void testIteratorRemoveTwiceWithoutNext() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();

        iterator.next();  // Move to first element
        iterator.remove();  // Remove first element
        assertThrows(UnsupportedOperationException.class, iterator::remove);  // Try removing again without calling next()
    }

    @Test
    void testIteratorRemoveAfterListModification() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();

        iterator.next();  // Move to first element
        list.add(4);  // Modify the list while iterating

        assertThrows(ConcurrentModificationException.class, iterator::remove);
    }


    @Nested
    public class NonComparableElementTest {

        private ArrayOrderedList<Object> objectList;

        @BeforeEach
        void setUp() {
            objectList = new ArrayOrderedList<>();
        }

        @Test
        void testAddThrowsNonComparableElementException() {
            assertThrows(NonComparableElementException.class, () -> objectList.add(new Object()));
        }
    }


}