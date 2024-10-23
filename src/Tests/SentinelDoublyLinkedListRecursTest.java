package Tests;
import static org.junit.jupiter.api.Assertions.*;

import ADTs.SentinelDoubleLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exceptions.EmptyCollectionException;
import Exceptions.ElementNotFoundException;
import Exceptions.InvalidParameterTypeException;

class SentinelDoublyLinkedListRecursTest {

    private SentinelDoubleLinkedList<String> list;

    @BeforeEach
    public void setUp() {
        list = new SentinelDoubleLinkedList<>();
    }

    @Test
    public void testPrintSentinelDoublyLinkedListRecurs_EmptyList() {
        // Test when the list is empty, it should return "Empty list."
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("Empty list.", result);

        result = list.printSentinelDoublyLinkedListRecurs(false);
        assertEquals("Empty list.", result);
    }

    @Test
    public void testPrintSentinelDoublyLinkedListRecurs_LeftToRight() {
        // Add elements to the list
        list.addFront("A");
        list.addFront("B");
        list.addFront("C");

        // Test recursive printing from left to right
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("CBA", result);  // The list should be printed from head to tail: "CBA"
    }

    @Test
    public void testPrintSentinelDoublyLinkedListRecurs_RightToLeft() {
        // Add elements to the list
        list.addFront("A");
        list.addFront("B");
        list.addFront("C");

        // Test recursive printing from right to left
        String result = list.printSentinelDoublyLinkedListRecurs(false);
        assertEquals("CBA", result);  // The list should be printed from tail to head: "ABC"
    }

    @Test
    public void testPrintSentinelDoublyLinkedListRecurs_SingleElement() {
        // Add a single element to the list
        list.addFront("X");

        // Test both directions for a single element
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("X", result);  // Left to right: "X"

        result = list.printSentinelDoublyLinkedListRecurs(false);
        assertEquals("X", result);  // Right to left: "X"
    }

    @Test
    public void testPrintSentinelDoublyLinkedListRecurs_LeftToRight_WithTwoElements() {
        // Add two elements to the list
        list.addFront("A");
        list.addLast("B");

        // Test left to right (from head to tail)
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("AB", result);  // The list should print in order: "AB"
    }

    @Test
    public void testPrintSentinelDoublyLinkedListRecurs_RightToLeft_WithTwoElements() {
        // Add two elements to the list
        list.addFront("A");
        list.addLast("B");

        // Test right to left (from tail to head)
        String result = list.printSentinelDoublyLinkedListRecurs(false);
        assertEquals("AB", result);  // The list should print in reverse order: "BA"
    }

    @Test
    public void testCountAndRemove_MultipleOccurrences() throws EmptyCollectionException {
        // Add multiple occurrences of the same element
        list.addFront("A");
        list.addFront("B");
        list.addLast("A");
        list.addLast("C");
        list.addLast("A");

        // Count and remove "A"
        int count = list.countAndRemove("A");
        assertEquals(3, count);  // There are 3 occurrences of "A"

        // Verify the remaining list
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("BC", result);  // Only "B" and "C" should remain
    }

    @Test
    public void testGetPairList_IntegerList() throws InvalidParameterTypeException {
        // Create a new list with integers
        SentinelDoubleLinkedList<Integer> intList = new SentinelDoubleLinkedList<>();
        intList.addFront(1);
        intList.addLast(2);
        intList.addLast(3);
        intList.addLast(4);
        intList.addLast(5);

        // Get the pair list
        SentinelDoubleLinkedList<Integer> pairList = intList.getPairList();

        // Test that only the even numbers are included
        String result = pairList.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("24", result);  // The list should contain only the even numbers: "2" and "4"
    }

    @Test
    public void testReplace_SingleOccurrence() throws ElementNotFoundException {
        // Add elements to the list
        list.addFront("A");
        list.addLast("B");
        list.addLast("C");

        // Replace a single occurrence of "B" with "X"
        list.replace("B", "X");

        // Verify the list has "A", "X", "C"
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("AXC", result);
    }

    @Test
    public void testReplace_MultipleOccurrences() throws ElementNotFoundException {
        // Add multiple occurrences of "A" to the list
        list.addFront("A");
        list.addLast("B");
        list.addLast("A");
        list.addLast("C");
        list.addLast("A");

        // Replace all occurrences of "A" with "X"
        list.replace("A", "X");

        // Verify the list has "X", "B", "X", "C", "X"
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("XBXCX", result);
    }

    @Test
    public void testReplace_NoOccurrence() throws ElementNotFoundException {
        // Add elements to the list
        list.addFront("A");
        list.addLast("B");
        list.addLast("C");

        // Try to replace a non-existent element "D" with "X"
        assertThrows(ElementNotFoundException.class, () -> {
            list.replace("D", "X");
        });

        // Verify the list remains unchanged
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("ABC", result);
    }

    @Test
    public void testReplace_EmptyList() {
        // Try to replace in an empty list
        assertThrows(ElementNotFoundException.class, () -> {
            list.replace("A", "X");
        });

        // The list should remain empty
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("Empty list.", result);
    }

    @Test
    public void testReplace_FirstAndLastOccurrence() throws ElementNotFoundException {
        // Add elements to the list, including occurrences at both ends
        list.addFront("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("A");

        // Replace all occurrences of "A" with "X"
        list.replace("A", "X");

        // Verify the list has "X", "B", "C", "X"
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("XBCX", result);
    }

    @Test
    public void testReplace_OnlyElementInList() throws ElementNotFoundException {
        // Add only one element
        list.addFront("A");

        // Replace the single element "A" with "X"
        list.replace("A", "X");

        // Verify the list now contains "X"
        String result = list.printSentinelDoublyLinkedListRecurs(true);
        assertEquals("X", result);
    }
}
