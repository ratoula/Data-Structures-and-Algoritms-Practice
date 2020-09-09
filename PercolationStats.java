
/**
 * Percolation Assignment
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final double[] threshold;
    private static final double v = 1.96;

    // perform independent trial on an n-by-n grid
    public PercolationStats(int n, int trials) {
        threshold = new double[trials];
        try {
            for (int i = 0; i < trials; i++) {
                Percolation trial = new Percolation(n);
                while (!trial.percolates()) {
                    int row = StdRandom.uniform(n);
                    int col = StdRandom.uniform(n);

                    if (!trial.isOpen(row, col)) {
                        trial.open(row, col);
                    }
                }
                threshold[i] = (double) trial.numberOfOpenSites() / (n * n);
                
                StdOut.println(i);
                /** for (int j = 0; j < trial.booleanArray.length; j++) {
                    for (int k = 0; k < trial.booleanArray.length; k++) {
                        if (trial.isFull(j,k)) {
                            StdOut.print("Dog ");
                        } else if (trial.booleanArray[j][k]) {
                            StdOut.print("Cat ");
                        } else {
                            StdOut.print("Poo ");
                        }
                    }
                    StdOut.println("");
                }*/
                

            }
        } catch (IllegalArgumentException exc) {
            /** Bad value entered */
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double t;

        double squareRoot = (double) threshold.length / 2;

        do {
            t = squareRoot;
            squareRoot = (t + (threshold.length / t)) / 2;
        } while ((t - squareRoot) != 0);

        return (StdStats.mean(threshold) - v * StdStats.stddev(threshold) / squareRoot);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double t;
        double squareRoot = (double) threshold.length / 2;

        do {
            t = squareRoot;
            squareRoot = (t + (threshold.length / t)) / 2;
        } while ((t - squareRoot) != 0);

        return (StdStats.mean(threshold) + v * StdStats.stddev(threshold) / squareRoot);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats attempt = new PercolationStats(n, t);
        StdOut.println("mean\t\t\t= " + attempt.mean());
        StdOut.println("stddev\t\t\t= " + attempt.stddev());
        StdOut.println("95% confidence interval\t= [" + attempt.confidenceLo() + ", " + attempt.confidenceHi() + "]");
    }

}