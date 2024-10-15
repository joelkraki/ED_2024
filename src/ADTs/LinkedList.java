package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public abstract class LinkedList<T> implements ListADT<T> {
    @Override
    public T removeFirst() throws EmptyCollectionException {
        return null;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        return null;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        return null;
    }

    @Override
    public T first() throws EmptyCollectionException {
        return null;
    }

    @Override
    public T last() throws EmptyCollectionException {
        return null;
    }

    @Override
    public boolean contains(T target) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }

    private class BasicIterator<T> implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
}
