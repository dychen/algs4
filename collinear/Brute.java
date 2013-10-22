/*
 * Daniel Chen
 * 10/22/13
 *
 * Compilation:  javac-algs4 Fast.java
 * Execution:    java-algs4 Fast [filename.txt]
 * Dependencies: algs4 standard libraries, Point.java
 *
 * Description: A brute-force O(N^4) solution to find all collinear points on a plane.
 */

import java.util.Arrays;

public class Brute {

    /*
     * If we find 4 collinear points, output these points in the following format:
     * p1 -> p2 -> p3 -> p4
     * Draw these points.
     */
    private static void handleCollinear(Point a, Point b, Point c, Point d) {
        StdOut.printf("%s -> %s -> %s -> %s\n", a.toString(), b.toString(), c.toString(), d.toString());
        a.drawTo(d);
    }

    /*
     * Determine whether 4 points are collinear.
     */
    private static boolean isCollinear(Point a, Point b, Point c, Point d) {
        if (a.slopeTo(b) == a.slopeTo(c))
            return (a.slopeTo(b) == a.slopeTo(d));
        else
            return false;
    }

    /*
     * Finds all 4-tuples of collinear points by iterating over all 4-combinations of points.
     */
    private static void findCollinear(Point[] points, int N) {
        // Assume numPoints >= 4
        for (int i = 0; i < N-3; i++) {
            for (int j = i+1; j < N-2; j++) {
                for (int k = j+1; k < N-1; k++) {
                    for (int l = k+1; l < N; l++) {
                        if (Brute.isCollinear(points[i], points[j], points[k], points[l]))
                            Brute.handleCollinear(points[i], points[j], points[k], points[l]);
                    }
                }
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
        if (numPoints >= 4) {
            Arrays.sort(points);
            Brute.findCollinear(points, numPoints);
        }
        StdDraw.show(0);
    }
}
