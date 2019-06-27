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

        while (!isEmpty) {
            try {
                s = StdIn.readString();
                StdOut.println(s);
            }
            catch (NoSuchElementException e) {
                isEmpty = true;
            }
        }


    }
}
