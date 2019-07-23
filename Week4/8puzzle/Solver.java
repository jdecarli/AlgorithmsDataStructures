/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    // TODO: We have to be able to compare two Boards!
    // 1. Implement comparer so the minpq knows how to order
    // 2. Replace minpq<board> with minpq<node>

    // TODO: after test, move to private
    private class Node implements Comparable<Node> {

        private final int stepsFromRoot;   // g
        private final int heuristicValue;  // f
        private final int priorityScore;   // g + f
        private final Board board;
        private final int cachedHamming;
        private final int cachedManhattan;

        private Node parentNode;

        public Node(Board board, Node inputParentNode, int level) {

            this.board = board;
            this.parentNode = inputParentNode;
            this.stepsFromRoot = level;
            // Using Hamming as heuristic --------------
            this.cachedHamming = this.board.hamming();
            this.cachedManhattan = this.board.manhattan();
            this.heuristicValue = this.cachedManhattan;
            // -----------------------------------------
            this.priorityScore = this.stepsFromRoot + this.heuristicValue;
        }

        public int compareTo(Node that) {

            // check nulls
            if (that == null)
                throw new NullPointerException("argument is null");

            // same values
            if (this.equals(that))
                return 0;

            // minor/major
            if (this.priorityScore < that.priorityScore)
                return -1;
            else if (this.priorityScore > that.priorityScore)
                return 1;

            // Breaking a tie
            // if (this.cachedManhattan < this.cachedManhattan) {
            //     return -1;
            // }
            // else if (this.cachedManhattan > this.cachedManhattan) {
            //     return 1;
            // }
            if (this.cachedHamming < that.cachedHamming) {
                return -1;
            }
            else if (this.cachedHamming > that.cachedHamming) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    // private Queue<Board> moves;
    private Stack<Board> moves;

    private boolean isSolvable;

    private int numOfMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pqA = new MinPQ<Node>();
        MinPQ<Node> pqB = new MinPQ<Node>();

        // Queue<Board> movesA = new Queue<Board>();
        // Queue<Board> movesB = new Queue<Board>();
        // Stack<Board> movesA = new Stack<Board>();
        // Stack<Board> movesB = new Stack<Board>();

        Node initialNode = new Node(initial, null, 0);
        Node initialNodeTwin = new Node(initial.twin(), null, 0);

        pqA.insert(initialNode);
        pqB.insert(initialNodeTwin);

        this.moves = new Stack<Board>();

        // TODO: Once test is done, remove safeguard
        while (true) {

            // int count = 0;
            // while (count < 10) {
            //     count++;

            // TODO: Unit test for Node class ---------
            // nodeUnitTester(initial);
            // break;
            // ----------------------------------------

            // A) Solve the Board A (initial)
            if (!isProcessing(pqA, false))
                break;

            // B) Solve the Board B (Twin of the initial)
            if (!isProcessing(pqB, true))
                break;
        }

        // TODO: Unit Test for Solver
        /*
        StdOut.println("------------------------------------");
        StdOut.println("Solver Test");
        StdOut.println("Is solvable: " + this.isSolvable());
        StdOut.println("Q of moves: " + this.moves());
        StdOut.println("Solution:");
        for (Board b : this.solution())
            StdOut.println(b);
        StdOut.println("------------------------------------");
        */
    }

    // TODO: Test for puzzle08.txt
    // Theory:
    // 1. We remove the queue (mov)
    // 2. Node will receive a parent Node
    // 3. Wait until success
    // 4. Once success, we traverse Nodes until its parent and we add them into a queue
    private boolean isProcessing(MinPQ<Node> pq, boolean isTwin) {
        Node bufferNode;

        // Get the best move
        Node nextMove = pq.delMin();

        // StdOut.println("Level " + nextMove.stepsFromRoot + ". Twin? " + isTwin);
        // StdOut.println(nextMove.board);

        // Add the move to the list (queue) of moves
        // mov.enqueue(nextMove.board);

        // Check if it's solved
        if (nextMove.board.isGoal()) {
            // If twin, return "not solvable" when goal
            this.isSolvable = !isTwin;
            // If Twin, return empty queue
            // this.moves = isTwin ? new Queue<Board>() : mov;
            // this.numOfMoves = isTwin ? this.moves.size() + 1 : this.moves.size();

            if (!isTwin) {  // if we found a solution for the original board
                bufferNode = nextMove;
                this.moves.push(bufferNode.board);

                while (bufferNode.parentNode != null) {
                    this.moves.push(bufferNode.parentNode.board);
                    bufferNode = bufferNode.parentNode;
                }

                this.numOfMoves = this.moves.size();
            }
            else {          // if we solved the twin
                this.moves = null;
                this.numOfMoves = 0;
            }

            return false; // Stop execution, we reached a solution
        }

        // Get the available moves after the nextMove
        // (i.e., neighbors of that Board, or children of that node
        // on the searcg tree)
        // and add them to the PQ
        for (Board neighbor : nextMove.board.neighbors()) {
            if (nextMove.stepsFromRoot == 0 || !neighbor.equals(nextMove.parentNode.board)) {
                pq.insert(new Node(neighbor, nextMove, nextMove.stepsFromRoot + 1));
            }
        }

        return true; // continue execution, solution not found
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.numOfMoves - 1;
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
            StdOut.println("Testing Solver Execution");
            Solver solver = new Solver(b); // Test in Solver
            StdOut.println("--------------------------------------------------");
        }
    }

    // TODO: remove this UNIT TEST (Node tester)
    // private void solverUnitTester(MinPQ<Node> pq, Queue<Board> moves, Boolean isTwin) {
    //     StdOut.println("------------------------------------");
    //     StdOut.println("Execute with original start...");
    //     StdOut.println("before - PQ size: " + pq.size());
    //     StdOut.println("before - moves size: " + moves.size());
    //     boolean executionResult = executeMove(pq, moves, isTwin);
    //     StdOut.println("after - PQ size: " + pq.size());
    //     StdOut.println("after - moves size: " + moves.size());
    //     StdOut.println("Execution result: " + executionResult);
    //     StdOut.println("------------------------------------");
    //
    //     if (!executionResult) {
    //         StdOut.println("Final moves:");
    //         for (Board b : moves) {
    //             StdOut.println(b);
    //         }
    //     }
    // }

    // private void nodeUnitTester(Board initial) {
    //     Node node = new Node(initial, null, 0);
    //     Node nodeLessPriority = new Node(initial.neighbors().iterator().next(), initial, 1);
    //
    //     int resultMustBeZero = node.compareTo(node);
    //     StdOut.println("Compare against self: " + resultMustBeZero);
    //     int resultMustBeOne = node.compareTo(nodeLessPriority);
    //     StdOut.println("Compare to neighbour with less priority: " + resultMustBeOne);
    //     int resultMustBeMinus = nodeLessPriority.compareTo(node);
    //     StdOut.println("Neighbour compares to parent with more priority: " + resultMustBeMinus);
    // }

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
