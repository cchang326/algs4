/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wqf;
    private int n;
    private boolean[][] opened;
    private int numSitesOpened;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n<=0!");
        }
        wqf = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n + 1][n + 1];
        this.n = n;

        // Connect the top and bottom virtual sites
        for (int i = 1; i <= n; i++) {
            wqf.union(0, i);
        }
        for (int i = n * n; i >= n * n - n; i--) {
            wqf.union(n * n + 1, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isLegalCoordinate(row, col)) {
            throw new IllegalArgumentException("Invalid coordinate!");
        }

        int currIndex = getUFIndex(row, col);

        opened[row][col] = true;
        numSitesOpened++;

        // left
        if (col > 1 && isOpen(row, col - 1)) {
            wqf.union(currIndex, getUFIndex(row, col - 1));
        }
        // top
        if (row > 1 && isOpen(row - 1, col)) {
            wqf.union(currIndex, getUFIndex(row - 1, col));
        }
        // right
        if (col < n && isOpen(row, col + 1)) {
            wqf.union(currIndex, getUFIndex(row, col + 1));
        }
        // bottom
        if (row < n && isOpen(row + 1, col)) {
            wqf.union(currIndex, getUFIndex(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isLegalCoordinate(row, col)) {
            throw new IllegalArgumentException("Invalid coordinate!");
        }
        return opened[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isLegalCoordinate(row, col)) {
            throw new IllegalArgumentException("Invalid coordinate!");
        }

        return opened[row][col] && wqf.find(getUFIndex(row, col)) == wqf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numSitesOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqf.find(n * n + 1) == wqf.find(0);
    }

    private int getUFIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    private boolean isLegalCoordinate(int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(4);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(4, 3);
        StdOut.println("opened " + perc.numberOfOpenSites());
        StdOut.println("percolates? " + perc.percolates());
        perc.open(3, 3);
        StdOut.println("opened " + perc.numberOfOpenSites());
        StdOut.println("percolates? " + perc.percolates());
    }
}
