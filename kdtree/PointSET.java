/*
 * Daniel Chen
 * 11/5/13
 *
 * Compilation:  javac-algs4 PointSET.java
 * Execution:
 * Dependencies: algs4 standard libraries, RectHV.java
 *
 * Description: A simple treeset representation of points on a 2D plane.
 *              Brute force implementations of range() (points that are in an input
 *              rectangle) and nearest() (nearest point to an input point).
 */

public class PointSET {

    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        double xmin = rect.xmin();
        double ymin = rect.ymin();
        double xmax = rect.xmax();
        double ymax = rect.ymax();
        for (Point2D p : points) {
            if (rect.contains(p))
                q.enqueue(p);
        }
        return q;
    }

    // a nearest neighbor in the set to p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        for (Point2D pIter : points) {
            if (nearest == null
                || pIter.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
                nearest = pIter;
            }
        }
        return nearest;
    }
}
