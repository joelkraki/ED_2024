package Tests;
import static org.junit.jupiter.api.Assertions.*;

import ADTs.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class ArrayUnorderedListTest {

    private ArrayUnorderedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new ArrayUnorderedList<>();
    }

    @Test
    void testAddToFrontInEmptyList() throws EmptyCollectionException {
        list.addToFront(1);
        assertEquals(1, list.size());
        assertEquals(1, list.first());
    }

    @Test
    void testAddToFrontShiftsElements() throws EmptyCollectionException {
        list.addToRear(2);  // Add to rear to make sure it's not empty
        list.addToRear(3);
        list.addToFront(1); // Add to front

        assertEquals(3, list.size());
        assertEquals(1, list.first()); // Ensure the element was added at the front
    }

    @Test
    void testAddToRear() throws EmptyCollectionException {
        list.addToRear(1);
        assertEquals(1, list.size());
        assertEquals(1, list.last()); // Ensure it's added to the rear
    }

    @Test
    void testAddToRearWithExistingElements() throws EmptyCollectionException {
        list.addToFront(1);
        list.addToFront(2); // List is now [2, 1]
        list.addToRear(3);  // Add to rear

        assertEquals(3, list.size());
        assertEquals(3, list.last()); // Ensure it was added to the rear
    }

    @Test
    void testAddAfter() throws EmptyCollectionException, ElementNotFoundException {
        list.addToRear(1);  // List is [1]
        list.addToRear(2);  // List is [1, 2]
        list.addToRear(4);  // List is [1, 2, 4]

        list.addAfter(3, 2);  // Add 3 after 2, list should become [1, 2, 3, 4]

        assertEquals(4, list.size());           // Ensure the size is correct
        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());  // First element should be 1
        assertEquals(2, iterator.next());  // Second element should be 2
        assertEquals(3, iterator.next());  // Third element should be 3
        assertEquals(4, iterator.next());  // Fourth element should be 4
    }

    @Test
    void testAddAfterThrowsElementNotFoundException() {
        list.addToRear(1);
        list.addToRear(2);

        assertThrows(ElementNotFoundException.class, () -> {
            list.addAfter(3, 5);  // Trying to add after a non-existing element
        });
    }

    @Test
    void testAddAfterThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> {
            list.addAfter(1, 2);  // AddAfter on empty list should throw exception
        });
    }

    @Test
    void testAddToFrontThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);  // Adding null element should throw exception
        });
    }

    @Test
    void testAddToRearThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToRear(null);  // Adding null element should throw exception
        });
    }

    @Test
    void testAddAfterThrowsIllegalArgumentExceptionForNullElementOrTarget() {
        list.addToRear(1);
        assertThrows(IllegalArgumentException.class, () -> {
            list.addAfter(null, 1);  // Adding a null element
        });

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAfter(2, null);  // Adding after a null target
        });
    }

    @Test
    void testExpandCapacityOnAddToFront() {
        ArrayUnorderedList<Integer> smallList = new ArrayUnorderedList<>(2);
        smallList.addToFront(1);
        smallList.addToFront(2);  // Should be able to add up to initial capacity
        smallList.addToFront(3);  // Trigger capacity expansion

        assertEquals(3, smallList.size());
    }

    @Test
    void testExpandCapacityOnAddToRear() {
        ArrayUnorderedList<Integer> smallList = new ArrayUnorderedList<>(2);
        smallList.addToRear(1);
        smallList.addToRear(2);   // Should be able to add up to initial capacity
        smallList.addToRear(3);   // Trigger capacity expansion

        assertEquals(3, smallList.size());
    }

    @Test
    void testExpandCapacityOnAddAfter() throws EmptyCollectionException, ElementNotFoundException {
        ArrayUnorderedList<Integer> smallList = new ArrayUnorderedList<>(2);
        smallList.addToRear(1);
        smallList.addToRear(2);   // Add up to capacity

        smallList.addAfter(3, 1); // Trigger capacity expansion by adding after element

        assertEquals(3, smallList.size());
    }

    @Test
    void testIteratorRemoveWithMixedAddMethods_Array() throws EmptyCollectionException, ElementNotFoundException {
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



}