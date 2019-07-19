/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
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
        int h = this.n * this.n - 1;
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                if (this.tiles[row][col] == 0) {
                    continue;
                }
                int keyGoal = coordToGoalKey(row, col);
                if (this.tiles[row][col] == keyGoal) {
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
        if (isGoal()) return 0;

        int manhattanDist = 0;
        int mismatchCount = 0;
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                if (this.tiles[row][col] == 0) {
                    continue;
                }
                int keyGoal = coordToGoalKey(row, col);
                int key = this.tiles[row][col];
                if (key != keyGoal) {
                    mismatchCount++;
                    int[] goalCoord = tileKeyToGoalCoord(key);
                    int dRow = Math.abs(row - goalCoord[0]);
                    int dCol = Math.abs(col - goalCoord[1]);
                    manhattanDist += (dRow + dCol);

                    // TEST (to be commented out later)
                    StdOut.println(
                            mismatchCount + ") Key = " + key + " (Should be " + keyGoal + ")");
                    StdOut.println("Now  (row, col) = (" + row + "," + col + ")");
                    StdOut.println("Goal (row, col) = (" + goalCoord[0] + "," + goalCoord[1] + ")");
                    StdOut.println("Manhattan Distance =  " + (dRow + dCol));
                    StdOut.println("--------------------------------------------------");
                }
            }
        }

        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        /**
         * 1. Find the tile "0"
         * 2. Determine the location type: Corner, Side, Other
         * 3. Find all adjacent tiles and get their coordinates & keys
         * 4. For each adjacent tile, swap it with "zero" ->
         * -> Use the obtained board to create a "neighbor" Board object
         * -> Add the Board object to an Iterable
         */

        Queue<Board> neighbors = new Queue<Board>();

        // 1. Find the tile "0"
        int zeroR = -1;
        int zeroC = -1;
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                int keyGoal = coordToGoalKey(row, col);
                if (this.tiles[row][col] == 0) {
                    zeroR = row;
                    zeroC = col;
                    break;
                }
            }
        }

        // Going Up
        if (zeroR - 1 >= 0) {
            Board newBoard = getZeroNeighbor(zeroR, zeroC, zeroR - 1, zeroC);
            neighbors.enqueue(newBoard);
        }
        // Going Down
        if (zeroR + 1 <= n - 1) {
            Board newBoard = getZeroNeighbor(zeroR, zeroC, zeroR + 1, zeroC);
            neighbors.enqueue(newBoard);
        }
        // Going Right
        if (zeroC + 1 <= n - 1) {
            Board newBoard = getZeroNeighbor(zeroR, zeroC, zeroR, zeroC + 1);
            neighbors.enqueue(newBoard);
        }
        // Going Left
        if (zeroC - 1 >= 0) {
            Board newBoard = getZeroNeighbor(zeroR, zeroC, zeroR, zeroC - 1);
            neighbors.enqueue(newBoard);
        }

        return neighbors;
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
            StdOut.println("--------------------------------------------------");
            StdOut.println("Creating the Board object and testing toString():");
            Board b = new Board(tiles);
            StdOut.println(b.toString());
            StdOut.println("--------------------------------------------------");
            StdOut.println("Hamming Distance: " + b.hamming());
            StdOut.println("--------------------------------------------------");
            StdOut.println("Total Manhattan Distance: " + b.manhattan());
            StdOut.println("--------------------------------------------------");
            StdOut.println("\n");

        }

    }

    private int[] tileKeyToGoalCoord(int key) {

        int[] coord = new int[2];

        if (key == 0) {
            coord[0] = this.n - 1;
            coord[1] = this.n - 1;
            return coord;
        }

        if (key % this.n == 0) {
            coord[0] = key / this.n - 1;
        }
        else {
            coord[0] = key / this.n;
        }
        coord[1] = key - coord[0] * this.n - 1;
        return coord;
    }

    private int coordToGoalKey(int row, int col) {
        if (row == this.n - 1 && col == this.n - 1) {
            return 0;
        }
        return row * this.n + (col + 1);
    }

    private Board getZeroNeighbor(int zeroR, int zeroC, int row, int col) {
        int keyToSlide = this.tiles[row][col];
        int[][] newTiles = this.tiles.clone();
        newTiles[row][col] = 0;
        newTiles[zeroR][zeroC] = keyToSlide;
        Board newBoard = new Board(newTiles);
        return newBoard;
    }
}
