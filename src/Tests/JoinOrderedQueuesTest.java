package Tests;

import ADTs.*;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JoinOrderedQueuesTest {

    private QueueADT<Integer> queue1;
    private QueueADT<Integer> queue2;

    @BeforeEach
    public void setUp() {
        queue1 = new CircularArrayQueue<>();
        queue2 = new CircularArrayQueue<>();
    }

    @Test
    public void testJoinOrderedQueues_BothEmpty() throws EmptyCollectionException {
        // Test when both queues are empty
        QueueADT<Integer> resultQueue = JoinOrderedQueues.joinOrderedQueues(queue1, queue2);
        assertTrue(resultQueue.isEmpty(), "Resulting queue should be empty when both queues are empty");
    }

    @Test
    public void testJoinOrderedQueues_OneEmptyQueue() throws EmptyCollectionException {
        // Test when one queue is empty and the other has elements
        queue1.enqueue(1);
        queue1.enqueue(3);
        queue1.enqueue(5);

        QueueADT<Integer> resultQueue = JoinOrderedQueues.joinOrderedQueues(queue1, queue2);

        assertEquals(3, resultQueue.size(), "Resulting queue should have 3 elements when one queue is empty");
        assertEquals(1, resultQueue.dequeue());
        assertEquals(3, resultQueue.dequeue());
        assertEquals(5, resultQueue.dequeue());
    }

    @Test
    public void testJoinOrderedQueues_BothNonEmpty() throws EmptyCollectionException {
        // Test when both queues have elements
        queue1.enqueue(1);
        queue1.enqueue(3);
        queue1.enqueue(5);

        queue2.enqueue(2);
        queue2.enqueue(4);
        queue2.enqueue(6);

        QueueADT<Integer> resultQueue = JoinOrderedQueues.joinOrderedQueues(queue1, queue2);

        assertEquals(6, resultQueue.size(), "Resulting queue should have 6 elements");
        assertEquals(1, resultQueue.dequeue());
        assertEquals(2, resultQueue.dequeue());
        assertEquals(3, resultQueue.dequeue());
        assertEquals(4, resultQueue.dequeue());
        assertEquals(5, resultQueue.dequeue());
        assertEquals(6, resultQueue.dequeue());
    }

    @Test
    public void testJoinOrderedQueues_QueueWithDuplicates() throws EmptyCollectionException {
        // Test when both queues contain duplicate elements
        queue1.enqueue(1);
        queue1.enqueue(3);
        queue1.enqueue(5);

        queue2.enqueue(1);
        queue2.enqueue(3);
        queue2.enqueue(5);

        QueueADT<Integer> resultQueue = JoinOrderedQueues.joinOrderedQueues(queue1, queue2);

        assertEquals(6, resultQueue.size(), "Resulting queue should have 6 elements");
        assertEquals(1, resultQueue.dequeue());
        assertEquals(1, resultQueue.dequeue());
        assertEquals(3, resultQueue.dequeue());
        assertEquals(3, resultQueue.dequeue());
        assertEquals(5, resultQueue.dequeue());
        assertEquals(5, resultQueue.dequeue());
    }

    @Test
    public void testJoinOrderedQueues_MixedLengthQueues() throws EmptyCollectionException {
        // Test when queues have different lengths
        queue1.enqueue(1);
        queue1.enqueue(3);

        queue2.enqueue(2);
        queue2.enqueue(4);
        queue2.enqueue(6);
        queue2.enqueue(8);

        QueueADT<Integer> resultQueue = JoinOrderedQueues.joinOrderedQueues(queue1, queue2);

        assertEquals(6, resultQueue.size(), "Resulting queue should have 6 elements");
        assertEquals(1, resultQueue.dequeue());
        assertEquals(2, resultQueue.dequeue());
        assertEquals(3, resultQueue.dequeue());
        assertEquals(4, resultQueue.dequeue());
        assertEquals(6, resultQueue.dequeue());
        assertEquals(8, resultQueue.dequeue());
    }

    @Test
    public void testJoinOrderedQueues_EmptyCollectionException() {
        // Test to ensure an exception is thrown when attempting to dequeue from an empty queue
        assertThrows(EmptyCollectionException.class, () -> {
            QueueADT<Integer> resultQueue = JoinOrderedQueues.joinOrderedQueues(queue1, queue2);
            resultQueue.dequeue();  // Should throw an exception
        });
    }
}
