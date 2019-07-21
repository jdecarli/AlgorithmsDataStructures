/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    // TODO: We have to be able to compare two Boards!
    // 1. Implement comparer so the minpq knows how to order
    // 2. Replace minpq<board> with minpq<node>

    // TODO: after test, move to private
    private class Node implements Comparable<Node> {

        private int stepsFromRoot;   // g
        private int heuristicValue;  // f
        private int priorityScore;   // g + f
        private Board board;
        private Board parentBoard;

        public Node(Board board, Board parent, int level) {

            this.board = board;
            this.parentBoard = parent;
            this.stepsFromRoot = level;
            // Using Hamming as heuristic --------------
            this.heuristicValue = this.board.hamming();
            // -----------------------------------------
            this.priorityScore = this.stepsFromRoot + this.heuristicValue;
        }

        public int compareTo(Node that) {

            // check nulls
            if (that == null)
                throw new NullPointerException("argument is null");

            // same values
            if (this.equals(that) || this.priorityScore == that.priorityScore)
                return 0;

            // minor/major
            if (this.priorityScore < that.priorityScore)
                return -1;
            else
                return 1;
        }
    }

    private Queue<Board> moves;

    private boolean isSolvable;

    private int numOfMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        MinPQ<Node> pqA = new MinPQ<Node>();
        MinPQ<Node> pqB = new MinPQ<Node>();

        Queue<Board> movesA = new Queue<Board>();
        Queue<Board> movesB = new Queue<Board>();

        Node initialNode = new Node(initial, null, 0);
        Node initialNodeTwin = new Node(initial.twin(), null, 0);

        pqA.insert(initialNode);
        pqB.insert(initialNodeTwin);

        while (true) {

            nodeUnitTester(initial);
            break;
            // ----------------------------------------

            // A) Solve the Board A (initial)
            // executeMove(pqA, movesA);

            /* TO BE REMOVED ------------------------------------------------
            // Get the best move
            Node nextMoveA = pqA.delMin();

            // Add the move to the list (queue) of moves
            movesA.enqueue(nextMoveA.board);

            // Check if it's solved
            if (nextMoveA.board.isGoal()) {
                this.isSolvable = true;
                this.numOfMoves = movesA.size();
                this.moves = movesA;
                break;
            }

            // Get the available moves after the nextMove
            // (i.e., neighbors of that Board, or children of that node
            // on the searcg tree)
            // and add them to the PQ
            for (Board neighbor : nextMoveA.board.neighbors()) {
                if (neighbor != nextMoveA.parentBoard) {
                    pqA.insert(new Node(neighbor, nextMoveA.board, nextMoveA.stepsFromRoot + 1));
                }

            }
            */

            // B) Solve the Board B (Twin of the initial)
            // executeMove(pqB, movesB);

            /* TO BE REMOVED ------------------------------------------------
            // Get the best move
            Board nextMoveB = pqB.delMin();

            // Add the move to the list (queue) of moves
            movesB.enqueue(nextMoveB);

            // Check if it's solved
            if (nextMoveB.isGoal()) {
                this.isSolvable = false;
                this.numOfMoves = -1;
                this.moves = null;
                break;
            }

            // Get the available moves after the nextMove
            // (i.e., neighbors of that Board, or children of that node
            // on the searcg tree)
            // and add them to the PQ
            for (Board neighbor : nextMoveB.neighbors()) {
                pqA.insert(neighbor);
            }
            */
        }
    }

    private void executeMove(MinPQ<Node> pq, Queue<Board> moves) {
        // A) Solve the Board A (initial)

        // Get the best move
        Node nextMove = pq.delMin();

        // Add the move to the list (queue) of moves
        moves.enqueue(nextMove.board);

        // Check if it's solved
        if (nextMove.board.isGoal()) {
            this.isSolvable = true;
            this.numOfMoves = moves.size();
            this.moves = moves;

            return;
        }

        // Get the available moves after the nextMove
        // (i.e., neighbors of that Board, or children of that node
        // on the searcg tree)
        // and add them to the PQ
        for (Board neighbor : nextMove.board.neighbors()) {
            if (!neighbor.equals(nextMove.parentBoard)) {
                pq.insert(new Node(neighbor, nextMove.board, nextMove.stepsFromRoot + 1));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.numOfMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return this.moves;
    }

    // test client (see below)
    public static void main(String[] args) {

        for (String filename : args) {
            // read in the board specified in the filename
            int[][] tiles = loadTest(filename);

            // Print the Board
            StdOut.println("--------------------------------------------------");
            StdOut.println("Creating the Board object");
            Board b = new Board(tiles);
            StdOut.println(b.toString());
            StdOut.println("--------------------------------------------------");
            StdOut.println("Hamming Distance: " + b.hamming());
            StdOut.println("--------------------------------------------------");
            StdOut.println("Total Manhattan Distance: " + b.manhattan());
            StdOut.println("--------------------------------------------------");

            // Testing comparer
            StdOut.println("\n--------------------------------------------------");
            StdOut.print("Testing comparer");
            Solver solver = new Solver(b); // Test in Solver
            StdOut.println("--------------------------------------------------");
        }
    }

    // TODO: remove this UNIT TEST (Node tester)
    public void nodeUnitTester(Board initial) {
        Node node = new Node(initial, null, 0);
        Node nodeLessPriority = new Node(initial.neighbors().iterator().next(), initial, 1);

        int resultMustBeZero = node.compareTo(node);
        StdOut.println("Compare against self: " + resultMustBeZero);
        int resultMustBeOne = node.compareTo(nodeLessPriority);
        StdOut.println("Compare to neighbour with less priority: " + resultMustBeOne);
        int resultMustBeMinus = nodeLessPriority.compareTo(node);
        StdOut.println("Neighbour compares to parent with more priority: " + resultMustBeMinus);
    }

    private static int[][] loadTest(String filename) {
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

        return tiles;
    }
}
