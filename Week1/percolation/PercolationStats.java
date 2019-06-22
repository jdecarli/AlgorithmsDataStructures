/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private int size;
    private int nTrials;

    public PercolationStats(int n,
                            int trials)    // perform trials independent experiments on an n-by-n grid
    {
        size = n;
        nTrials = trials;

    }

    private int[] getShuffledIndexList(int n) {
        // Get the array length
        int numberOfGridCells = n * n;

        // Build a list of cell indices
        int[] ixList = new int[numberOfGridCells];
        for (int i = 0; i < numberOfGridCells; i++) {
            ixList[i] = i + 1;
        }
        // Instantiate a random generator
        // StdRandom r = new StdRandom();

        // Shuffle the index list
        StdRandom.shuffle(ixList);

        return ixList;
    }

    private double runNewTrial() {
        /*
         * Run a new independent trial. Create a new grid and keep opening
         * sites until it percolates. When it does, get the number of
         * open sites and estimate the probability for a site to be open.
         *
         * @return probability for a site to be open*/

        // Instantiate a new grid for this trial
        Percolation grid = new Percolation(size);

        return 0.0;
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return 0.0;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {

        return 0.0;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return 0.0;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return 0.0;
    }

    public static void main(String[] args)        // test client (described below)
    {

    }
}
