package ADTs;

import Exceptions.EmptyCollectionException;

public class WeirdQueueManager {

    private QueueADT<QueueADT<String>> queueOfqueues;

    public WeirdQueueManager() {
        this.queueOfqueues = new CircularArrayQueue<>();
    }

    public void addString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Parameter is null.");
        }

        QueueADT<String> newQueue = new CircularArrayQueue<>();

        newQueue.enqueue(str);

        queueOfqueues.enqueue(newQueue);
    }

    public QueueADT<QueueADT<String>> mergeQueues() throws EmptyCollectionException {
        if (this.queueOfqueues.size() == 0) {
            throw new EmptyCollectionException("Queue is Empty");
        }

        if (this.queueOfqueues.size() == 1) {
            return this.queueOfqueues;
        }

        QueueADT<String> front = queueOfqueues.dequeue();
        QueueADT<String> second = queueOfqueues.dequeue();

        if (front.size() == 1 && !this.queueOfqueues.isEmpty()) {

            while (!second.isEmpty()) {
                front.enqueue(second.dequeue());
            }

            queueOfqueues.enqueue(front);
        } else {

            while (!front.isEmpty()) {
                second.enqueue(front.dequeue());
            }

            queueOfqueues.enqueue(second);
        }

        return mergeQueues();
    }

    @Override
    public String toString() {
        return this.queueOfqueues.toString();
    }
}
