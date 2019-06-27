/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 2: RandonmizedQueue
 **************************************************************************** */

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;           // array of items
    private int elements;           // number of elements on stack

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        array = (Item[]) new Object[2];
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return elements == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return elements;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        if (elements == array.length)
            resize(2 * array.length);   // double size of array if necessary
        array[elements++] = item;       // add item
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        // TODO: return random number

        // shrink size of array if necessary
        if (elements > 0 && elements == array.length / 4) resize(array.length / 2);
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        // PLACEHOLDER - replace with random iterator
        return new ReverseArrayIterator();
    }

    // PLACEHOLDER - replace with random iterator
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = elements - 1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return array[i--];
        }
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= elements;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < elements; i++) {
            temp[i] = array[i];
        }
        array = temp;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }

    public static void main(String[] args)   // unit testing (optional)
    {
    }
}
