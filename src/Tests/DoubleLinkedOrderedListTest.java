package Tests;

import ADTs.ArrayOrderedList;
import ADTs.DoubleLinkedOrderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.NonComparableElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoubleLinkedOrderedListTest {

    private DoubleLinkedOrderedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoubleLinkedOrderedList<>();
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
    void testAddElementToEmptyList() throws NonComparableElementException {
        list.add(5);
        assertEquals(1, list.size());
        assertEquals("[5]", list.toString());
    }

    @Test
    void testAddElementToBeginning() throws NonComparableElementException {
        list.add(5);
        list.add(3);
        assertEquals(2, list.size());
        assertEquals("[3, 5]", list.toString());
    }

    @Test
    void testAddElementToEnd() throws NonComparableElementException {
        list.add(3);
        list.add(5);
        assertEquals(2, list.size());
        assertEquals("[3, 5]", list.toString());
    }

    @Test
    void testAddElementToMiddle() throws NonComparableElementException {
        list.add(3);
        list.add(5);
        list.add(4);
        assertEquals(3, list.size());
        assertEquals("[3, 4, 5]", list.toString());
    }

    @Nested
    public class NonComparableElementTest {

        private DoubleLinkedOrderedList<Object> objectList;

        @BeforeEach
        void setUp() {
            objectList = new DoubleLinkedOrderedList<>();
        }

        @Test
        void testAddThrowsNonComparableElementException() {
            assertThrows(NonComparableElementException.class, () -> objectList.add(new Object()));
        }
    }


    @Test
    void testAddThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> list.add(null));
    }

    @Test
    void testRemoveFirst() throws EmptyCollectionException, NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(1, list.removeFirst());
        assertEquals("[2, 3]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    void testRemoveLast() throws EmptyCollectionException, NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.removeLast());
        assertEquals("[1, 2]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    void testRemoveElement() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(2, list.remove(2));
        assertEquals("[1, 3]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    void testRemoveFirstThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> list.removeFirst());
    }

    @Test
    void testRemoveLastThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> list.removeLast());
    }

    @Test
    void testRemoveThrowsElementNotFoundException() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThrows(ElementNotFoundException.class, () -> list.remove(5));
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

    // Iterator tests
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
    void testIteratorNoSuchElementException() throws NonComparableElementException {
        list.add(1);
        list.add(2);

        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIteratorConcurrentModificationException() throws NonComparableElementException {
        list.add(1);
        list.add(2);

        Iterator<Integer> iterator = list.iterator();
        list.add(3);  // Modify the list during iteration

        assertThrows(ConcurrentModificationException.class, iterator::next);
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
    void testIteratorRemoveThrowsConcurrentModificationException() throws NonComparableElementException {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();
        assertEquals(1, iterator.next());
        list.add(4);  // Modify the list

        assertThrows(ConcurrentModificationException.class, () -> iterator.remove());

    }

    @Test
    void testRemoveFromEmptyListThrowsException() {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        assertThrows(EmptyCollectionException.class, () -> list.remove(1));
    }

    @Test
    void testRemoveOnlyElement() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        assertEquals(1, list.remove(1));
        assertTrue(list.isEmpty());
    }

    @Test
    void testRemoveHead() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3); // List: [1, 2, 3]

        assertEquals(1, list.remove(1)); // Removing head
        assertEquals(2, list.first());   // New head should be 2
    }

    @Test
    void testRemoveTail() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3); // List: [1, 2, 3]

        assertEquals(3, list.remove(3)); // Removing tail
        assertEquals(2, list.last());    // New tail should be 2
    }

    @Test
    void testRemoveMiddleElement() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3); // List: [1, 2, 3]

        assertEquals(2, list.remove(2)); // Removing the middle element
        assertEquals("[1, 3]", list.toString());
    }

    @Test
    void testContainsElementNotFound() throws NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertFalse(list.contains(4)); // Element 4 is not in the list
    }

    @Test
    void testIteratorRemoveLastElement() throws NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3); // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        iterator.next();
        iterator.next(); // Now at the last element

        iterator.remove(); // Remove last element
        assertEquals("[1, 2]", list.toString());
    }

    @Test
    void testIteratorRemoveFirstElement() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3); // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();
        iterator.next(); // Now at the first element

        iterator.remove(); // Remove first element
        assertEquals("[2, 3]", list.toString());
    }

    @Test
    void testIteratorRemoveMiddleElement() throws EmptyCollectionException, ElementNotFoundException, NonComparableElementException {
        DoubleLinkedOrderedList<Integer> list = new DoubleLinkedOrderedList<>();
        list.add(1);
        list.add(2);
        list.add(3); // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();
        iterator.next(); // 1
        iterator.next(); // 2 (middle element)

        iterator.remove(); // Remove middle element
        assertEquals("[1, 3]", list.toString());
    }

    @Test
    void testRemoveFirstWhenOnlyOneElement() throws EmptyCollectionException, NonComparableElementException {
        list.add(1);  // Add a single element

        assertEquals(1, list.removeFirst());  // Remove that element
        assertTrue(list.isEmpty());  // The list should now be empty

        list.add(1);
        assertEquals("[1]", list.toString());
    }

    @Test
    void testRemoveLastWhenOnlyOneElement() throws EmptyCollectionException, NonComparableElementException {
        list.add(1);  // Add a single element

        assertEquals(1, list.removeLast());  // Remove that element
        assertTrue(list.isEmpty());  // The list should now be empty

        list.add(1);
        assertEquals("[1]", list.toString());
    }

}