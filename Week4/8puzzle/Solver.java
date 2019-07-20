/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {


    private Queue<Board> moves;

    private boolean isSolvable;

    private int numOfMoves;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        MinPQ<Board> pqA = new MinPQ<Board>();
        MinPQ<Board> pqB = new MinPQ<Board>();

        Queue<Board> movesA = new Queue<Board>();
        Queue<Board> movesB = new Queue<Board>();

        pqA.insert(initial);
        pqB.insert(initial.twin());

        while (true) {

            // A) Solve the Board A (initial)

            // Get the best move
            Board nextMoveA = pqA.delMin();

            // Add the move to the list (queue) of moves
            movesA.enqueue(nextMoveA);

            // Check if it's solved
            if (nextMoveA.isGoal()) {
                this.isSolvable = true;
                this.numOfMoves = movesA.size();
                this.moves = movesA;
                break;
            }

            // Get the available moves after the nextMove
            // (i.e., neighbors of that Board, or children of that node
            // on the searcg tree)
            // and add them to the PQ
            for (Board neighbor : nextMoveA.neighbors()) {
                pqA.insert(neighbor);
            }

            // B) Solve the Board B (Twin of the initial)

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
    }

}
