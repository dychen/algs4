import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;

/*
 * Daniel Chen
 * 10/14/13
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    /*
     * Implement as a resizing array.
     * size: The number of Items in the array.
     * items: the array of Items.
     */
    private int size;
    private Item[] items;

    /*
     * No-argument constructor. Initializes an empty RandomizedQueue of size 1.
     */
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    /*
     * Returns whether or not the RandomizedQueue is empty.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /*
     * Returns the number of elements in the RandomizedQueue.
     */
    public int size() {
        return size;
    }

    /*
     * Adds an item to the back of the RandomizedQueue. If it's full, resize the
     * array first.
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (size == items.length)
            resize(2 * items.length);
        items[size++] = item;
    }

    /*
     * Removes the element at a random index in the RandomizedQueue and returns
     * it, then moves the element at the end of the RandomizedQueue to that
     * index. If it's 1/4 full, resize the array afterwards.
     */
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        int i = randomIndex();
        Item result = items[i];
        items[i] = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        return result;
    }

    /*
     * Returns an element at a random index in the RandomizedQueue.
     */
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int i = randomIndex();
        return items[i];
    }

    /*
     * Returns an Iterator object.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /*
     * Implements the Iterator interface. Contains a shuffled copy of the array
     * in the corresponding RandomizedQueue object.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iterArray;
        private int curr = 0;
        public RandomizedQueueIterator() {
            iterArray = (Item[]) new Object[size];
            for (int i = 0; i < size; i++)
                iterArray[i] = items[i];
            StdRandom.shuffle(iterArray);
        }
        public boolean hasNext() {
            return (curr < size);
        }
        public Item next() {
            if (curr == size)
                throw new NoSuchElementException();
            return iterArray[curr++];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /*
     * Resizes the array to the specified size.
     */
    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }

    /*
     * Returns a random index in the array.
     */
    private int randomIndex() {
        return StdRandom.uniform(size);
    }
}
