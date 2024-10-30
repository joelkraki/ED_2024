package ADTs.Trees;

import ADTs.*;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    private static final int DEFAULT_CAPACITY = 1;
    private static final int GROWTH_FACTOR = 2;

    protected T[] tree;
    protected int count;

    public ArrayBinaryTree(int capacity) {
        this.count = 0;
        this.tree = (T[]) new Object[capacity];
    }

    public ArrayBinaryTree() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayBinaryTree(int capacity, T root) {
        this.count = 0;
        this.tree = (T[]) new Object[capacity];
        this.tree[0] = root;
    }

    public ArrayBinaryTree(T root) {
        this(DEFAULT_CAPACITY, root);
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return tree[0];
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
    public boolean contains(T targetElement) throws EmptyCollectionException {
        if (targetElement == null) {
            throw new IllegalArgumentException("Parameter element is null");
        }

        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        try {
            find(targetElement);
        } catch (ElementNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public T find(T targetElement) throws EmptyCollectionException, ElementNotFoundException {
        if (targetElement == null) {
            throw new IllegalArgumentException("Parameter element is null");
        }

        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        T temp = null;
        boolean found = false;

        for (int i = 0; i < count && !found; i++)
            if (targetElement.equals(tree[i])) {
                found = true;
                temp = tree[i];
            }

        if (!found)
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);

        return temp;
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        inOrder(0, list);

        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        preOrder(0, list);

        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        postOrder(0, list);

        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> results = new DoubleLinkedUnorderedList<>();

        levelOrder(0, results);

        return results.iterator();
    }

    @Override
    public String toString() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        levelOrder(0, list);

        return list.toString();
    }

    protected void preOrder(int pos, UnorderedListADT<T> list) {
        if (pos < tree.length) {
            if (tree[pos] != null) {
                list.addToRear(tree[pos]);
                preOrder(pos * 2 + 1, list);
                preOrder((pos + 1) * 2, list);
            }
        }
    }

    protected void postOrder(int pos, UnorderedListADT<T> list) {
        if (pos < tree.length) {
            if (tree[pos] != null) {
                postOrder(pos * 2 + 1, list);
                postOrder((pos + 1) * 2, list);
                list.addToRear(tree[pos]);
            }
        }
    }

    protected void inOrder(int pos, UnorderedListADT<T> list) {
        if (pos < tree.length) {
            if (tree[pos] != null) {
                inOrder(pos * 2 + 1, list);
                list.addToRear(tree[pos]);
                inOrder((pos + 1) * 2, list);
            }
        }
    }

    protected void levelOrder(int pos, UnorderedListADT<T> list) {
        QueueADT<Integer> queue = new LinkedQueue<>();

        if (this.count != 0) {
            queue.enqueue(pos);
        }

        while (!queue.isEmpty()) {
            try {
                int next = queue.dequeue();

                list.addToRear(this.tree[next]);

                if (this.tree[next * 2 + 1] != null) {
                    queue.enqueue(next * 2 + 1);
                }

                if (this.tree[(next + 1) * 2] != null) {
                    queue.enqueue((next + 1) * 2);
                }

            } catch (EmptyCollectionException _) {
            }
        }
    }
}
