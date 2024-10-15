package Tests;

import ADTs.CircularArrayQueue;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircularArrayQueueTest {

    private CircularArrayQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new CircularArrayQueue<>();
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

    @Test
    void testQueueExpandsCapacity() {
        CircularArrayQueue<Integer> smallQueue = new CircularArrayQueue<>(2);
        smallQueue.enqueue(1);
        smallQueue.enqueue(2);
        assertEquals(2, smallQueue.size());

        smallQueue.enqueue(3); // Should trigger capacity expansion
        assertEquals(3, smallQueue.size());
        assertEquals("[1, 2, 3]", smallQueue.toString());
    }

    @Test
    void testCircularBehavior() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue();
        queue.enqueue(4);
        assertEquals(3, queue.size());
        assertEquals("[2, 3, 4]", queue.toString());
    }
}
