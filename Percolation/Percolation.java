/*
 * Daniel Chen
 * 8/26/13
 */

import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;

public class Percolation {

    private int size;
    private boolean[] blocked;
    //private QuickFindUF qfalg;
    private WeightedQuickUnionUF wqualg;

    /* 
     * Constructor. Initializes an NxN grid of blocked cells.
     * Input N: The size of the grid.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be positive.");
        }

        size = N;
        blocked = new boolean[N * N];
        // Initialize the grid of blocked cells.
        for (int i = 0; i < N * N; i++) {
            blocked[i] = true;
        }

        // Initialize the QF/WQU representation of these cells.
        // The first N * N cells represent the corresponding cells in the grid.
        // The N * N cell is the top node and should be connected to each 
        // cell in the top row.
        // The N * N + 1 cell is the bottom node and should be connected to each
        // cell in the bottom row.
        //qfalg = new QuickFindUF(N * N + 2);
        wqualg = new WeightedQuickUnionUF(N * N + 2);
    }

    /*
     * Opens the cell at row i, column j.
     * Input i: The row of the cell (from 1 to N).
     * Input j: The column of the cell (from 1 to N).
     */
    public void open(int i, int j) {
        if (i < 1 || i > this.size || j < 1 || j > this.size) {
            throw new IndexOutOfBoundsException("Indices must be from 1 to N");
        }

        int rmod = i-1;
        int cmod = j-1;
        int mod_index = rmod * size + cmod;
        blocked[mod_index] = false;
        // Connect to left cell
        if (j > 1 && isOpen(i, j-1))
            //qfalg.union(mod_index, rmod * size + (cmod-1));
            wqualg.union(mod_index, rmod * size + (cmod-1));
        // Connect to right cell
        if (j < size && isOpen(i, j+1))
            //qfalg.union(mod_index, rmod * size + (cmod+1));
            wqualg.union(mod_index, rmod * size + (cmod+1));
        // Connect to upper cell
        if (i > 1 && isOpen(i-1, j))
            //qfalg.union(mod_index, (rmod-1) * size + cmod);
            wqualg.union(mod_index, (rmod-1) * size + cmod);
        // Connect to lower cell
        if (i < size && isOpen(i+1, j))
            //qfalg.union(mod_index, (rmod+1) * size + cmod);
            wqualg.union(mod_index, (rmod+1) * size + cmod);
        // If it's on the top row, connect it to the top cell.
        if (i == 1) {
            //qfalg.union(mod_index, size * size);
            wqualg.union(mod_index, size * size);
        }
        // If it's on the bottom row, connect it to the bottom cell.
        if (i == size) {
            //qfalg.union(mod_index, size * size + 1);
            wqualg.union(mod_index, size * size + 1);
        }
    }

    /*
     * Returns whether or not the cell at (i, j) is open.
     * Input i: The row of the cell (from 1 to N).
     * Input j: The column of the cell (from 1 to N).
     * Output: Whether or not the cell is open.
     */
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException("Indices must be from 1 to N");
        }

        int rmod = i-1;
        int cmod = j-1;
        int mod_index = rmod * size + cmod;
        return !blocked[mod_index];
    }

    /*
     * Returns whether or not there exists a percolation from the cell at (i, j)
     * to the top of the grid. That is, it returns true if the cell at (i, j) is
     * in the same set as any open cell in the top row of the grid.
     * Input i: The row of the cell (from 1 to N).
     * Input j: The column of the cell (from 1 to N).
     * Output: Whether or not the system percolates to the input cell.
     */
    public boolean isFull(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException("Indices must be from 1 to N");
        }

        int rmod = i-1;
        int cmod = j-1;
        int mod_index = rmod * size + cmod;
        // Return whether or not the input node is connected to the top node.
        //return qfalg.connected(mod_index, size * size);
        return wqualg.connected(mod_index, size * size);
    }

    /*
     * Returns whether or not the system percolates. That is, it returns true if
     * there exists some cell in the bottom row that is in the same set as any 
     * open cell in the top row of the grid.
     * Output: Whether or not the system percolates.
     */
    public boolean percolates() {
        // Return whether or not the bottom node is connected to the top node.
        //return qfalg.connected(size * size, size * size + 1);
        return wqualg.connected(size * size, size * size + 1);
    }

    /*
     * Chooses a random open cell and opens it.
     */
    public void openRandom() {
        while (true) {
            double rand = StdRandom.random();
            int idx = (int) Math.floor(rand * size * size);
            int row = idx / size + 1; // + 1 because open() params are
            int col = idx % size + 1; // 1-indexed
            if (!isOpen(row, col)) {
                open(row, col);
                break;
            }
        }
    }
}
