package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class LinkedList<T> implements ListADT<T> {

    protected DoubleLinearNode<T> head, tail;
    protected int size, modCount;

    public LinkedList() {
        this.size = 0;
        this.modCount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        T toRemove = head.getElement();

        head = head.getNext();

        if (this.size == 1) {
            this.tail = null;
        } else {
            head.setPrev(null);
        }

        this.size--;
        this.modCount++;

        return toRemove;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        T toRemove = tail.getElement();

        tail = tail.getPrev();

        if (this.size == 1) {
            this.head = null;
        } else {
            tail.setNext(null);
        }

        this.size--;
        this.modCount++;

        return toRemove;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        DoubleLinearNode<T> foundNode = this.findNode(element);

        if (this.size == 1) {
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

        this.size--;
        this.modCount++;

        return element;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return this.head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        try {
            this.findNode(target);
        } catch (ElementNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator(this.modCount);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        if (this.size > 0) {
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

    protected DoubleLinearNode<T> findNode(T element) throws ElementNotFoundException {
        DoubleLinearNode<T> current = this.head;

        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }

        if (current == null) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

        return current;
    }

    private class BasicIterator implements Iterator<T> {

        private DoubleLinearNode<T> cursor;
        private boolean okToRemove;
        private int expectedModCount;

        public BasicIterator(int expectedModCount) {
            this.expectedModCount = expectedModCount;
            this.okToRemove = false;
            this.cursor = LinkedList.this.head;
        }

        @Override
        public boolean hasNext() {
            return this.cursor != null;
        }

        @Override
        public T next() {
            if (cursor == null) {
                throw new NoSuchElementException("Next element not found.");
            }

            if (this.expectedModCount != modCount) {
                throw new ConcurrentModificationException("Concurrent Modification detected");
            }

            T next = this.cursor.getElement();

            this.okToRemove = true;

            this.cursor = this.cursor.getNext();

            return next;
        }

        @Override
        public void remove() {
            if (!okToRemove) {
                throw new UnsupportedOperationException("Can't perform this operation");
            }

            if (this.expectedModCount != modCount) {
                throw new ConcurrentModificationException("Concurrent Modification detected");
            }

            if (this.cursor == null) {
                try {
                    removeLast();
                } catch (EmptyCollectionException e) {
                }
            } else if (this.cursor.getPrev() == LinkedList.this.head){
                try {
                    removeFirst();
                } catch (EmptyCollectionException e) {
                }
            } else {
                DoubleLinearNode<T> toRemove = this.cursor.getPrev();
                DoubleLinearNode<T> predecessor = toRemove.getPrev();
                DoubleLinearNode<T> successor = toRemove.getNext();

                predecessor.setNext(successor);
                successor.setPrev(predecessor);

                LinkedList.this.size--;
                LinkedList.this.modCount++;
            }

            this.okToRemove = false;
            this.expectedModCount++;
        }
    }
}
