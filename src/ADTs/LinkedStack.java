package ADTs;

import Exceptions.EmptyCollectionException;

public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> top;
    private int size;

    public LinkedStack() {
        this.top = null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element is null");
        }

        LinearNode<T> newNode = new LinearNode<>(element);
        newNode.setNext(top);
        top = newNode;

        this.size++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The stack is currently empty");
        }

        LinearNode<T> oldTop = top;

        top = top.getNext();

        oldTop.setNext(null);

        this.size--;

        return oldTop.getElement();
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The stack is currently empty");
        }

        return this.top.getElement();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        // To print elements from bottom to top, we need to reverse the traversal
        LinkedStack<T> tempStack = new LinkedStack<>(); // A temporary stack to reverse the order
        LinearNode<T> currentNode = this.top;

        // Push all elements to the tempStack to reverse the order
        while (currentNode != null) {
            tempStack.push(currentNode.getElement());
            currentNode = currentNode.getNext();
        }

        // Pop from tempStack to append elements in the correct order
        while (!tempStack.isEmpty()) {
            try {
                sb.append(tempStack.pop());
            } catch (EmptyCollectionException e) {
            }
            if (!tempStack.isEmpty()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

}
