package ADTs;

import Exceptions.EmptyCollectionException;

public class JoinOrderedQueues {

    public static <T extends Comparable<T>> QueueADT<T> joinOrderedQueues(QueueADT<T> queue1, QueueADT<T> queue2) throws EmptyCollectionException {
        if (queue1 == null || queue2 == null) {
            throw new IllegalArgumentException("Parameter is null.");
        }

        QueueADT<T> finalQueue = new CircularArrayQueue<>();

        // While both queues have elements
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            T currentQueue1 = queue1.first(); // Peek the front of queue1
            T currentQueue2 = queue2.first(); // Peek the front of queue2

            // Compare and enqueue the smaller element
            if (currentQueue1.compareTo(currentQueue2) <= 0) {
                finalQueue.enqueue(queue1.dequeue());
            } else {
                finalQueue.enqueue(queue2.dequeue());
            }
        }

        // If there are remaining elements in queue1, enqueue them
        while (!queue1.isEmpty()) {
            finalQueue.enqueue(queue1.dequeue());
        }

        // If there are remaining elements in queue2, enqueue them
        while (!queue2.isEmpty()) {
            finalQueue.enqueue(queue2.dequeue());
        }

        return finalQueue;
    }
}
