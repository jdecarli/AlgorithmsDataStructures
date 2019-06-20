/******************************************************************************
 *  Name: KB & JDC
 *  Date:
 *  Description: Programming Assignment 1: Percolation
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;


public class Percolation {

    private int size;
    private int N;
    private WeightedQuickUnionUF uf;
    private Boolean[] cellState;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n < 0) {
            throw new java.lang.IllegalArgumentException(
                    "The grid size must be a positive integer!");
        }
        // Size of the grid
        size = n;
        // Total number of cells: grid plus two virtual ones
        N = n * n + 2;
        // Instantiate the UF with N cells
        uf = new WeightedQuickUnionUF(N);
        // Create an array that keeps track of the cell states (open/closed)
        // Initialize all cells as closed (aka blocked)
        Boolean[] cellState = new Boolean[N];
        Arrays.fill(cellState, Boolean.FALSE);
        // Open the virtual-top cell
        cellState[0] = true;
    }

    private Integer[] getGridCoordinates(int ix) {

        if (ix == 0) { // virtual-top
            Integer[] coord = { 0, 1 };
            return coord;
        }

        if (ix == N - 1) { // virtual-bottom
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
            return N - 1;
        }

        ix = size * (row - 1) + col;

        return ix;
    }

    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        if (row < 0 || row > size + 1) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }
    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        if (row < 0 || row > size + 1) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }

        int ix = getGridIndex(row, col);

        boolean isOp = uf.

        return false;
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        if (row < 0 || row > size + 1) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }

        return false;
    }

    public int numberOfOpenSites() // number of open sites
    {
        return 0;
    }

    public boolean percolates() // does the system percolate?
    {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
