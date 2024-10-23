package Tests;

import static org.junit.jupiter.api.Assertions.*;

import ADTs.LinearNode;
import ADTs.SingleLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SingleLinkedListRecursTest {

    private SingleLinkedList<String> list;

    @BeforeEach
    public void setUp() {
        list = new SingleLinkedList<>();
    }

    @Test
    public void testPrintSinglyLinkedListRecurs_EmptyList() {
        // Test the empty list case
        String result = list.printSinglyLinkedListRecurs();
        assertEquals("Empty list.", result);
    }

    @Test
    public void testPrintSinglyLinkedListRecurs_NonEmptyList() {
        // Add elements to the list
        list.addFront("A");
        list.addFront("B");
        list.addFront("C");

        // Test the recursive print for a non-empty list
        String result = list.printSinglyLinkedListRecurs();
        assertEquals("CBA", result);  // List is printed from head to tail, so "CBA"
    }

    @Test
    public void testPrintSinglyLinkedListRecurs_WithCustomNode_EmptyStart() {
        // Pass a null node (empty start)
        LinearNode<String> customNode = null;
        String result = list.printSinglyLinkedListRecurs(customNode);

        // Since the node is null, it should return an empty string
        assertEquals("", result);
    }

    @Test
    public void testPrintSinglyLinkedListRecurs_WithCustomNode_NonEmptyStart() {
        // Create custom nodes
        LinearNode<String> nodeA = new LinearNode<>("A");
        LinearNode<String> nodeB = new LinearNode<>("B");
        LinearNode<String> nodeC = new LinearNode<>("C");
        nodeA.setNext(nodeB);
        nodeB.setNext(nodeC);

        // Test the recursive method starting with nodeA
        String result = list.printSinglyLinkedListRecurs(nodeA);

        // Verify the correct order of elements
        assertEquals("ABC", result);  // Should return "ABC" as nodeA is the head
    }
}
