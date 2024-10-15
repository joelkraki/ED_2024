package Tests;

import ADTs.ArrayStack;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayStackTest {

    private ArrayStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new ArrayStack<>();
    }

    @Test
    public void testPushAndPop() throws EmptyCollectionException {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void testPopOnEmptyStack() {
        assertThrows(EmptyCollectionException.class, () -> stack.pop());
    }

    @Test
    public void testPeek() throws EmptyCollectionException {
        stack.push(5);
        assertEquals(5, stack.peek());

        // Ensure the element is not removed
        assertEquals(5, stack.peek());
    }

    @Test
    public void testPeekOnEmptyStack() {
        assertThrows(EmptyCollectionException.class, () -> stack.peek());
    }

    @Test
    public void testIsEmpty() throws EmptyCollectionException {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testSize() throws EmptyCollectionException {
        assertEquals(0, stack.size());
        stack.push(10);
        assertEquals(1, stack.size());
        stack.push(20);
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
    }

    @Test
    public void testPushNull() {
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
    }

    @Test
    public void testCapacityGrowth() throws EmptyCollectionException {
        // Push enough elements to trigger capacity growth
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }

        assertEquals(100, stack.size());

        // Ensure all elements are still in the correct order (LIFO)
        for (int i = 99; i >= 0; i--) {
            assertEquals(i, stack.pop());
        }
    }
}