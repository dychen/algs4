/*
 * Daniel Chen
 * 8/26/13
 */

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    /*
     * Constructor. Performs T percolation experiments on NxN grids.
     * Input N: The size of the grid.
     * Input T: The number of trials.
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Inputs must be positive.");
        }

        double[] numOpenSites = new double[T];
        int counts;
        int openCellIdx;
        int row;
        int col;
        // Perform percolation experiments
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            counts = 0;
            while (!p.percolates()) {
                p.openRandom();
                counts++;
            }
            numOpenSites[i] = ((double) counts) / (N * N);
        }
        // Calculate statistics.
        mean = StdStats.mean(numOpenSites);
        stddev = StdStats.stddev(numOpenSites);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(T);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(T);
    }

    /*
     * Returns the sample mean from the percolation experiments.
     * Output: Sample mean.
     */
    public double mean() {
        return mean;
    }

    /*
     * Returns the same standard deviation from the percolation experiments.
     * Output: Sample standard deviation.
     */
    public double stddev() {
        return stddev;
    }

    /*
     * Returns the lower bound of the 95% CI from the percolation experiments.
     * Output: Lower bound of the confidence interval.
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /*
     * Returns the upper bound of the 95% CI from the percolation experiments.
     * Output: Upper bound of the confidence interval.
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    /*
     * Main method. Takes two command-line arguments, N and T, which should be 
     * positive integers.
     * Input args: Two positive integers (N T) from the command line 
     * (e.g. 400 100)
     */
    public static void main(String[] args) {
        StdOut.println("Enter the grid dimension N "
                        + "(must be a positive integer): ");
        int N = StdIn.readInt();
        StdOut.println("Enter the number of trials T "
                        + "(must be a positive integer): ");
        int T = StdIn.readInt();
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(N, T);
        double elapsedTime = sw.elapsedTime();
        StdOut.printf("mean                    = %f\n", ps.mean());
        StdOut.printf("stddev                  = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", ps.confidenceLo(),
                      ps.confidenceHi());
        StdOut.printf("Elapsed Time            = %fs\n", elapsedTime);
    }
}