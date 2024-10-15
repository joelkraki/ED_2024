package ADTs;

class DoubleLinearNode<T> {

    private DoubleLinearNode<T> prev;
    private DoubleLinearNode<T> next;
    private T element;

    public DoubleLinearNode(T element) {
        this.element = element;
    }

    public DoubleLinearNode() {
    }

    public DoubleLinearNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DoubleLinearNode<T> prev) {
        this.prev = prev;
    }

    public DoubleLinearNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleLinearNode<T> next) {
        this.next = next;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}
