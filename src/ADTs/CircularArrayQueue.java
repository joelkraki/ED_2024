package ADTs;

import Exceptions.EmptyCollectionException;

public class CircularArrayQueue<T> implements QueueADT<T> {

    public static final int DEFAULT_CAPACITY = 1;
    public static final int GROWTH_FACTOR = 2;

    private T[] array;
    private int size;
    private int front;
    private int rear;

    public CircularArrayQueue(int capacity) {
        this.array = (T[]) new Object[capacity];
        this.size = 0;
    }

    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
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

        if (array.length == this.size) {
            expandCapacity();
        }

        array[rear] = element;

        rear = (rear + 1) % array.length;

        this.size++;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException("The queue is currently empty");
        }

        return array[this.front];
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException("The queue is currently empty");
        }

        T toRemove = array[front];

        array[front] = null; //aqui ja e necessário

        front = (front + 1) % array.length;

        this.size--;

        return toRemove;
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return "[]";
        }

        StringBuilder str = new StringBuilder("[");

        int i;
        for (i = 0; i < this.size - 1; i++) {
            str.append(array[(front + i) % array.length]).append(", ");
        }

        str.append(array[(front + i) % array.length]).append("]");

        return str.toString();
    }

    private void expandCapacity() {
        T[] newArray = (T[]) new Object[this.array.length * GROWTH_FACTOR];

        for (int i = 0; i < this.size; i++) {
            newArray[i] = array[(this.front + i) % this.array.length];
        }

        this.array = newArray;
        this.front = 0;
        this.rear = this.size;
    }
}
