package Tests;

import ADTs.LinkedStack;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedStackTest {

    private LinkedStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new LinkedStack<>();
    }

    @Test
    public void testPushAndPop() throws EmptyCollectionException {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, (int) stack.pop());
        assertEquals(2, (int) stack.pop());
        assertEquals(1, (int) stack.pop());
    }

    @Test
    public void testPopOnEmptyStack() {
        try {
            stack.pop();
            fail("Expected an EmptyStackException to be thrown");
        } catch (EmptyCollectionException e) {
            assertEquals("The stack is currently empty", e.getMessage());
        }
    }

    @Test
    public void testPeek() throws EmptyCollectionException {
        stack.push(5);
        assertEquals(5, (int) stack.peek());

        // Check that peek does not remove the element
        assertEquals(5, (int) stack.peek());
    }

    @Test
    public void testPeekOnEmptyStack() {
        try {
            stack.peek();
            fail("Expected an EmptyStackException to be thrown");
        } catch (EmptyCollectionException e) {
            assertEquals("The stack is currently empty", e.getMessage());
        }
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
        assertThrows(IllegalArgumentException.class, () -> {
            stack.push(null);
        });
    }
}