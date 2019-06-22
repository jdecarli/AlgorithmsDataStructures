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
    private int ixVirtualTop;
    private int ixVirtualBottom;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        validateInput(n);

        // Size of the grid
        size = n;

        // Total number of cells: grid plus two virtual ones
        numberOfCells = n * n + 2;

        // Set the indices for the virtual top/bottom sites (cells):
        ixVirtualTop = 0;
        ixVirtualBottom = numberOfCells - 1;

        // Instantiate the UF with numberOfCells cells
        uf = new WeightedQuickUnionUF(numberOfCells);

        // Create an array that keeps track of the cell states (open/closed)
        // Initialize all cells as closed (aka blocked)
        cellIsOpen = new boolean[numberOfCells];
        fillArray(cellIsOpen, false); // Arrays.fill(cellIsOpen, Boolean.FALSE);

        // Open the virtual-top cell
        cellIsOpen[0] = true;

        numberOfOpenCells = 0; // The virtual-top cell doesn't count
    }

    private int[] getGridCoordinates(int ix) {

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

    private int getGridIndex(int row, int col) {
        int ix;

        if (row == 0) {
            return ixVirtualTop;
        }

        if (row == size + 1) {
            return ixVirtualBottom;
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

        // Check neighbors (needs refactor - just for first run)
        /// faucet and sink
        if (row == 1) // virtual top
            uf.union(ix, getGridIndex(0, 1)); //<-- replace with constants

        if (row == size) // virtual bottom
            uf.union(ix, getGridIndex(size + 1, size)); //<-- replace with constants

        /// up
        if (row > 1 && cellIsOpen[getGridIndex(row - 1, col)]) {
            uf.union(ix, getGridIndex(row - 1, col));
        }

        /// down
        if (row < size && cellIsOpen[getGridIndex(row + 1, col)]) {
            uf.union(ix, getGridIndex(row + 1, col));
        }

        /// left
        if (col > 1 && cellIsOpen[getGridIndex(row, col - 1)]) {
            uf.union(ix, getGridIndex(row, col - 1));
        }

        /// right
        if (col < size && cellIsOpen[getGridIndex(row, col + 1)]) {
            uf.union(ix, getGridIndex(row, col + 1));
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
        System.out.println("test");
    }
}
