package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public abstract class ArrayList<T> implements ListADT<T> {

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
    public boolean contains(T target) throws EmptyCollectionException {
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
        return null;
    }
}
