package ADTs;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidParameterTypeException;

import java.util.Arrays;

public class SentinelDoubleLinkedList<T> {

    private DoubleLinearNode<T> head;
    private DoubleLinearNode<T> tail;
    private int size;

    public SentinelDoubleLinkedList() {
        this.head = new DoubleLinearNode<>();
        this.tail = new DoubleLinearNode<>();

        this.head.setNext(tail);
        this.tail.setPrev(head);

        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addFront(T element) {
        addBetween(element, this.head, this.head.getNext());
    }

    public void addLast(T element) {
        addBetween(element, this.tail.getPrev(), this.tail);
    }

    public T removeFront() throws EmptyCollectionException {
        return remove(this.head.getNext());
    }

    public T removeLast() throws EmptyCollectionException {
        return remove(this.tail.getPrev());
    }

    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null.");
        }

        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        DoubleLinearNode<T> node = findNode(element, this.head.getNext());

        return remove(node);
    }

    //viola o encapsulamento - será necessário clonar os elementos??
    // como validar o tamanho do array?? FALTA VALIDAR O TAMANHO DO ARRAY
    public T[] getArray(T[] array, T leftBound, T rightBound) throws ElementNotFoundException {
        if (array == null || leftBound == null || rightBound == null) {
            throw new IllegalArgumentException("Invalid null Parameter.");
        }

        DoubleLinearNode<T> leftNode = findNode(leftBound, this.head.getNext());
        DoubleLinearNode<T> rightNode = findNode(rightBound, leftNode); //encontra o node a começar no leftNode

        int arraySize = 0;

        DoubleLinearNode<T> current = leftNode;

        while (current != rightNode.getNext()) {
            array[arraySize++] = current.getElement();

            current = current.getNext();
        }

        array = Arrays.copyOf(array, arraySize);

        return array;
    }

    public T[] getFullArray(T[] array) throws ElementNotFoundException {
        return getArray(array, this.head.getNext().getElement(), this.tail.getPrev().getElement());
    }

    public T[] getArrayFrom(T[] array, T from) throws ElementNotFoundException {
        return getArray(array, from, this.tail.getPrev().getElement());
    }

    public T[] getArrayTo(T[] array, T to) throws ElementNotFoundException {
        return getArray(array, this.head.getNext().getElement(), to);
    }

    public SentinelDoubleLinkedList<Integer> getPairList() throws InvalidParameterTypeException {
        if (!this.head.getNext().getElement().getClass().equals(Integer.class)) {
            throw new InvalidParameterTypeException("The parameter type must be Integer.");
        }

        SentinelDoubleLinkedList<Integer> pairList = new SentinelDoubleLinkedList<>();

        DoubleLinearNode<Integer> current = (DoubleLinearNode<Integer>) this.head.getNext();

        while (current != this.tail) {
            Integer currentElement = current.getElement();

            if (current.getElement() % 2 == 0) {
                pairList.addLast(currentElement);
            }

            current = current.getNext();
        }

        return pairList;
    }

    public int countAndRemove(T element) throws EmptyCollectionException {
        int count = 0;

        DoubleLinearNode<T> currentNode = this.head.getNext();

        while (currentNode != this.tail) {
            DoubleLinearNode<T> nextNode = currentNode.getNext();

            if (currentNode.getElement().equals(element)) {
                remove(currentNode);
                count++;
            }

            currentNode = nextNode;
        }

        return count;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        DoubleLinearNode<T> current = this.head.getNext();

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

    //privado para garantir encapsulamento
    private void addBetween(T element, DoubleLinearNode<T> predecessor, DoubleLinearNode<T> successor) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);
        newNode.setPrev(predecessor);
        newNode.setNext(successor);

        predecessor.setNext(newNode);
        successor.setPrev(newNode);

        this.size++;
    }

    //privado para garantir encapsulamento
    private T remove(DoubleLinearNode<T> nodeToRemove) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        DoubleLinearNode<T> prevNode = nodeToRemove.getPrev();
        DoubleLinearNode<T> nextNode = nodeToRemove.getNext();

        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);

        nodeToRemove.setNext(null);
        nodeToRemove.setPrev(null);

        this.size--;

        return nodeToRemove.getElement();
    }

    private DoubleLinearNode<T> findNode(T element, DoubleLinearNode<T> start) throws ElementNotFoundException {
        boolean found = false;
        DoubleLinearNode<T> current = start;

        while (current != this.tail && !found) {
            if (current.getElement().equals(element)) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Parameter element not found in Linked List");
        }

        return current;
    }

    public String printSentinelDoublyLinkedListRecurs(boolean leftToRight) {
        if (isEmpty()) {
            return ("Empty list.");
        }

        if (leftToRight) {
            return printSentinelDoublyLinkedListRecursLeftToRight(this.head.getNext());
        }

       return printSentinelDoublyLinkedListRecursRightToLeft(this.tail.getPrev());

    }

    private String printSentinelDoublyLinkedListRecursLeftToRight(DoubleLinearNode<T> start) {
        if (start == this.tail) {
            return "";
        }

        return start.getElement() + printSentinelDoublyLinkedListRecursLeftToRight(start.getNext());
    }

    private String printSentinelDoublyLinkedListRecursRightToLeft(DoubleLinearNode<T> start) {
        if (start == this.head) {
            return "";
        }

        return printSentinelDoublyLinkedListRecursRightToLeft(start.getPrev()) + start.getElement();
    }

    public void replace(T existingElement, T newElement) throws ElementNotFoundException {
        if (this.size == 0) {
            throw new ElementNotFoundException();
        }

        replace(existingElement, newElement, this.head.getNext());
    }

    private void replace(T existingElement, T newElement, DoubleLinearNode<T> start) throws ElementNotFoundException {
        if (start == this.tail) {
            return;
        }

        DoubleLinearNode<T> foundNode;

        try {
            foundNode = findNode(existingElement, start);
        } catch (ElementNotFoundException e) {
            if (start == this.head.getNext()) {
                throw new ElementNotFoundException(e.getMessage());
            }
            return;
        }

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(newElement);

        DoubleLinearNode<T> predecessor = foundNode.getPrev();
        DoubleLinearNode<T> successor = foundNode.getNext();

        predecessor.setNext(newNode);
        successor.setPrev(newNode);
        newNode.setPrev(predecessor);
        newNode.setNext(successor);
        foundNode.setNext(null);
        foundNode.setPrev(null);

        replace(existingElement, newElement, newNode.getNext());
    }
}
