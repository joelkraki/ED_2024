package ADTs;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidParameterTypeException;
import Exceptions.NonComparableElementException;

import java.util.Arrays;
import java.util.Comparator;

public class SentinelDoubleLinkedList<T extends Comparable<? super T>> {

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

        if (node == null) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

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

    private DoubleLinearNode<T> findNode(T element, DoubleLinearNode<T> start) {
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
            return null;
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

    public void replace(T existingElement, T newElement) throws ElementNotFoundException, EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        replace(existingElement, newElement, this.head.getNext());
    }

    private void replace(T existingElement, T newElement, DoubleLinearNode<T> start) throws ElementNotFoundException {
        if (start == this.tail) {
            return;
        }

        DoubleLinearNode<T> foundNode = findNode(existingElement, start);

        if (foundNode == null) {
            if (start == this.head.getNext()) {
                throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
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

    public boolean linearSearch (T target) throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> current = this.head.getNext();
        boolean found = false;

        while (!found && current != this.tail) {
            if (target.compareTo(current.getElement()) == 0)
                found = true;

            current = current.getNext();
        }

        return found;
    }

    public void selectionSort () throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> min;
        DoubleLinearNode<T> current = this.head.getNext();
        DoubleLinearNode<T> last = this.tail.getPrev();
        T temp;

        while (current != last) {
            min = current;

            DoubleLinearNode<T> scan = current.getNext();

            while (scan != this.tail) {
                if (scan.getElement().compareTo(min.getElement()) < 0) {
                    min = scan;
                }

                scan = scan.getNext();
            }

            temp = min.getElement();
            min.setElement(current.getElement());
            current.setElement(temp);

            current = current.getNext();
        }
    }

    public void insertionSort () throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> current = this.head.getNext().getNext(); //Starts at the 2nd element

        while (current != this.tail) {
            T currElem = current.getElement();

            DoubleLinearNode<T> position = current.getPrev();

            while (position != this.head && position.getElement().compareTo(currElem) > 0) {
                position = position.getPrev();
            }

            if (position != current.getPrev()) {
                DoubleLinearNode<T> posNext = position.getNext();

                posNext.setPrev(current);
                position.setNext(current);

                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());

                current.setNext(posNext);
                current.setPrev(position);
            }

            current = current.getNext();
        }
    }

    public void bubbleSort () throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> position = this.tail.getPrev();
        DoubleLinearNode<T> scan = null;
        T temp;

        while (position != this.head) {
            scan = this.head.getNext();

            while (scan != position) {
                if (scan.getElement().compareTo(scan.getNext().getElement()) > 0) {
                    temp = scan.getElement();
                    scan.setElement(scan.getNext().getElement());
                    scan.getNext().setElement(temp);
                }

                scan = scan.getNext();
            }

            position = position.getPrev();
        }
    }

    public void quickSort() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        quickSort(this.head.getNext(), this.tail.getPrev());
    }

    private void quickSort(DoubleLinearNode<T> start, DoubleLinearNode<T> end) throws EmptyCollectionException {
        if (end.getPrev() == start) {
            return;
        }

        DoubleLinearNode<T> nodeOfPartition = findPartition(start, end);

        quickSort(start, nodeOfPartition.getPrev());

        quickSort(nodeOfPartition.getNext(), end);
    }

    private DoubleLinearNode<T> findPartition (DoubleLinearNode<T> start, DoubleLinearNode<T> end) throws EmptyCollectionException {
        DoubleLinearNode<T> left, right;
        T temp, partitionelement;
        DoubleLinearNode<T> middle = findMiddle();

        // use middle element as partition
        partitionelement = middle.getElement();
        left = start;
        right = end;

        while (right.getPrev() != left) {
            /** search for an element that is > the partitionelement */
            while (left.getElement().compareTo(partitionelement) < 0) {
                left = left.getNext();
            }
            /** search for an element that is < the partitionelement */
            while (right.getElement().compareTo(partitionelement) > 0)
                right = right.getPrev();

            /** swap the elements */
            if (right != middle && left != middle)
            {
                temp = left.getElement();
                left.setElement(right.getElement());
                right.setElement(temp);
            }
        }

        /** move partition element to partition index*/
        temp = start.getElement();
        start.setElement(right.getElement());
        right.setElement(temp);

        return right;
    }

    private DoubleLinearNode<T> findMiddle() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> current = this.head.getNext();

        for (int i = 0; i < this.size / 2; i++) {
            current = current.getNext();
        }

        return current;
    }

    private class BasicComparator implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {
            if (!(o1 instanceof Comparable)) {
                throw new IllegalArgumentException(NonComparableElementException.DEFAULT_MSG);
            }

            Comparable<T> cmpO1 = (Comparable<T>) o1;

            return cmpO1.compareTo(o1);
        }
    }
}
