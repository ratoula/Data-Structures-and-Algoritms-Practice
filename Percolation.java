
/**
 * Percolation Assignment
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uF;
    public boolean[][] booleanArray;
    private int root;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        uF = new WeightedQuickUnionUF(n * n);
        booleanArray = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int n = booleanArray.length;

        booleanArray[row][col] = true;

        if ((row > 0) && booleanArray[row - 1][col]) {
            uF.union((row - 1) * n + col, row * n + col);
        }
        if ((row < (n - 1)) && booleanArray[row + 1][col]) {
            uF.union((row + 1) * n + col, row * n + col);
        }
        if ((col > 0) && booleanArray[row][col - 1]) {
            uF.union(row * n + col - 1, row * n + col);
        }
        if ((col < (n - 1)) && booleanArray[row][col + 1]) {
            uF.union(row * n + col + 1, row * n + col);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            return booleanArray[row][col];
        } catch (IllegalArgumentException exc) {
            return false;
        }

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int n = booleanArray.length;
        try {
            return (uF.find(row * n + col) == root);
        } catch (IllegalArgumentException exc) {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSites = 0;
        for (int i = 0; i < booleanArray.length; i++) {
            for (int j = 0; j < booleanArray[i].length; j++) {
                if (booleanArray[i][j]) {
                    openSites = openSites + 1;
                }
            }
        }
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = booleanArray.length;

        for (int i = 0; i < (n); i++) {
            for (int j = 0; j < (n); j++) {
                if (uF.find(n * n - n + j) == uF.find(i)) {
                    root = uF.find(n * n - n + j);
                    return true;
                }
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        /** empty */
    }
}