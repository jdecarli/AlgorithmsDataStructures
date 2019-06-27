/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 2: Permutation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {

        int numPops = Integer.parseInt(args[0]);

        String s;
        boolean isEmpty = false;

        // Instantiate a Randomized Queue
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        // StdOut.println("Reading strings from the Standard Input....");
        while (!isEmpty) {
            try {
                s = StdIn.readString();
                // StdOut.println(s);
                rq.enqueue(s);
            }
            catch (NoSuchElementException e) {
                isEmpty = true;
            }
        }

        // Print `numPops` random elements from the queue
        // StdOut.println(
        //         "Printing " + numPops + " randomly chosen strings from the Standard Input....");
        int counter = 0;
        for (String s1 : rq) {
            StdOut.println(s1);
            counter++;
            if (counter == numPops) break;
        }


    }
}
