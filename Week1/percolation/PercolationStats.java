/* *****************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 1: PercolationStats
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // enable/disable debug mode
    private static boolean isDebugMode;

    private int size;
    private int nTrials;
    private double[] probList;

    public PercolationStats(int n,
                            int trials)    // perform trials independent experiments on an n-by-n grid
    {
        validateInput(n, trials);

        // Size of the grid
        size = n;

        // Number of the MC trials
        nTrials = trials;

        // Instantiate the array
        probList = new double[nTrials];

        this.runAllTrials();

    }

    public double mean()                          // sample mean of percolation threshold
    {
        double m = StdStats.mean(probList);
        return m;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        double s = StdStats.stddev(probList);
        return s;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double m = StdStats.mean(probList);
        double s = StdStats.stddev(probList);
        double lo = m - 1.96 * s / Math.sqrt(nTrials);
        return lo;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double m = StdStats.mean(probList);
        double s = StdStats.stddev(probList);
        double hi = m + 1.96 * s / Math.sqrt(nTrials);
        return hi;
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

        // stats.runAllTrials();
        System.out.println("mean                                = " + stats.mean());
        System.out.println("stddev                              = " + stats.stddev());
        System.out.println(
                "95% confidence interval             = [" + stats.confidenceLo() + ", " + stats
                        .confidenceHi() + "]");

        System.out.println("\nElapsed time: " + stopwatch.elapsedTime());
    }

    private int[] getGridCoordinates(int ix) {
        /**
         * Converts the flat index of the grid to a
         * (row, col) pair of coordinates.
         * @param (int) ix: flat grid index
         * @return (int[]) (row, col)
         */


        int row;
        int col;

        if (ix % size == 0) { // last column
            row = ix / size;
        }
        else {
            row = ix / size + 1;
        }

        col = ix - (row - 1) * size;

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

    private void runAllTrials() {
        for (int i = 0; i < nTrials; i++) {
            probList[i] = runNewTrial();
        }
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

        if (isDebugMode) {
            System.out.println("Estimated Probability: " + prob);
        }

        return prob;
    }

    private void validateInput(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "The grid size and the amount of trials must be a positive integer!");
        }
    }
}
