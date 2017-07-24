import edu.princeton.cs.algs4.Queue;
import java.lang.Math;

public class Board {
  private final char[][] tiles;
  private final int boardDimension;

  /**
   * input a block to initialize the board.
   * 
   * @param blocks
   *          to initialize the specific board
   */
  public Board(int[][] blocks) {
    boardDimension = blocks.length;
    tiles = new char[blocks.length][blocks[0].length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[i].length; j++) {
        tiles[i][j] = (char) blocks[i][j];
      }
    }
  } // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j)

  /**
   * Method to get the dimension of the board.
   * 
   * @return the dimension of the board
   */
  public int dimension() {
    return boardDimension;
  } // board dimension n

  /**
   * Calculate the hamming distance between the current board and the goal
   * board.
   * 
   * @return distance
   */
  public int hamming() {
    int hammingNum = 0;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if ((i * (boardDimension) + j + 1) != (int) tiles[i][j] && (int) tiles[i][j] != 0) {
          hammingNum++;
        }
      }
    }
    return hammingNum;
  } // number of blocks out of place

  /**
   * Calculate the mahattan distance between the current board and the goal
   * board.
   * 
   * @return it
   */
  public int manhattan() {
    int soloDistance = 0;
    int manhattanNum = 0;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if (tiles[i][j] != 0) {
          int rowDistance = Math.abs((((int) tiles[i][j] - 1) / boardDimension) - i);
          int columnDistance = Math.abs(((int) tiles[i][j] - 1) % boardDimension - j);
          soloDistance = rowDistance + columnDistance;
          manhattanNum += soloDistance;
        }
        soloDistance = 0;
      }
    }
    return manhattanNum;
  } // sum of Manhattan distances between blocks and goal

  public boolean isGoal() {
    return hamming() == 0;
  } // is this board the goal board?

  // 
  /**
   * get a twin board.
   * @return a board that is obtained by exchanging any pair of blocks
   */
  public Board twin() {
    int[][] twinBlock = copyBoards();
    int i = 0;
    int j = 0;
    while (twinBlock[i][j] == 0 || twinBlock[i][j + 1] == 0 && i < twinBlock.length) {
      j++;
      if (j >= twinBlock.length - 1) {
        i++;
        j = 0;
      }

    }
    exchangeBlock(twinBlock, i, j, i, j + 1);

    return new Board(twinBlock);
  }

  // exchange two items from the same block.
  private void exchangeBlock(int[][] board, int firsti, int firstj, int secondi, int secondj) {
    int temp = board[firsti][firstj];
    board[firsti][firstj] = board[secondi][secondj];
    board[secondi][secondj] = temp;

  }

  private int[][] copyBoards() {
    int[][] copyBoard = new int[tiles.length][tiles[0].length];
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        copyBoard[i][j] = (int) tiles[i][j];
      }
    }
    return copyBoard;
  }

  /**
   * Compare object y to the original board.
   */
  public boolean equals(Object y) {
    if (y == this) {
      return true;
    } else if (y == null || y.getClass() != this.getClass()) {
      return false;
    } else {
      Board that = (Board) y;
      if (that.boardDimension != this.boardDimension) {
        return false;
      } else {
        for (int i = 0; i < this.tiles.length; i++) {
          if (this.tiles[i].length != that.tiles[i].length) {
            return false;
          }
          for (int j = 0; j < this.tiles[i].length; j++) {
            if (this.tiles[i][j] != that.tiles[i][j]) {
              return false;
            }
          }
        }
      }
    }

    return true;
  }

  /**
   * get neighbor boards.
   * 
   * @return a collection of boards
   */
  public Iterable<Board> neighbors() {
    Queue<Board> neighbors = new Queue<>();

    int indexZero = 1;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if ((int) tiles[i][j] == 0) {
          indexZero = i * boardDimension + j + 1;
        }
      }
    }

    int row = (indexZero - 1) / boardDimension;
    int column = (indexZero - 1) % boardDimension;

    if (row - 1 >= 0) {
      int[][] temp = copyBoards();
      exchangeBlock(temp, row, column, row - 1, column);
      neighbors.enqueue(new Board(temp));
    }
    if (column - 1 >= 0) {
      int[][] temp = copyBoards();
      exchangeBlock(temp, row, column, row, column - 1);
      neighbors.enqueue(new Board(temp));
    }
    if (row + 1 < tiles.length) {
      int[][] temp = copyBoards();
      exchangeBlock(temp, row, column, row + 1, column);
      neighbors.enqueue(new Board(temp));
    }
    if (column + 1 < tiles[0].length) {
      int[][] temp = copyBoards();
      exchangeBlock(temp, row, column, row, column + 1);
      neighbors.enqueue(new Board(temp));
    }

    return neighbors;
  }
  
  /**
   * Returns a string representation of this transaction.
   * @return a string representation of this transaction.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(boardDimension + "\n");
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        s.append(String.format("%2d ", (int)tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  } // string representation of this board (in the output format specified below)

}