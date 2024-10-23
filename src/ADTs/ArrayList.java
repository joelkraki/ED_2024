package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ArrayList<T> implements ListADT<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 1;
    private static final int GROWTH_FACTOR = 2;

    protected T[] list;
    protected int count;
    protected int modCount;

    public ArrayList(int capacity) {
        list = (T[]) new Object[capacity];
        this.count = 0;
        this.modCount = 0;
    }

    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException();
        }

        T first = this.list[0];

        for (int i = 0; i < this.count - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[--this.count] = null;
        this.modCount++;

        return first;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException();
        }

        T last = this.list[this.count - 1];

        this.list[--this.count] = null;
        this.modCount++;

        return last;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.count == 0) {
            throw new EmptyCollectionException();
        }

        int index = findElement(element);

        if (index == -1) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

        for (int i = index; i < this.count - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[--this.count] = null;
        this.modCount++;

        return element;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException();
        }

        return this.list[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException();
        }

        return this.list[this.count - 1];
    }

    @Override
    public boolean contains(T target) {
        return findElement(target) != -1;
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
        if (this.count == 0) {
            return "[]";
        }

        StringBuilder str = new StringBuilder("[");

        int i;
        for (i = 0; i < this.size() - 1; i++) {
            str.append(list[i]).append(", ");
        }

        str.append(list[i]).append("]");

        return str.toString();
    }

    protected int findElement(T element) {
        for (int i = 0; i < this.count; i++) {
            if (this.list[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    protected void expandCapacity() {
        T[] newArray = (T[]) (new Object[this.list.length * GROWTH_FACTOR]);

        for (int i = 0; i < this.list.length; i++) {
            newArray[i] = this.list[i];
        }

        this.list = newArray;
    }

    private class BasicIterator implements Iterator<T> {
        private int cursor;
        private boolean okToRemove;
        private int expectedModCount;

        public BasicIterator() {
            this.expectedModCount = ArrayList.this.modCount;
            this.okToRemove = false;
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor != count;
        }

        @Override
        public T next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("Concurrent Modification Detected.");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Next element not found");
            }

            this.okToRemove = true;

            return list[cursor++];
        }

        @Override
        public void remove() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("Concurrent Modification Detected.");
            }

            if (!okToRemove) {
                throw new UnsupportedOperationException("Can't perform this operation");
            }

            try {
                ArrayList.this.remove(list[cursor - 1]); //pode dar problemas elementos repetidos
            } catch (EmptyCollectionException | ElementNotFoundException e) {
                return;
            }

            this.cursor--;
            okToRemove = false;
            this.expectedModCount++;
        }
    }
}
