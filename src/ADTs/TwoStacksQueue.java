package ADTs;

import Exceptions.EmptyCollectionException;

public class TwoStacksQueue<T> implements QueueADT<T> {

    private StackADT<T> enqueue;
    private StackADT<T> dequeue;
    private int size;

    public TwoStacksQueue() {
        this.enqueue = new ArrayStack<>();
        this.dequeue = new ArrayStack<>();
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void enqueue(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter is null.");
        }

        this.enqueue.push(element);

        this.size++;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException("The queue is currently empty");
        }

        switchAndInvertStacks(enqueue, dequeue);

        T first = dequeue.peek();

        switchAndInvertStacks(dequeue, enqueue);

        return first;
    }

    @Override
    public T dequeue() throws EmptyCollectionException { //O(n)
        if (this.size == 0) {
            throw new EmptyCollectionException("The queue is currently empty");
        }

        switchAndInvertStacks(enqueue, dequeue);

        T toRemove = dequeue.pop();

        this.size--;

        switchAndInvertStacks(dequeue, enqueue);

        return toRemove;
    }

    @Override
    public String toString() {
        return this.enqueue.toString();
    }

    private void switchAndInvertStacks(StackADT<T> from, StackADT<T> to) throws EmptyCollectionException {
        for (int i = 0; i < this.size; i++) {
            to.push(from.pop());
        }
    }
}
