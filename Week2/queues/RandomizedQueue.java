/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 2: RandonmizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

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

        // return random number
        int randIndex = StdRandom.uniform(elements);
        // Choose a random element from the queue
        Item randElement = array[randIndex];
        // Copy the last element to the empty spot (i.e. plug the hole)
        array[randIndex] = array[elements - 1];
        // Forget the last element (to avoid loitering)
        array[elements - 1] = null;
        // Update the queue size
        elements--;

        // shrink size of array if necessary
        if (elements > 0 && elements == array.length / 4) resize(array.length / 2);

        return randElement;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        // return random number
        int randIndex = StdRandom.uniform(elements);
        // Choose a random element from the queue
        Item randElement = array[randIndex];

        return randElement;
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int ix;
        private Item[] arrayCopy;

        public ArrayIterator() {
            // Make a copy of the array
            arrayCopy = (Item[]) new Object[elements];
            for (int j = 0; j < elements; j++) {
                arrayCopy[j] = array[j];
            }

            // Shuffle the objects in the arrayCopy
            StdRandom.shuffle(arrayCopy);

            // Initialize the iterator index
            ix = 0;
        }

        public boolean hasNext() {
            return ix < elements;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return arrayCopy[ix++];
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
        // Feed
        RandomizedQueue<String> bag = new RandomizedQueue<String>();
        System.out.println("size: " + bag.size());
        bag.enqueue("first");
        bag.enqueue("second");
        bag.enqueue("third");
        bag.enqueue("fourth");
        System.out.println("size after 4 elements: " + bag.size());

        int count = 0;
        for (String s : bag) {
            System.out.println("iterator: " + s);
            count++;
        }

        System.out.println("count after 4 enqueues: " + count);
    }
}
