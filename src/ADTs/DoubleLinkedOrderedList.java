package ADTs;

import Exceptions.NonComparableElementException;

public class DoubleLinkedOrderedList<T> extends LinkedList<T> implements OrderedListADT<T> {

    public DoubleLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NonComparableElementException {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null.");
        }

        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException(NonComparableElementException.DEFAULT_MSG);
        }

        Comparable<T> comparableElem = (Comparable<T>) element;

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (super.count == 0) {
            super.head = newNode;
            super.tail = newNode;
        } else {
            DoubleLinearNode<T> currentNode = super.head;

            while (currentNode != null && comparableElem.compareTo(currentNode.getElement()) > 0) {
                currentNode = currentNode.getNext();
            }

            if (currentNode == super.head) { // add to the left side of the Linked List
                newNode.setNext(super.head);
                super.head.setPrev(newNode);
                super.head = newNode;
            } else if (currentNode == null) { // add to the right side of the Linked List
                super.tail.setNext(newNode);
                newNode.setPrev(super.tail);
                super.tail = newNode;
            } else { // add to the middle of the Linked List
                DoubleLinearNode<T> predecessor = currentNode.getPrev();

                newNode.setPrev(predecessor);
                newNode.setNext(currentNode);

                predecessor.setNext(newNode);
                currentNode.setPrev(newNode);
            }
        }

        super.count++;
        super.modCount++;
    }
}
