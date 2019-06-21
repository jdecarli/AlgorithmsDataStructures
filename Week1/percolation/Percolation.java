/******************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 1: Percolation
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;


public class Percolation {

    private int size;
    private int numberOfCells;
    private WeightedQuickUnionUF uf;
    private Boolean[] cellIsOpen;
    private int numberOfOpenCells;
    private int ixVirtualTop;
    private int ixVirtualBottom;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n < 0) {
            throw new java.lang.IllegalArgumentException(
                    "The grid size must be a positive integer!");
        }
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
        cellIsOpen = new Boolean[numberOfCells];
        Arrays.fill(cellIsOpen, Boolean.FALSE);

        // Open the virtual-top cell
        cellIsOpen[0] = true;

        numberOfOpenCells = 0; // The virtual-top cell doesn't count
    }

    private Integer[] getGridCoordinates(int ix) {

        if (ix == ixVirtualTop) { // virtual-top
            Integer[] coord = { 0, 1 };
            return coord;
        }

        if (ix == ixVirtualBottom) { // virtual-bottom
            Integer[] coord = { size + 1, size };
            return coord;
        }

        int row;
        int col;

        row = ix / size + 1;
        col = ix - (row - 1) * size;

        Integer[] coord = { row, col };

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

    private void validateIndexRange(int row, int col) {

        if (row < 0 || row > size + 1) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        validateIndexRange(row, col);

        // Get the cell index
        int ix = getGridIndex(row, col);
        // Switch the cell state to OPEN
        cellIsOpen[ix] = true;
        // Keep track of how many are open
        numberOfOpenCells += 1;
        //

    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        validateIndexRange(row, col);

        int ix = getGridIndex(row, col);

        boolean isOp = cellIsOpen[ix];

        return isOp;
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        validateIndexRange(row, col);

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

    // test client (optional)
    public static void main(String[] args) {

    }
}
