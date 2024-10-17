package Tests;

import ADTs.DoubleLinkedUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleLinkedUnorderedListTest {

    private DoubleLinkedUnorderedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoubleLinkedUnorderedList<>();
    }
    @Test
    void testAddToFront() throws EmptyCollectionException {
        list.addToFront(1);  // List should be [1]
        assertEquals(1, list.size());
        assertEquals(1, list.first());

        list.addToFront(2);  // List should be [2, 1]
        assertEquals(2, list.size());
        assertEquals(2, list.first());
        assertEquals(1, list.last());

        list.addToFront(3);  // List should be [3, 2, 1]
        assertEquals(3, list.size());
        assertEquals(3, list.first());
        assertEquals(1, list.last());
    }

    @Test
    void testAddToRear() throws EmptyCollectionException {
        list.addToRear(1);  // List should be [1]
        assertEquals(1, list.size());
        assertEquals(1, list.first());

        list.addToRear(2);  // List should be [1, 2]
        assertEquals(2, list.size());
        assertEquals(1, list.first());
        assertEquals(2, list.last());

        list.addToRear(3);  // List should be [1, 2, 3]
        assertEquals(3, list.size());
        assertEquals(1, list.first());
        assertEquals(3, list.last());
    }

    @Test
    void testAddAfter() throws EmptyCollectionException, ElementNotFoundException {
        list.addToRear(1);  // List is [1]
        list.addToRear(2);  // List is [1, 2]
        list.addToRear(4);  // List is [1, 2, 4]

        list.addAfter(3, 2);  // Add 3 after 2, list should be [1, 2, 3, 4]

        assertEquals(4, list.size());
        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertEquals(4, iterator.next());
    }

    @Test
    void testAddAfterThrowsElementNotFoundException() {
        list.addToRear(1);
        list.addToRear(2);

        assertThrows(ElementNotFoundException.class, () -> {
            list.addAfter(3, 5);  // Target element 5 does not exist
        });
    }

    @Test
    void testAddAfterThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> {
            list.addAfter(1, 1);  // List is empty
        });
    }

    @Test
    void testAddToFrontThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);
        });
    }

    @Test
    void testAddToRearThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToRear(null);
        });
    }

    @Test
    void testAddAfterThrowsIllegalArgumentException() {
        list.addToRear(1);

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAfter(null, 1);
        });
    }

    @Test
    void testAddAfterUpdatesTail() throws EmptyCollectionException, ElementNotFoundException {
        list.addToRear(1);  // List is [1]
        list.addToRear(2);  // List is [1, 2]

        // Add 3 after 2 (which is the tail), so 3 should become the new tail
        list.addAfter(3, 2);  // List should be [1, 2, 3]

        assertEquals(3, list.size());
        assertEquals(1, list.first());
        assertEquals(3, list.last());  // Verify that the tail is now 3

        Iterator<Integer> iterator = list.iterator();
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
    }

    @Test
    void testIteratorRemoveWithMixedAddMethods_DoubleLinked() throws EmptyCollectionException, ElementNotFoundException {

        // Add elements using different add methods
        list.addToFront(1);  // List: [1]
        list.addToRear(3);   // List: [1, 3]
        list.addAfter(2, 1); // List: [1, 2, 3]

        // Verify list structure
        assertEquals(3, list.size());
        assertEquals("[1, 2, 3]", list.toString());

        // Use iterator to traverse and remove elements
        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next()); // First element is 1
        iterator.remove();                // Remove 1 -> List: [2, 3]

        assertEquals(2, iterator.next()); // Second element is 2
        iterator.remove();                // Remove 2 -> List: [3]

        assertEquals(3, iterator.next()); // Last element is 3
        iterator.remove();                // Remove 3 -> List: []

        // After all removals, list should be empty
        assertTrue(list.isEmpty());
        assertEquals("[]", list.toString());
    }

    @Test
    void testIteratorRemoveAfterNext() throws EmptyCollectionException {
        list.addToFront(1); // List: [1]
        list.addToRear(2);  // List: [1, 2]
        list.addToRear(3);  // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());
        iterator.remove();  // Remove 1 -> List: [2, 3]

        assertEquals(2, iterator.next());
        iterator.remove();  // Remove 2 -> List: [3]

        assertEquals(3, iterator.next());
        iterator.remove();  // Remove 3 -> List: []

        assertTrue(list.isEmpty());
    }

    @Test
    void testIteratorRemoveWithoutNext() {
        DoubleLinkedUnorderedList<Integer> list = new DoubleLinkedUnorderedList<>();
        list.addToFront(1); // List: [1]

        Iterator<Integer> iterator = list.iterator();

        // Attempting to call remove before calling next should throw an exception
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void testIteratorRemoveFirstElement() throws EmptyCollectionException {
        DoubleLinkedUnorderedList<Integer> list = new DoubleLinkedUnorderedList<>();
        list.addToFront(1); // List: [1]
        list.addToRear(2);  // List: [1, 2]
        list.addToRear(3);  // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());  // First element
        iterator.remove();  // Remove first element -> List: [2, 3]

        assertEquals("[2, 3]", list.toString());
    }

    @Test
    void testIteratorRemoveMiddleElement() throws EmptyCollectionException {
        DoubleLinkedUnorderedList<Integer> list = new DoubleLinkedUnorderedList<>();
        list.addToFront(1); // List: [1]
        list.addToRear(2);  // List: [1, 2]
        list.addToRear(3);  // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();

        iterator.next();  // Skip 1
        assertEquals(2, iterator.next());  // Middle element
        iterator.remove();  // Remove middle element -> List: [1, 3]

        assertEquals("[1, 3]", list.toString());
    }

    @Test
    void testIteratorRemoveLastElement() throws EmptyCollectionException {
        DoubleLinkedUnorderedList<Integer> list = new DoubleLinkedUnorderedList<>();
        list.addToFront(1); // List: [1]
        list.addToRear(2);  // List: [1, 2]
        list.addToRear(3);  // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();

        iterator.next();  // Skip 1
        iterator.next();  // Skip 2
        assertEquals(3, iterator.next());  // Last element
        iterator.remove();  // Remove last element -> List: [1, 2]

        assertEquals("[1, 2]", list.toString());
    }

    @Test
    void testIteratorRemoveWithConcurrentModification() {
        DoubleLinkedUnorderedList<Integer> list = new DoubleLinkedUnorderedList<>();
        list.addToFront(1); // List: [1]
        list.addToRear(2);  // List: [1, 2]
        list.addToRear(3);  // List: [1, 2, 3]

        Iterator<Integer> iterator = list.iterator();

        iterator.next();  // Move iterator to the first element

        // Modify list directly to cause ConcurrentModificationException
        list.addToFront(0); // List: [0, 1, 2, 3]

        // Calling remove should now trigger ConcurrentModificationException
        assertThrows(ConcurrentModificationException.class, () -> iterator.remove());
    }

    @Test
    void testIteratorRemoveWhenEmpty() {
        Iterator<Integer> iterator = list.iterator();

        // Attempting to remove when the list is empty
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void testIteratorRemoveWhenCursorIsNull() throws EmptyCollectionException {
        DoubleLinkedUnorderedList<Integer> list = new DoubleLinkedUnorderedList<>();
        list.addToFront(1); // List: [1]
        list.addToRear(2);  // List: [1, 2]
        list.addToRear(3);  // List: [1, 2, 3]
        list.addToRear(4);  // List: [1, 2, 3, 4]

        Iterator<Integer> iterator = list.iterator(); //cursor at 1

        iterator.next();  // cursor to 2
        iterator.next();  // cursor to 3

        iterator.remove(); //removes 2

        assertEquals(3, iterator.next());  // cursor to 4

        // Now, the cursor is null (end of the list)
        iterator.remove();  // Remove last element (3) -> List: [1, 2]

        assertEquals("[1, 4]", list.toString());
    }

}
