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
    protected int size;
    protected int modCount;

    public ArrayList(int capacity) {
        list = (T[]) new Object[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @Override
    public T removeFirst() throws EmptyCollectionException { //??se fosse circular este metodo nao tinha que ser O(n)
        if (this.size == 0) {
            throw new EmptyCollectionException();
        }

        T first = this.list[0];

        for (int i = 0; i < this.size - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.size - 1] = null;
        this.size--;
        this.modCount++;
        return first;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException();
        }

        T last = this.list[this.size - 1];

        this.list[this.size - 1] = null;
        this.size--;
        this.modCount++;
        return last;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.size == 0) {
            throw new EmptyCollectionException();
        }

        int index = findElement(element);
        T toRemove = this.list[index];

        for (int i = index; i < this.size - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.size - 1] = null;
        this.size--;
        this.modCount++;
        return toRemove;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException();
        }

        return this.list[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException();
        }

        return this.list[this.size - 1];
    }

    @Override
    public boolean contains(T target) {
        try {
            findElement(target);
        } catch (ElementNotFoundException e) { //??? Esta abordagem é muito pesada por lançar uma exceção?
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
        if (this.size == 0) {
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

    private int findElement(T element) throws ElementNotFoundException {
        for (int i = 0; i < this.size; i++) {
            if (this.list[i].equals(element)) {
                return i;
            }
        }

        throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
    }

    private class BasicIterator implements Iterator<T> {
        private int cursor;
        private boolean okToRemove;
        private int expectedModCount;

        public BasicIterator(int expectedModCount) {
            this.expectedModCount = expectedModCount;
            this.okToRemove = false;
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("Concurrent Modification Detected.");
            }
            if (cursor == size) {
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
                ArrayList.this.remove(list[this.cursor - 1]);  // tornar as excecões Runtime?
            } catch (EmptyCollectionException | ElementNotFoundException e) {

            }

            okToRemove = false;
            this.expectedModCount++;
        }
    }

    protected void expandCapacity() {
        T[] newArray = (T[]) (new Object[this.list.length * GROWTH_FACTOR]);

        for (int i = 0; i < this.list.length; i++) {
            newArray[i] = this.list[i];
        }

        this.list = newArray;
    }

}
