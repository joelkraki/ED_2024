package Tests;

import ADTs.LinkedQueue;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {

    private LinkedQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new LinkedQueue<>();
    }

    @Test
    void testQueueIsEmptyInitially() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testEnqueueElement() {
        queue.enqueue(10);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
    }

    @Test
    void testEnqueueMultipleElements() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size());
        assertEquals("[1, 2, 3]", queue.toString());
    }

    @Test
    void testDequeueElement() throws EmptyCollectionException {
        queue.enqueue(5);
        int dequeuedElement = queue.dequeue();
        assertEquals(5, dequeuedElement);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testDequeueMultipleElements() throws EmptyCollectionException {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(10, queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals(20, queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals(30, queue.dequeue());
        assertEquals(0, queue.size());
        assertThrows(EmptyCollectionException.class, () -> queue.dequeue());
    }

    @Test
    void testFirstElement() throws EmptyCollectionException {
        queue.enqueue(100);
        assertEquals(100, queue.first());
        queue.enqueue(200);
        assertEquals(100, queue.first());
    }

    @Test
    void testDequeueFromEmptyQueue() {
        assertThrows(EmptyCollectionException.class, () -> queue.dequeue());
    }

    @Test
    void testFirstFromEmptyQueue() {
        assertThrows(EmptyCollectionException.class, () -> queue.first());
    }

    @Test
    void testEnqueueNullElement() {
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
    }
}
