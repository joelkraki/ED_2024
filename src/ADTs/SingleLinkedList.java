package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class SingleLinkedList<T> {

    private LinearNode<T> head;
    private LinearNode<T> tail;
    private int size;

    public SingleLinkedList() {
        this.size = 0;
    }

    public void addFront(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }

        LinearNode<T> newNode = new LinearNode<>(element);

        newNode.setNext(head);

        this.head = newNode;

        if (isEmpty()) {
            this.tail = this.head;
        }

        this.size++;
    }

    public void addLast(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }

        LinearNode<T> newNode = new LinearNode<>(element);

        if (isEmpty()) {
            this.head = newNode;
        } else {
            this.tail.setNext(newNode);
        }

        this.tail = newNode;

        this.size++;
    }

    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (element == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }

        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        boolean found = false;
        LinearNode<T> current = this.head;
        LinearNode<T> previous = null;

        while (current != null && !found) {
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

        if (this.size == 1) { //apenas 1 nó
            this.head = null;
            this.tail = null;
        } else if (current.equals(head)) { // o nó a eliminar corresponde ao head
            this.head = current.getNext();
            current.setNext(null);
        } else if (current.equals(tail)) {// o nó a eliminar corresponde à tail
            this.tail = previous;
            previous.setNext(null);
        } else {
            previous.setNext(current.getNext());// o nó a eliminar está no meio da lista ligada
            current.setNext(null);
        }

        this.size--;
        return current.getElement();
    }

    public T removeFront() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        LinearNode<T> currentHead = this.head;

        this.head = this.head.getNext();

        currentHead.setNext(null);

        this.size--;

        if (isEmpty()) {
            this.tail = null;
        }

        return currentHead.getElement();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        return this.head.getElement();
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("Empty list.");
        }

        LinearNode<T> current = this.head;

        while (current != null) {
            System.out.println(current.getElement());
            current = current.getNext();
        }
    }
}
