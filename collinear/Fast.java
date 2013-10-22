/*
 * Daniel Chen
 * 10/22/13
 *
 * Compilation:  javac-algs4 Fast.java
 * Execution:    java-algs4 Fast [filename.txt]
 * Dependencies: algs4 standard libraries, Point.java
 *
 * Description: A sort-based O(N^2logN) solution to find all collinear points on a plane.
 */

import java.util.Arrays;

public class Fast {

    /*
     * If we find at least 3 other points collinear to the base point, the points will be
     * at points[lo] ... points[hi-1]. Sort and output these points in the following format:
     * p0 -> p1 -> ... -> pN
     * Draw these points.
     */
    private static void handleCollinear(Point[] points, int lo, int hi) {
        // Invariant: min will always be at points[0]
        // Invariant: max will always be at points[hi-1]
        StdOut.printf("%s -> ", points[0].toString());
        Arrays.sort(points, lo, hi);
        for (int k = 0; k < hi-lo; k++) {
            StdOut.printf("%s", points[lo+k].toString());
            if (k != hi-lo-1)
                StdOut.printf(" -> ");
            else
                StdOut.printf("\n");
        }
        points[0].drawTo(points[hi-1]);
    }

    /*
     * Given an array of points and an integer N, the size of the array, find all lines of 4 or more points.
     * Avoid finding subsegments of the lines and avoid finding permutations of the lines.
     * General approach:
     * For each point in the array, sort the rest of the array by slope relative to that point.
     * Then, traverse that array and find all consecutive subarrays of that array of length >= 3 with
     * equal slopes relative to the base point.
     */
    private static void findCollinear(Point[] points, int N) {
        Point[] copy = new Point[N];
        for (int j = 0; j < N; j++)
            copy[j] = points[j];
        // For each point in the array, sort the rest of the array with that point as the base
        // Then, iterate through the rest of the array to find collinear points
        for (int i = 0; i < N; i++) {
            Point base = copy[i];
            // Sort the array by the base
            Arrays.sort(points, 0, N, base.SLOPE_ORDER);
            int lo = 1;
            int hi = 2;
            // base must be smaller than every point on its line (so that we don't get permutations)
            boolean flag = base.compareTo(points[lo]) < 0 ? true : false;
            // For the rest of the array, find the collinear points
            while (hi < N) {
                // If the current point has the same slope as the previously found point:
                // Make sure it's larger than the base point.
                if (points[hi].slopeTo(base) == points[lo].slopeTo(base)) {
                    if (points[hi].compareTo(base) < 0)
                        flag = false;
                }
                // If the current point has a different slope than the previously found point:
                else {
                    // If we found at least 3 collinear points (excluding base):
                    // Handle collinear points
                    // Note: collinear points will be at points[lo], points[lo+1], ..., points[hi-1]
                    // Note: number of collinear points found is hi-lo
                    if (flag && hi - lo >= 3)
                        Fast.handleCollinear(points, lo, hi);
                    // Reset lo, flag
                    lo = hi;
                    flag = base.compareTo(points[lo]) < 0 ? true : false;
                }
                hi++;
            }
            // Check edge case (if the last point has the same slope as the previously found point
            if (points[N-1].slopeTo(base) == points[lo].slopeTo(base)) {
                if (flag && hi - lo >= 3)
                    Fast.handleCollinear(points, lo, hi);
            }
        }
    }

    /*
     * Draw all points from an input file given as a command line argument and find all lines containing
     * 4 or more points.
     */
    public static void main(String[] args) {
        int x, y, numPoints;
        String filename;
        Point p;

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        filename = args[0];
        In file = new In(filename);
        numPoints = file.readInt();
        Point[] points = new Point[numPoints];
        for (int i = 0; i < numPoints; i++) {
            x = file.readInt();
            y = file.readInt();
            p = new Point(x, y);
            p.draw();
            points[i] = p;
        }
        if (numPoints >= 4)
            Fast.findCollinear(points, numPoints);
        StdDraw.show(0);
    }
}
