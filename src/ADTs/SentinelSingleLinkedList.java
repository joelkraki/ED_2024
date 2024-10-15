package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class SentinelSingleLinkedList<T> {

    private LinearNode<T> head;
    private LinearNode<T> tail;
    private int size;

    public SentinelSingleLinkedList() {
        this.head = new LinearNode<>();
        this.tail = new LinearNode<>();

        this.head.setNext(this.tail);
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void addFront(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }

        LinearNode<T> newNode = new LinearNode<>(element);

        newNode.setNext(this.head.getNext());
        this.head.setNext(newNode);
        this.size++;
    }

    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null.");
        }

        if (this.size == 0) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        boolean found = false;
        LinearNode<T> current = this.head.getNext();
        LinearNode<T> previous = this.head;

        while (current != this.tail && !found) {
            if (current.getElement().equals(element)) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Parameter element not found in Linked List");
        }

        previous.setNext(current.getNext());
        current.setNext(null); //desnecessario

        this.size--;
        return current.getElement();
    }

    public T removeFront() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        LinearNode<T> currentHead = this.head.getNext();

        this.head.setNext(currentHead.getNext());

        currentHead.setNext(null);//desnecessario

        this.size--;

        return currentHead.getElement();
    }

    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return this.head.getNext().getElement();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        LinearNode<T> current = this.head.getNext();

        while (current != this.tail) {
            str.append(current.getElement().toString());

            current = current.getNext();

            if (current != this.tail) {
                str.append(", ");
            }
        }

        str.append("]");

        return str.toString();
    }
}
