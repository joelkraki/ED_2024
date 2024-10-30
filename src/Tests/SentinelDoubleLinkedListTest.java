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

    @Test
    void testLinearSearch_TargetFound() throws EmptyCollectionException {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertTrue(list.linearSearch(2), "Expected to find target 2 in the list.");
    }

    @Test
    void testLinearSearch_TargetNotFound() throws EmptyCollectionException {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertFalse(list.linearSearch(4), "Expected not to find target 4 in the list.");
    }

    @Test
    void testLinearSearch_EmptyList() throws EmptyCollectionException {
        assertThrows(EmptyCollectionException.class, () -> list.linearSearch(1));
    }

    @Test
    void testSelectionSort_EmptyList() {
        // Given an empty list
        assertThrows(EmptyCollectionException.class, () -> list.selectionSort());

    }

    @Test
    void testSelectionSort_SingleElement() throws EmptyCollectionException {
        // Given a list with one element
        list.addLast(5);

        list.selectionSort();

        // Expect the list to remain unchanged as it only contains one element
        assertEquals("[5]", list.toString(), "Single element list should remain unchanged after sorting");
    }

    @Test
    void testSelectionSort_SortedList() throws EmptyCollectionException {
        // Given a list that is already sorted
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        list.selectionSort();

        // Expect the list to remain in the same sorted order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Already sorted list should remain unchanged after sorting");
    }

    @Test
    void testSelectionSort_ReverseOrderList() throws EmptyCollectionException {
        // Given a list sorted in reverse order
        list.addLast(4);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.selectionSort();

        // Expect the list to be sorted in ascending order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Reverse ordered list should be sorted in ascending order");
    }

    @Test
    void testSelectionSort_UnsortedList() throws EmptyCollectionException {
        // Given an unsorted list with mixed elements
        list.addLast(3);
        list.addLast(1);
        list.addLast(4);
        list.addLast(2);

        list.selectionSort();

        // Expect the list to be sorted in ascending order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Unsorted list should be sorted in ascending order");
    }

    @Test
    void testSelectionSort_DuplicateElements() throws EmptyCollectionException {
        // Given a list with duplicate elements
        list.addLast(2);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.selectionSort();

        // Expect duplicates to be placed correctly in sorted order
        assertEquals("[1, 2, 2, 3]", list.toString(), "List with duplicates should be sorted with duplicates in correct positions");
    }

    @Test
    void testInsertionSort_EmptyList() {
        // Given an empty list
        assertThrows(EmptyCollectionException.class, () -> list.insertionSort(),
                "Sorting an empty list should throw an EmptyCollectionException");
    }

    @Test
    void testInsertionSort_SingleElement() throws EmptyCollectionException {
        // Given a list with one element
        list.addLast(5);

        list.insertionSort();

        // Expect the list to remain unchanged as it only contains one element
        assertEquals("[5]", list.toString(), "Single element list should remain unchanged after sorting");
    }

    @Test
    void testInsertionSort_SortedList() throws EmptyCollectionException {
        // Given a list that is already sorted
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        list.insertionSort();

        // Expect the list to remain in the same sorted order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Already sorted list should remain unchanged after sorting");
    }

    @Test
    void testInsertionSort_ReverseOrderList() throws EmptyCollectionException {
        // Given a list sorted in reverse order
        list.addLast(4);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.insertionSort();

        // Expect the list to be sorted in ascending order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Reverse ordered list should be sorted in ascending order");
    }

    @Test
    void testInsertionSort_UnsortedList() throws EmptyCollectionException {
        // Given an unsorted list with mixed elements
        list.addLast(3);
        list.addLast(1);
        list.addLast(4);
        list.addLast(2);

        list.insertionSort();

        // Expect the list to be sorted in ascending order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Unsorted list should be sorted in ascending order");
    }

    @Test
    void testInsertionSort_DuplicateElements() throws EmptyCollectionException {
        // Given a list with duplicate elements
        list.addLast(2);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.insertionSort();

        // Expect duplicates to be placed correctly in sorted order
        assertEquals("[1, 2, 2, 3]", list.toString(), "List with duplicates should be sorted with duplicates in correct positions");
    }

    @Test
    void testBubbleSort_EmptyList() {
        // Given an empty list
        assertThrows(EmptyCollectionException.class, () -> list.bubbleSort(),
                "Sorting an empty list should throw an EmptyCollectionException");
    }

    @Test
    void testBubbleSort_SingleElement() throws EmptyCollectionException {
        // Given a list with one element
        list.addLast(5);

        list.bubbleSort();

        // Expect the list to remain unchanged as it only contains one element
        assertEquals("[5]", list.toString(), "Single element list should remain unchanged after sorting");
    }

    @Test
    void testBubbleSort_SortedList() throws EmptyCollectionException {
        // Given a list that is already sorted
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        list.bubbleSort();

        // Expect the list to remain in the same sorted order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Already sorted list should remain unchanged after sorting");
    }

    @Test
    void testBubbleSort_ReverseOrderList() throws EmptyCollectionException {
        // Given a list sorted in reverse order
        list.addLast(4);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.bubbleSort();

        // Expect the list to be sorted in ascending order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Reverse ordered list should be sorted in ascending order");
    }

    @Test
    void testBubbleSort_UnsortedList() throws EmptyCollectionException {
        // Given an unsorted list with mixed elements
        list.addLast(3);
        list.addLast(1);
        list.addLast(4);
        list.addLast(2);

        list.bubbleSort();

        // Expect the list to be sorted in ascending order
        assertEquals("[1, 2, 3, 4]", list.toString(), "Unsorted list should be sorted in ascending order");
    }

    @Test
    void testBubbleSort_DuplicateElements() throws EmptyCollectionException {
        // Given a list with duplicate elements
        list.addLast(2);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.bubbleSort();

        // Expect duplicates to be placed correctly in sorted order
        assertEquals("[1, 2, 2, 3]", list.toString(), "List with duplicates should be sorted with duplicates in correct positions");
    }

    @Test
    void testQuickSortOnEmptyList() {
        assertThrows(EmptyCollectionException.class, () -> list.quickSort());
        assertEquals(0, list.size());
    }

    @Test
    void testQuickSortOnSingleElementList() throws EmptyCollectionException {
        list.addLast(5);
        list.quickSort();
        assertEquals(1, list.size());
        assertEquals("[5]", list.toString());
    }

    @Test
    void testQuickSortOnAlreadySortedTwoElementList() throws EmptyCollectionException {
        list.addLast(2);
        list.addLast(5);
        list.quickSort();
        assertEquals(2, list.size());
        assertEquals("[2, 5]", list.toString());
    }

    @Test
    void testQuickSortOnUnsortedTwoElementList() throws EmptyCollectionException {
        list.addLast(5);
        list.addLast(2);
        list.quickSort();
        assertEquals(2, list.size());
        assertEquals("[2, 5]", list.toString());
    }

    @Test
    void testQuickSortOnMultipleElements() throws EmptyCollectionException {
        list.addLast(3);
        list.addLast(8);
        list.addLast(5);
        list.addLast(1);
        list.addLast(9);
        list.quickSort();
        assertEquals(5, list.size());
        assertEquals("[1, 3, 5, 8, 9]", list.toString());
    }

    @Test
    void testQuickSortWithDuplicateElements() throws EmptyCollectionException {
        list.addLast(3);
        list.addLast(3);
        list.addLast(5);
        list.addLast(1);
        list.addLast(9);
        list.addLast(1);
        list.quickSort();
        assertEquals(6, list.size());
        assertEquals("[1, 1, 3, 3, 5, 9]", list.toString());
    }

    @Test
    void testQuickSortWithNegativeNumbers() throws EmptyCollectionException {
        list.addLast(-3);
        list.addLast(0);
        list.addLast(-5);
        list.addLast(8);
        list.addLast(-1);
        list.quickSort();
        assertEquals(5, list.size());
        assertEquals("[-5, -3, -1, 0, 8]", list.toString());
    }

    @Test
    void testQuickSortOnDescendingOrderList() throws EmptyCollectionException {
        list.addLast(9);
        list.addLast(7);
        list.addLast(5);
        list.addLast(3);
        list.addLast(1);
        list.quickSort();
        assertEquals(5, list.size());
        assertEquals("[1, 3, 5, 7, 9]", list.toString());
    }

    @Test
    void testQuickSortPerformanceOnLargeList() throws EmptyCollectionException {
        for (int i = 100; i > 0; i--) {
            list.addLast(i);
        }
        list.quickSort();
        assertEquals(100, list.size());

        for (int i = 1; i <= 100; i++) {
            assertEquals(i, list.removeFront());
        }
    }



}
