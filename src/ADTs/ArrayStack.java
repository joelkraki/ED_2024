package ADTs;

import Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements StackADT<T> {

    private static final int DEFAULT_CAPACITY = 1;
    private static final int GROWTH_FACTOR = 2;

    private T[] stack;
    private int top;

    public ArrayStack() {
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
        this.top = 0;
    }

    @Override
    public int size() {
        return this.top;
    }

    @Override
    public boolean isEmpty() {
        return this.top == 0;
    }

    @Override
    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null");
        }

        if (this.top == this.stack.length) {
            expandCapacity();
        }

        this.stack[top++] = element;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The stack is currently empty");
        }

        T toRemove = this.stack[--top];
        this.stack[top] = null;
        return toRemove;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The stack is currently empty");
        }

        return this.stack[top - 1];
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return "[]";
        }

        StringBuilder str = new StringBuilder("[");

        int i;
        for (i = 0; i < this.size() - 1; i++) {
            str.append(stack[i]).append(", ");
        }

        str.append(stack[i]).append("]");

        return str.toString();
    }

    private void expandCapacity() {
        T[] newArray = (T[]) (new Object[this.stack.length * GROWTH_FACTOR]);

        for (int i = 0; i < this.stack.length; i++) {
            newArray[i] = this.stack[i];
        }

        this.stack = newArray;
    }
}
