import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private Node minNode;
  private Node inNode;

  /**
   * A constructor to solve the 8-puzzle problem.
   * @param initial board
   */
  public Solver(Board initial) {
    MinPQ<Node> minPQ = new MinPQ<>();

    if (initial == null) {
      throw new java.lang.IllegalArgumentException("Null content is not allowed.");
    }
    //minNode = new Node(initial, null);
    inNode = new Node(initial, null);
    Node inNodetwin = new Node(initial.twin(), null);
    minPQ.insert(inNode);
    minPQ.insert(inNodetwin);

    minNode = minPQ.delMin();
    while (!minNode.getBoard().isGoal()) {
      for (Board board : minNode.getBoard().neighbors()) {
        if (minNode.getPrevious() == null) {
          minPQ.insert(new Node(board, minNode));
        } else if (!minNode.getPrevious().getBoard().equals(board)) {
          minPQ.insert(new Node(board, minNode));
        }

      }

      minNode = minPQ.delMin();
    }

  } // find a solution to the initial board (using the A* algorithm)
  
  /**
   * return true if the initial board is solvable, false otherwise.
   * @return true if the initial board is solvable, false otherwise.
   */
  public boolean isSolvable() {
    Node preNode = minNode;
    while (preNode.getPrevious() != null) {
      preNode = preNode.getPrevious();
    }

    if (preNode.getBoard().equals(inNode.getBoard())) {
      return true;
    } else {
      return false;
    }
  } // is the initial board solvable?
  
  /**
   * return the move numbers for solution.
   * @return the move numbers for solution
   */
  public int moves() {
    if (!isSolvable()) {
      return -1;
    } else {
      return minNode.getMove();
    }

  } // min number of moves to solve initial board; -1 if unsolvable
  
  /**
   * return an iterable collection for sequence of boards in a shortest solution.
   * @return an iterable collection for sequence of boards in a shortest solution.
   */
  public Iterable<Board> solution() {
    if (!isSolvable()) {
      return null;
    }else {
      Stack<Board> sequenceBoard = new Stack<>();

      Node preNode = minNode;
      sequenceBoard.push(preNode.getBoard());

      while (preNode.getPrevious() != null) {
        preNode = preNode.getPrevious();
        sequenceBoard.push(preNode.getBoard());
      }

      return sequenceBoard;
    }
  }
  private class Node implements Comparable<Node> {
    private final Node previous;
    private final Board board;
    private int moveNums;

    public Node(Board board, Node previous) {
      this.board = board;
      this.previous = previous;
      if (previous == null) {
        this.moveNums = 0;
      } else {
        this.moveNums = previous.moveNums + 1;
      }
    }

    private Board getBoard() {
      return this.board;
    }

    private Node getPrevious() {
      return this.previous;
    }

    private int getMove() {
      return moveNums;
    }

    @Override
    public int compareTo(Node o) {
      return (this.board.manhattan() - o.board.manhattan()) + (this.moveNums - o.moveNums);
    }

  }
  
  /**
   * Main method.
   */
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }
  } // solve a slider puzzle (given below)
}