/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] probability;
    private int n;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        probability = new double[trials];
        this.n = n;

        for (int i = 0; i < trials; i++) {
            probability[i] = runTrial();
        }
    }

    double runTrial() {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n) + 1;
            int col = StdRandom.uniformInt(n) + 1;
            p.open(row, col);
        }

        return (double) (p.numberOfOpenSites()) / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(probability);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 100);
        StdOut.println(ps.mean());
    }

}
