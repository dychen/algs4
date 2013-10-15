import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;

/*
 * Daniel Chen
 * 10/14/13
 */

public class Deque<Item> implements Iterable<Item> {

    /* 
     * Implement as a doubly linked list.
     * size: Number of Nodes in the structure.
     * first: Pointer to the head of the structure.
     * last: Pointer to the tail of the structure.
     */
    private int size;
    private Node first;
    private Node last;

    /* 
     * Inner class. The doubly linked list is made of Nodes.
     * next: Pointer to the next Node.
     * prev: Pointer to the previous Node.
     * value: Value of the current Node.
     */
    private class Node {
        Node next;
        Node prev;
        Item value;
    }

    /*
     * No-argument constructor. Initializes an empty Deque.
     */
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    /*
     * Returns whether or not the Deque is empty.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /*
     * Returns the number of elements in the Deque.
     */
    public int size() {
        return size;
    }

    /*
     * Adds an item to the front of the Deque.
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        if (oldFirst == null) {
            first.next = null;
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        first.prev = null;
        first.value = item;
        size++;
    }

    /*
     * Adds an item to the back of the Deque.
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        if (oldLast == null) {
            last.prev = null;
            first = last;
        }
        else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        last.next = null;
        last.value = item;
        size++;
    }

    /*
     * Removes an item from the front of the Deque.
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.value;
        if (first.next != null) {
            first = first.next;
            first.prev = null;
        }
        else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    /*
     * Removes an item from the back of the Deque.
     */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.value;
        if (last.prev != null) {
            last = last.prev;
            last.next = null;
        }
        else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    /*
     * Returns an Iterator object.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /*
     * Implements the Iterator interface.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node curr = first;
        public boolean hasNext() {
            return (curr != null);
        }
        public Item next() {
            if (curr == null)
                throw new NoSuchElementException();
            Node tmp = curr;
            curr = curr.next;
            return tmp.value;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}