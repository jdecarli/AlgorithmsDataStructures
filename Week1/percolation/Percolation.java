/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description: Programming Assignment 1: Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int size;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "The grid size must be a positive integer!");
        }

        size = n;

        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n * n);
    }

    private Integer[] getGridCoordinates(int ix) {

        int row;
        int col;

        row = ix / size + 1;
        col = ix - size * row;

        Integer[] coord = { row, col };

        return coord;
    }

    private int getGridIndex(int row, int col) {
        int ix;

        ix = size * (row - 1) + col;

        return ix;
    }

    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        if (row < 1 | row >= size) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 | col >= size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }
    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        if (row < 1 | row >= size) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 | col >= size) {
            throw new java.lang.IllegalArgumentException("Column index outside the range");
        }

        return false;
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        if (row < 1 | row >= size) {
            throw new java.lang.IllegalArgumentException("Row index outside the range");
        }

        if (col < 1 | col >= size) {
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
