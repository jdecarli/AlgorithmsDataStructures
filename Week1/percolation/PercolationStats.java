/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 1: PercolationStats
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private int size;
    private int nTrials;
    private int numberOfCells;
    private int ixVirtualTop;
    private int ixVirtualBottom;

    public PercolationStats(int n,
                            int trials)    // perform trials independent experiments on an n-by-n grid
    {
        validateInput(n, trials);

        // Size of the grid
        size = n;
        // Number of the MC trials
        nTrials = trials;

        // Total number of cells: grid plus two virtual ones
        numberOfCells = n * n + 2;

        // Set the indices for the virtual top/bottom sites (cells):
        ixVirtualTop = 0;
        ixVirtualBottom = numberOfCells - 1;

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
        /*
         * Private Methods
         */
        System.out.println("Started...");
        // System.out.println("Args: " + args[0] + "," + args[1]);
    }

    private int[] getGridCoordinates(int ix) {
        /**
         * Converts the flat index of the grid to a
         * (row, col) pair of coordinates.
         * @param (int) ix: flat grid index
         * @return (int[]) (row, col)
         */

        if (ix == ixVirtualTop) { // virtual-top
            int[] coord = { 0, 1 };
            return coord;
        }

        if (ix == ixVirtualBottom) { // virtual-bottom
            int[] coord = { size + 1, size };
            return coord;
        }

        int row;
        int col;

        row = ix / size + 1;
        col = ix - (row - 1) * size;

        int[] coord = { row, col };

        return coord;
    }

    private int[] getShuffledIndexList(int n) {
        // Get the array length
        int numberOfGridCells = n * n;

        // Build a list of cell indices
        int[] ixList = new int[numberOfGridCells];
        for (int i = 0; i < numberOfGridCells; i++) {
            ixList[i] = i + 1;
        }

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

        // Get a shuffled index list
        int[] ixListShuffled = getShuffledIndexList(size);

        // Loop through all cells randomly.
        // Keep opening them until the grid percolates
        int i = 0;
        while (!grid.percolates() && i < ixListShuffled.length) {
            // Select a random cell...
            int[] gridCoord = getGridCoordinates(ixListShuffled[i]);
            int row = gridCoord[0];
            int col = gridCoord[1];
            // ... and open it
            grid.open(row, col);

            i++;
        }
        // Estimate the probability for a site to be open
        double prob = ((double) grid.numberOfOpenSites()) / (size * size);

        return prob;
    }

    private void validateInput(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "The grid size must be a positive integer!");
        }
    }
}
