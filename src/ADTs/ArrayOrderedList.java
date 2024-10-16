package ADTs;

import Exceptions.NonComparableElementException;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    public ArrayOrderedList(int capacity) {
        super(capacity);
    }

    @Override
    public void add(T element) throws NonComparableElementException {
        if (element == null) {
            throw new IllegalArgumentException("Element parameter is null");
        }

        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException(NonComparableElementException.DEFAULT_MSG);
        }

        if (super.size == super.list.length) {
            super.expandCapacity();
        }

        Comparable<T> comparableElem = (Comparable<T>) element;

        int i = 0;
        while (i < super.size && comparableElem.compareTo(super.list[i]) > 0) {
            i++;
        }

        for (int j = super.size; j > i ; j--) {
            super.list[j] = super.list[j - 1];
        }

        super.list[i] = element;
        super.size++;
        super.modCount++;
    }
}
