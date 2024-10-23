package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

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

        if (super.count == 0) {
            super.tail = newNode;
        } else {
            super.head.setPrev(newNode);
        }

        super.head = newNode;
        super.count++;
        super.modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null,");
        }

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        newNode.setPrev(super.tail);

        if (super.count == 0) {
            super.head = newNode;
        } else {
            super.tail.setNext(newNode);
        }

        super.tail = newNode;
        super.count++;
        super.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, ElementNotFoundException {
        if (element == null || target == null) {
            throw new IllegalArgumentException("Parameter element / target is null");
        }

        if (super.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> targetNode = super.findNode(target);

        if (targetNode == null) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

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

        super.count++;
        super.modCount++;
    }
}
