package ADTs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T>{

    public ArrayUnorderedList(int capacity) {
        super(capacity);
    }

    public ArrayUnorderedList() {
    }

    @Override
    public void addToFront(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null.");
        }

        if (super.count == super.list.length) {
            super.expandCapacity();
        }

        for (int i = super.count - 1; i >= 0; i--) {
            super.list[i + 1] = super.list[i];
        }

        super.list[0] = element;
        super.count++;
        super.modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null.");
        }

        if (super.count == super.list.length) {
            super.expandCapacity();
        }

        super.list[super.count++] = element;
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

        if (super.count == super.list.length) {
            super.expandCapacity();
        }

        int targetIndex = super.findElement(target);

        if (targetIndex == -1) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

        for (int i = super.count; i > targetIndex + 1; i--) {
            super.list[i] = super.list[i - 1];
        }

        super.list[targetIndex + 1] = element;
        super.count++;
        super.modCount++;
    }
}
