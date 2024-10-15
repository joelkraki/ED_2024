package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public abstract class ArrayList<T> implements ListADT<T> {

    private static final int INITIAL_CAPACITY = 1;
    private static final int GROWTH_FACTOR = 2;

    private T[] list;
    private int size;
    private int modCount;

    public ArrayList() {
        list = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.modCount = 0;
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

        this.list[this.size-- - 1] = null;
        this.modCount++;
        return first;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.size == 0) {
            throw new EmptyCollectionException();
        }

        T last = this.list[this.size - 1];

        this.list[this.size-- - 1] = null;
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
        return new BasicIterator<>(this.modCount);
    }

    private int findElement(T element) throws ElementNotFoundException {
        for (int i = 0; i < this.size; i++) {
            if (this.list[i].equals(element)) {
                return i;
            }
        }

        throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
    }

    private class BasicIterator<T> implements Iterator<T> {
        private boolean okToRemove;
        private int expectedModCount;

        public BasicIterator(int expectedModCount) {
            this.expectedModCount = expectedModCount;
            this.okToRemove = false;
        }

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
