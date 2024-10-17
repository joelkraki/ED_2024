package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.NoSuchElementException;

public class DoubleLinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T>{

    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null,");
        }

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        newNode.setNext(super.head);

        if (super.size == 0) {
            super.tail = newNode;
        } else {
            super.head.setPrev(newNode);
        }

        super.head = newNode;
        super.size++;
        super.modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null,");
        }

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        newNode.setPrev(super.tail);

        if (super.size == 0) {
            super.head = newNode;
        } else {
            super.tail.setNext(newNode);
        }

        super.tail = newNode;
        super.size++;
        super.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, ElementNotFoundException {
        if (element == null || target == null) {
            throw new IllegalArgumentException("Parameter element / target is null");
        }

        if (super.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> targetNode = super.findNode(target);
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        newNode.setPrev(targetNode);

        if (targetNode == super.tail) {
            super.tail = newNode;
        } else {
            DoubleLinearNode<T> successor = targetNode.getNext();

            newNode.setNext(successor);
            successor.setPrev(newNode);
        }

        targetNode.setNext(newNode);

        super.size++;
        super.modCount++;
    }
}
