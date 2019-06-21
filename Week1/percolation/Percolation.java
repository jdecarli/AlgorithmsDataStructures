/******************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 1: Percolation
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int size;
    private int numberOfCells;
    private WeightedQuickUnionUF uf;
    private boolean[] cellIsOpen;
    private int numberOfOpenCells;

    // -----------------------------------
    // To Implement:
    // - Move validation
    // - Add open method validations
    // -----------------------------------

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        validateInput(n);

        // Size of the grid
        size = n;

        // Total number of cells: grid plus two virtual ones
        numberOfCells = n * n + 2;

        // Instantiate the UF with numberOfCells cells
        uf = new WeightedQuickUnionUF(numberOfCells);

        // Create an array that keeps track of the cell states (open/closed)
        // Initialize all cells as closed (aka blocked)
        cellIsOpen = new boolean[numberOfCells];
        fillArray(cellIsOpen, false); // Replacing Arrays.fill(cellIsOpen, Boolean.FALSE);

        // Open the virtual-top cell
        cellIsOpen[0] = true;

        numberOfOpenCells = 0; // The virtual-top cell doesn't count
    }

    private Integer[] getGridCoordinates(int ix) {

        if (ix == 0) { // virtual-top
            Integer[] coord = { 0, 1 };
            return coord;
        }

        if (ix == numberOfCells - 1) { // virtual-bottom
            Integer[] coord = { size + 1, size };
            return coord;
        }

        int row;
        int col;

        int ixGrid = ix - 1;

        row = ixGrid / size + 1;
        col = ixGrid - size * row;

        Integer[] coord = { row, col };

        return coord;
    }

    private int getGridIndex(int row, int col) {
        int ix;

        if (row == 0) {
            return 0;
        }

        if (row == size + 1) {
            return numberOfCells - 1;
        }

        ix = size * (row - 1) + col;

        return ix;
    }

    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        validateInput(row, col);

        // Get the cell index
        int ix = getGridIndex(row, col);
        // Switch the cell state to OPEN
        cellIsOpen[ix] = true;
        // Keep track of how many are open
        numberOfOpenCells += 1;

        // Check neighbours (needs refactor - just for first run)
        /// faucet and sink
        if (row == 1) // virtual top
            uf.union(ix, getGridIndex(0, 1));

        if (row == size) // virtual bottom
            uf.union(ix, getGridIndex(size + 1, size));

        /// up
        if (row > 1) {
            uf.union(ix, getGridIndex(row - 1, col));
            numberOfOpenCells++;
        }

        /// down
        if (row < size) {
            uf.union(ix, getGridIndex(row + 1, col));
            numberOfOpenCells++;
        }

        /// left
        if (col > 1) {
            uf.union(ix, getGridIndex(row, col - 1));
            numberOfOpenCells++;
        }

        /// right
        if (col < size) {
            uf.union(ix, getGridIndex(row, col + 1));
            numberOfOpenCells++;
        }
    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        validateInput(row, col);

        int ix = getGridIndex(row, col);

        boolean isOp = cellIsOpen[ix];

        return isOp;
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        validateInput(row, col);

        int ix = getGridIndex(row, col);

        boolean isConnected = uf.connected(0, ix);

        return isConnected;
    }

    public int numberOfOpenSites() // number of open sites
    {
        return numberOfOpenCells;
    }

    public boolean percolates() // does the system percolate?
    {
        boolean perc = uf.connected(0, numberOfCells - 1);
        return perc;
    }

    // Private methods
    private void validateInput(int n) {
        if (n < 0) {
            throw new java.lang.IllegalArgumentException(
                    "The grid size must be a positive integer!");
        }
    }

    private void validateInput(int row, int col) {
        if (row < 0 || row > size + 1) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }
    }

    private void fillArray(boolean[] grid, boolean value) {
        for (int i = 0; i < grid.length; i++)
            grid[i] = value;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
