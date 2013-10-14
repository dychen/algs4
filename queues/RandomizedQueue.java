import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;

    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (size == items.length)
            resize(2 * items.length);
        items[size++] = item;
    }

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

    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int i = randomIndex();
        return items[i];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int curr = 0;
        public boolean hasNext() {
            return (curr < size-1);
        }
        public Item next() {
            if (curr == size)
                throw new NoSuchElementException();
            return items[curr++];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }

    private int randomIndex() {
        return StdRandom.uniform(size);
    }
}
