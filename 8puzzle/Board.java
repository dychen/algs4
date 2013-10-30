/*
 * Daniel Chen
 * 10/29/13
 * 
 * Compilation:  javac-algs4 Board.java
 * Execution:
 * Dependencies: algs4 standard libraries
 *
 * Description: An NxN board of integers for the 8-puzzle (or (N^2-1)-puzzle).
 */

public class Board {

    private int N;
    private int[][] board;
    /* Row and column indices for the empty block, which should be a 0.
     * That is, board[emptyRow][emptyCol] == 0. */
    private int emptyRow;
    private int emptyCol;

    /* Constructor */
    public Board(int[][] blocks) {
        // Assume blocks is a square matrix
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
                board[i][j] = blocks[i][j];
            }
        }
    }

    /* Returns the size of the board. */
    public int dimension() {
        return N;
    }

    /* Returns the hamming distance of the board to a finished board.
     * The hamming distance of a single element is 1 if the element isn't in the
     * right place and 0 if it is.
     */
    public int hamming() {
        int block;
        int total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                block = board[i][j];
                if (block != 0)
                    total += hammingDistance(block, i, j);
            }
        }
        return total;
    }

    /* Returns the manhattan distance of the board to a finished board.
     * The manhattan distance of a single element is the distance in
     * rows + columns of the element to its final position.
     */
    public int manhattan() {
        int block;
        int total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                block = board[i][j];
                if (block != 0)
                    total += manhattanDistance(block, i, j);
            }
        }
        return total;
    }

    /* Returns true if the board is finished. A finished 3x3 board is as follows:
     * =====
     * 1 2 3
     * 4 5 6
     * 7 8 0
     * =====
     */
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    /* Returns a copy of the board with the first two elements of one of the
     * first two rows swapped. */
    public Board twin() {
        int tmp;
        Board twinBoard = new Board(this.copy());
        // Switch row 1 if there's an empty block in first blocks of row 0
        if (board[0][0] == 0 || board[0][1] == 0) {
            twinBoard.swap(1, 0, 1, 1);
        }   
        // Switch row 0 if there's no empty block in first blocks of row 0
        else {
            twinBoard.swap(0, 0, 0, 1);
        }
        return twinBoard;
    }

    /* Returns true if the elements of two board matrices are equal and false
     * otherwise. */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.N != this.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (that.board[i][j] != this.board[i][j])
                    return false;
            }
        }
        return true;
    }

    /* Returns a queue of all the neighboring boards (possible configurations after
     * 1 valid move. A valid move is a move that switches an empty block with an
     * adjacent block. */
    public Iterable<Board> neighbors() {
        // Queue is from algs4 library
        Queue<Board> q = new Queue<Board>();
        Board copy;
        // Swap with upper block (row-1)
        if (emptyRow > 0) {
            copy = new Board(this.copy());
            copy.swap(emptyRow, emptyCol, emptyRow-1, emptyCol);
            q.enqueue(copy);
        }
        // Swap with lower block (row+1)
        if (emptyRow < N-1) {
            copy = new Board(this.copy());
            copy.swap(emptyRow, emptyCol, emptyRow+1, emptyCol);
            q.enqueue(copy);
        }
        // Swap with left block (col-1)
        if (emptyCol > 0) {
            copy = new Board(this.copy());
            copy.swap(emptyRow, emptyCol, emptyRow, emptyCol-1);
            q.enqueue(copy);
        }
        // Swap with right block (col+1)
        if (emptyCol < N-1) {
            copy = new Board(this.copy());
            copy.swap(emptyRow, emptyCol, emptyRow, emptyCol+1);
            q.enqueue(copy);
        }
        return q;
    }

    /* Returns the String representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /* Returns the hamming distance of an element to its final position.
     * 0 if the element is in its final position, 1 if it isn't.
     */
    private int hammingDistance(int block, int i, int j) {
        int goalRow = (block - 1) / N;
        int goalCol = (block - 1) % N;
        if (goalRow == i && goalCol == j)
            return 0;
        return 1;
    }

    /* Returns the manhattan distance of an element to its final position.
     * The difference in rows + columns between the current position and the
     * final position. Equivalently, the fewest number of swaps between
     * the current element and an adjacent element to get to its final
     * position.
     */
    private int manhattanDistance(int block, int i, int j) {
        int goalRow = (block - 1) / N;
        int goalCol = (block - 1) % N;
        return Math.abs(goalRow - i) + Math.abs(goalCol - j);
    }

    /* Returns a copy of the board matrix. */
    private int[][] copy() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                copy[i][j] = board[i][j];
        return copy;
    }

    /* Swaps the positions of two elements at (r1, c1) and (r2, c2) on the
     * board.
     */
    private void swap(int r1, int c1, int r2, int c2) {
        int tmp = board[r1][c1];
        board[r1][c1] = board[r2][c2];
        board[r2][c2] = tmp;
        // Be sure to update (emptyRow, emptyCol) if a 0 was swapped.
        if (board[r1][c1] == 0) {
            emptyRow = r1;
            emptyCol = c1;
        }
        if (board[r2][c2] == 0) {
            emptyRow = r2;
            emptyCol = c2;
        }

    }
}
