/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 2: Deque
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    // helper linked list class
    private class Node {
        private Item item;
        private Node next, prev;

        public Node(Item input) {
            item = input;
            next = null;
            prev = null;
        }
    }

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node oldfirst = first;
        first = new Node(item);
        first.next = oldfirst;
        if (oldfirst != null) oldfirst.prev = first;

        if (size == 0) last = first;

        size++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node newLast = new Node(item);

        if (size == 0) {
            first = newLast;
            last = newLast;
        }
        else {
            newLast.prev = last;
            last.next = newLast;
            last = newLast;
        }

        size++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        // System.out.println("internal remove first: " + first.item);
        //  System.out.println("internal remove first: " + first.item);

        Item result = first.item;

        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        size--;

        return result;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item result = last.item;

        if (last.prev == null) {
            last = null;
            first = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        size--;

        return result;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        // Empty queue
        // Make sure to test what happens when your data structures are emptied. One very common bug is
        // for something to go wrong when your data structure goes from non-empty to empty and then back
        // to non-empty. Make sure to include this in your tests.

        // Mutiple Iterators
        // Make sure to test that multiple iterators can be used simultaneously. You can test this with a
        // nested foreach loop. The iterators should operate independently of one another

        // Basic tests
        Deque<String> deck = new Deque<String>();

        System.out.println("isEmpty: " + deck.isEmpty());
        System.out.println("size: " + deck.size);
        deck.addFirst("first");
        deck.addFirst("more first");
        deck.addLast("last");
        deck.addLast("more last");
        System.out.println("isEmpty: " + deck.isEmpty());
        System.out.println("size: " + deck.size);

        // Iterator
        for (String s : deck)
            System.out.println("iterator: " + s);

        System.out.println("size: " + deck.size);
        System.out.println("remove first: " + deck.removeFirst());
        System.out.println("remove first: " + deck.removeFirst());
        System.out.println("remove first: " + deck.removeFirst());
        System.out.println("remove first: " + deck.removeFirst());
        System.out.println("size: " + deck.size);

        // Second set
        System.out.println("\n---- Refeeding -----");
        System.out.println("isEmpty: " + deck.isEmpty());
        System.out.println("size: " + deck.size);
        deck.addLast("some last");
        deck.addLast("very last");
        deck.addFirst("some first");
        deck.addFirst("very first");
        System.out.println("size: " + deck.size);

        // Iterator
        for (String s : deck)
            System.out.println("iterator: " + s);

        System.out.println("remove last: " + deck.removeLast());
        System.out.println("remove last: " + deck.removeLast());
        System.out.println("remove last: " + deck.removeLast());
        System.out.println("remove last: " + deck.removeLast());
        System.out.println("size: " + deck.size);
    }
}
