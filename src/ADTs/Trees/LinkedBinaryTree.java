package ADTs.Trees;

import ADTs.*;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    private BinaryTreeNode<T> root;
    private int count;

    public LinkedBinaryTree() {
        this.count = 0;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the
     * new binary tree
     */
    public LinkedBinaryTree (T element)
    {
        this.count = 1;
        this.root = new BinaryTreeNode<>(element);
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException(EmptyCollectionException.DEFAULT_MESSAGE);
        }

        return root.getElement();
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

        T found = findAgain(targetElement, this.root);

        if (found == null) {
            throw new ElementNotFoundException(ElementNotFoundException.DEFAULT_MSG);
        }

        return found;
    }

    private T findAgain(T targetElement, BinaryTreeNode<T> node) {
        if (node == null) {
            return null;
        }

        if (node.getElement().equals(targetElement)) {
            return node.getElement();
        }

        T leftFound = findAgain(targetElement, node.getLeft());

        if (leftFound != null) {
            return leftFound;
        }

        return findAgain(targetElement, node.getRight());
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        inOrder(this.root, list);

        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        preOrder(this.root, list);

        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        levelOrder(this.root, list);

        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T>  results = new DoubleLinkedUnorderedList<>();

        levelOrder(this.root, results);

        return results.iterator();
    }

    @Override
    public String toString() {
        UnorderedListADT<T> list = new ArrayUnorderedList<>();

        postOrder(this.root, list);

        return list.toString();
    }

    private void preOrder(BinaryTreeNode<T> node, UnorderedListADT<T> list) {
        if (node == null) {
            return;
        }

        list.addToRear(node.getElement());
        preOrder(node.getLeft(), list);
        preOrder(node.getRight(), list);
    }

    private void postOrder(BinaryTreeNode<T> node, UnorderedListADT<T> list) {
        if (node == null) {
            return;
        }

        postOrder(node.getLeft(), list);
        postOrder(node.getRight(), list);
        list.addToRear(node.getElement());
    }

    private void inOrder(BinaryTreeNode<T> node, UnorderedListADT<T> list) {
        if (node == null) {
            return;
        }

        inOrder(node.getLeft(), list);
        list.addToRear(node.getElement());
        inOrder(node.getLeft(), list);
    }

    private void levelOrder(BinaryTreeNode<T> node, UnorderedListADT<T> list) {
        QueueADT<BinaryTreeNode<T>> nodes = new LinkedQueue<>();

        if (this.count != 0) {
            nodes.enqueue(node);
        }

        while (!nodes.isEmpty()) {
            try {
                BinaryTreeNode<T> next = nodes.dequeue();

                list.addToRear(next.getElement());

                if (next.getLeft() != null) {
                    nodes.enqueue(next.getLeft());
                }

                if (next.getRight() != null) {
                    nodes.enqueue(next.getRight());
                }

            } catch (EmptyCollectionException _) {
            }
        }

    }
}
