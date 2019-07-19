/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        if (tiles == null) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = tiles.length;
        this.tiles = new int[this.n][this.n];

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        // Second (fancy and efficient) implementation:
        int maxNumOfDigits = String.valueOf(this.n * this.n).length();

        StringBuilder sb = new
                StringBuilder(this.n + "\n ");

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                String sTemp = String.valueOf(this.tiles[row][col]);
                for (int i = 0; i < maxNumOfDigits - sTemp.length(); i++) {
                    sb.append(" ");
                }
                sb.append(sTemp);
                sb.append(" ");
            }
            sb.append("\n ");
        }

        String s = sb.toString();
        return s;
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int h = this.n * this.n;

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                int ixGoal = row * this.n + (col + 1);

                if (this.tiles[row][col] == ixGoal) {
                    h--;
                }
            }
        }

        // The last cell in the Goal Board must contain a Zero (i.e. be empty)
        if (this.tiles[this.n - 1][this.n - 1] == 0) {
            h--;
        }

        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == null) // check nullable
            return false;

        if (y == this) // compare with itself
            return true;

        if (y.getClass() != this.getClass()) // check types
            return false;

        Board other = (Board) y; // casting

        if (this.n != other.tiles.length) // check size
            return false;

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                if (this.tiles[row][col] != other.tiles[row][col])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // TEST
            StdOut.println("Board from the file " + filename + ":");
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    StdOut.print(tiles[row][col] + " ");
                }
                StdOut.print("\n");
            }

            // Print the Board
            StdOut.println("Creating the Board object and checking toString():");
            Board b = new Board(tiles);
            StdOut.println(b.toString());
            StdOut.println("Hamming Distance: " + b.hamming());
            StdOut.println("\n");

            // Check equals
            StdOut.println("\n- Check equals");

            int[][] copy = tiles.clone();
            Board sameBoard = new Board(copy);
            copy[0][0] = 5; // to make it disfferent
            Board distinctBoard = new Board(copy);

            StdOut.println("Against null: " + b.equals(null));
            StdOut.println("Against self: " + b.equals(b));
            StdOut.println("Against distinct: " + b.equals(distinctBoard));
            StdOut.println("Against same: " + b.equals(sameBoard));

            StdOut.println("Tiles length: " + tiles.length);
        }

    }

}
