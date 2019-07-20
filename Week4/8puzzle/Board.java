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
                }
            }
        }

        return manhattanDist;
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
        int[] zeroCoordinates = this.getZeroCoordinates();
        int zeroR = zeroCoordinates[0];
        int zeroC = zeroCoordinates[1];

        // Going Up
        if (zeroR - 1 >= 0) {
            Board newBoard = this.getZeroNeighbor(zeroR, zeroC, zeroR - 1, zeroC);
            neighbors.enqueue(newBoard);
        }
        // Going Down
        if (zeroR + 1 <= n - 1) {
            Board newBoard = this.getZeroNeighbor(zeroR, zeroC, zeroR + 1, zeroC);
            neighbors.enqueue(newBoard);
        }
        // Going Right
        if (zeroC + 1 <= n - 1) {
            Board newBoard = this.getZeroNeighbor(zeroR, zeroC, zeroR, zeroC + 1);
            neighbors.enqueue(newBoard);
        }
        // Going Left
        if (zeroC - 1 >= 0) {
            Board newBoard = this.getZeroNeighbor(zeroR, zeroC, zeroR, zeroC - 1);
            neighbors.enqueue(newBoard);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // Clone
        int[][] twin = this.getBoardCopy();

        // Find a good pair and do the swap
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n - 1; col++) {
                if (twin[row][col] != 0 && twin[row][col + 1] != 0) {
                    int buffer = twin[row][col];
                    twin[row][col] = twin[row][col + 1];
                    twin[row][col + 1] = buffer;
                    break;
                }
            }
        }

        // Return a Board
        Board twinBoard = new Board(twin);

        return twinBoard;
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

            // Testing Twin 2 method
            StdOut.println("--------------------------------------------------");
            StdOut.println("List the neighbors:");
            for (Board neighbor : b.neighbors()) {
                StdOut.println(neighbor.toString());
            }
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
        int[][] newTiles = getBoardCopy();

        newTiles[row][col] = 0;
        newTiles[zeroR][zeroC] = keyToSlide;
        Board newBoard = new Board(newTiles);

        return newBoard;
    }

    private int[] getZeroCoordinates() {
        int[] zeroCoordinates = new int[2];

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                if (this.tiles[row][col] == 0) {
                    zeroCoordinates[0] = row;
                    zeroCoordinates[1] = col;
                    break;
                }
            }
        }

        return zeroCoordinates;
    }

    private int[][] getBoardCopy() {
        int[][] newTiles = new int[this.n][this.n];
        for (int r = 0; r < this.n; r++) {
            for (int c = 0; c < this.n; c++) {
                newTiles[r][c] = this.tiles[r][c];
            }
        }

        return newTiles;
    }
}
