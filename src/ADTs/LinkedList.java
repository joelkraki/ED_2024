package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class LinkedList<T> implements ListADT<T> {

    protected DoubleLinearNode<T> head, tail;
    protected int count, modCount;

    public LinkedList() {
        this.count = 0;
        this.modCount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        T toRemove = head.getElement();

        head = head.getNext();

        this.count--;

        if (this.count == 0) {
            this.tail = null;
        } else {
            head.setPrev(null);
        }

        this.modCount++;

        return toRemove;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        T toRemove = tail.getElement();

        tail = tail.getPrev();

        this.count--;

        if (this.count == 0) {
            this.head = null;
        } else {
            tail.setNext(null);
        }

        this.modCount++;

        return toRemove;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> foundNode = this.findNode(element);

        if (foundNode == null) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

        if (this.count == 1) {
            this.head = null;
            this.tail = null;
        } else if (foundNode == this.head) {
            this.head = this.head.getNext();
            this.head.setPrev(null);
        } else if (foundNode == this.tail) {
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
        } else {
            DoubleLinearNode<T> predecessor = foundNode.getPrev();
            DoubleLinearNode<T> successor = foundNode.getNext();

            predecessor.setNext(successor);
            successor.setPrev(predecessor);
        }

        this.count--;
        this.modCount++;

        return element;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return this.head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return findNode(target) != null;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        if (this.count > 0) {
            DoubleLinearNode<T> current = this.head;

            while (current.getNext() != null) {
                str.append(current.getElement()).append(", ");

                current = current.getNext();
            }

            str.append(current.getElement());
        }

        str.append("]");

        return str.toString();
    }

    protected DoubleLinearNode<T> findNode(T element) {
        DoubleLinearNode<T> current = this.head;

        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }

        return current;
    }

    private class BasicIterator implements Iterator<T> {

        private DoubleLinearNode<T> cursor;
        private boolean okToRemove;
        private int expectedModCount;

        public BasicIterator() {
            this.expectedModCount = LinkedList.this.modCount;
            this.okToRemove = false;
            this.cursor = LinkedList.this.head;
        }

        @Override
        public boolean hasNext() {
            return this.cursor != null;
        }

        @Override
        public T next() {
            if (this.expectedModCount != modCount) {
                throw new ConcurrentModificationException("Concurrent Modification detected");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Next element not found.");
            }

            T next = this.cursor.getElement();

            this.okToRemove = true;

            this.cursor = this.cursor.getNext();

            return next;
        }

        @Override
        public void remove() {
            if (this.expectedModCount != LinkedList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification detected");
            }

            if (!okToRemove) {
                throw new UnsupportedOperationException("Can't perform this operation");
            }

            if (this.cursor == null) {
                try {
                    removeLast();
                } catch (EmptyCollectionException e) {
                    return;
                }
            } else {
                try {
                    LinkedList.this.remove(this.cursor.getPrev().getElement());
                } catch (EmptyCollectionException | ElementNotFoundException e) { // cuidado com os REPETIDOS
                    return;
                }
            }

            this.okToRemove = false;
            this.expectedModCount++;
        }
    }
}
