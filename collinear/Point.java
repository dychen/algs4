/*************************************************************************
 * Name: Daniel Chen
 * Email: daniel.young.chen@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:    java Point (runs test cases)
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (this.y == that.y)
            return 0.0;
        else
            return ((double) (that.y - this.y)) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        // Assume the coordinates of this and that will be different.
        int xCoordCompareTo;
        int yCoordCompareTo = this.y - that.y;
        if (yCoordCompareTo < 0)
            return -1;
        else if (yCoordCompareTo > 0)
            return 1;
        else
            xCoordCompareTo = this.x - that.x;
            if (xCoordCompareTo < 0)
                return -1;
            else if (xCoordCompareTo > 0)
                return 1;
            else
                return 0;   
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* A few test cases. This is not comprehensive! */
        Point p1 = new Point(0, 0);
        Point p2 = new Point(2, 0);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(0, 2);
        Point p5 = new Point(1, 1);
        StdOut.println("Testing slopeTo() function.");
        assert p1.slopeTo(p2) == 0.0 && p2.slopeTo(p1) == 0.0;
        assert p1.slopeTo(p3) == 1.0 && p3.slopeTo(p1) == 1.0;
        assert p1.slopeTo(p4) == Double.POSITIVE_INFINITY && p4.slopeTo(p1) == Double.POSITIVE_INFINITY;
        assert p2.slopeTo(p3) == Double.POSITIVE_INFINITY && p3.slopeTo(p2) == Double.POSITIVE_INFINITY;
        assert p2.slopeTo(p4) == -1.0 && p4.slopeTo(p2) == -1.0;
        assert p3.slopeTo(p4) == 0.0 && p4.slopeTo(p3) == 0.0;
        assert p1.slopeTo(p1) == Double.NEGATIVE_INFINITY;
        StdOut.println("Testing compareTo() function.");
        assert p1.compareTo(p2) == -1 && p2.compareTo(p1) == 1;
        assert p1.compareTo(p3) == -1 && p3.compareTo(p1) == 1;
        assert p1.compareTo(p4) == -1 && p4.compareTo(p1) == 1;
        assert p2.compareTo(p3) == -1 && p3.compareTo(p2) == 1;
        assert p2.compareTo(p4) == -1 && p4.compareTo(p2) == 1;
        assert p3.compareTo(p4) == 1 && p4.compareTo(p3) == -1;
        assert p1.compareTo(p1) == 0;
        StdOut.println("Testing Comparator compare() function.");
        Point[] points = new Point[5];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;
        points[4] = p5;
        Arrays.sort(points, 0, 5, p1.SLOPE_ORDER);
        assert points[0] == p1;
        assert points[1] == p2;
        assert points[2] == p3 || points[2] == p5;
        assert points[3] == p3 || points[3] == p5;
        assert p3.slopeTo(p1) == p5.slopeTo(p1);
        assert points[4] == p4;
        Arrays.sort(points, 0, 5, p2.SLOPE_ORDER);
        assert points[0] == p2;
        assert points[1] == p4 || points[1] == p5;
        assert points[2] == p4 || points[2] == p5;
        assert p4.slopeTo(p2) == p5.slopeTo(p2);
        assert points[3] == p1;
        assert points[4] == p3;
    }

    // comparator (used in sorting other Points by slope relative to the current point)
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double cmp = p1.slopeTo(Point.this) - p2.slopeTo(Point.this);
            if (cmp < 0)
                return -1;
            else if (cmp > 0)
                return 1;
            else
                return 0;
        }
    }
}
