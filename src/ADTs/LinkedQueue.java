package ADTs;

import Exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int size;

    public LinkedQueue() {
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

        LinearNode<T> newNode = new LinearNode<>(element);

        if (this.size == 0) {
            this.front = newNode;
        } else {
            this.rear.setNext(newNode);
        }

        this.rear = newNode;

        this.size++;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException("The queue is currently empty");
        }

        return this.front.getElement();
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException("The queue is currently empty");
        }

        LinearNode<T> nodeToRemove = this.front;

        this.front = this.front.getNext();

        nodeToRemove.setNext(null);//desnecessario

        this.size--;

        return nodeToRemove.getElement();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        if (this.size > 0) {
            LinearNode<T> current = this.front;

            while (current.getNext() != null) {
                str.append(current.getElement()).append(", ");

                current = current.getNext();
            }

            str.append(current.getElement());
        }

        str.append("]");

        return str.toString();
    }
}
