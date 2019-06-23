/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 1: PercolationStats
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // enable/disable debug mode
    private static boolean isDebugMode;
    private static int nTrials = 0;

    private int size;
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
        // Debug mode
        isDebugMode = false;

        System.out.println("Started... (DebugMode: " + isDebugMode + ")\n");
        Stopwatch stopwatch = new Stopwatch();

        System.out.println("Size: " + args[0] + " | Trials: " + args[1]);
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));

        for (int i = 0; i < nTrials; i++)
            stats.runNewTrial();

        System.out.println("\nElapsed time: " + stopwatch.elapsedTime());
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
        // col = ix - (row - 1) * size;
        col = ix - (row - 1) * size + 1; // index is 1 based

        int[] coord = { row, col };

        return coord;
    }

    private int[] getShuffledIndexList(int n) {
        // Get the array length
        int numberOfGridCells = n * n;

        if (isDebugMode)
            System.out.println("shuffled list - numberOfGridCells: " + numberOfGridCells);

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

        if (isDebugMode) System.out.println("percolation class ok");

        // Get a shuffled index list
        int[] ixListShuffled = getShuffledIndexList(size);

        if (isDebugMode) {
            System.out.println("shuffled list ok");
            System.out.println("ixListShuffled - size: " + size);
            System.out.println("ixListShuffled - ixListShuffled.length: " + ixListShuffled.length);
        }

        // Loop through all cells randomly.
        // Keep opening them until the grid percolates
        int i = 0;
        while (!grid.percolates() && i < ixListShuffled.length) {
            // Select a random cell...
            int[] gridCoord = getGridCoordinates(ixListShuffled[i]);
            int row = gridCoord[0];
            int col = gridCoord[1];

            // ... and open it
            if (isDebugMode) {
                System.out.println("open - ixListShuffled[i]: " + ixListShuffled[i]);
                System.out.println("open - index: " + i);
                System.out.println("open - row: " + row + " | col: " + col);
            }

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
                    "The grid size and the amount of trials must be a positive integer!");
        }
    }
}
