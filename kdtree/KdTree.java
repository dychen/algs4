/*
 * Daniel Chen
 * 11/5/13
 *
 * Compilation:  javac-algs4 KdTree.java
 * Execution:
 * Dependencies: algs4 standard libraries, RectHV.java
 *
 * Description: A 2d-tree representation of points on a 2D plane.
 *              Optimized implementations of range() (points that are in an input
 *              rectangle) and nearest() (nearest point to an input point).
 */


public class KdTree {

    private int size;
    private KdNode root;

    /* Inner node of the 2d-tree */
    private static class KdNode {
        private Point2D p;    // point created for the node
        private RectHV r;     // outer rectangle surrounding the node
        private KdNode left;  // left is also down
        private KdNode right; // right is also up

        public KdNode(Point2D p, RectHV r) {
            this.p = p;
            this.r = r;
        }
    }

    /* Construct an empty 2d-treeset of points */
    public KdTree() {
        size = 0;
        root = null;
    }

    /* Returns true if there are no points in the set, false otherwise */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of points in the set */
    public int size() {
        return size;
    }

    /* Inserts the point p into the 2d-tree.
     * See helper function for details. */
    public void insert(Point2D p) {
        // last param is true if comparing by x-value
        //RectHV baseRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        //root = insert(root, p, baseRect, true);
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    /* Returns true if the 2d-tree contains the input p, false otherwise.
     * See helper function for details. */
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    /* Draws all of the points and lines to standard draw.
     * See helper function for details. */
    public void draw() {
        draw(root, true);
    }

    /* Returns a queue of all points in the 2d-tree inside the input rectangle.
     * See helper function for details. */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    /* Returns the closest points in the 2d-tree to the input point.
     * See helper function for details. */
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return nearest(root, p, root.p, true);
    }

    /* Helper functions */

    /* Approach: 
     * Traverse the tree, alternating between vertical and horizontal nodes,
     * until you reach an empty node and insert there. Keep track of the parent
     * rectangle of each node (a new node will split its parent rectangle either
     * horizontally or vertically, depending on the node, into two subrectangles
     * for its children).
     *
     * Params:
     * node:   Current node to be considered
     * p:      Input point (to be inserted)
     * x0, y0: Rectangle lower bounds
     * x1, y1: Rectangle upper bounds
     * xcmp:   true if the current node is vertical (requires x-val comparison) */
    private KdNode insert(KdNode node, Point2D p, double x0, double y0,
                          double x1, double y1, boolean xcmp) {
        // Insert when you reach an empty location
        if (node == null) {
          size++;
          RectHV r = new RectHV(x0, y0, x1, y1);
          return new KdNode(p, r);
        }
        // If the point already exists, just return
        else if (node.p.x() == p.x() && node.p.y() == p.y()) return node;
        // The current node is vertical: compare x-coordinates
        if (xcmp) {
            double cmp = p.x() - node.p.x();
            if (cmp < 0)
                node.left = insert(node.left, p, x0, y0, node.p.x(), y1, !xcmp);
            else
                node.right = insert(node.right, p, node.p.x(), y0, x1, y1, !xcmp);
        }
        // The current node is horizontal: compare y-coordinates
        else {
            double cmp = p.y() - node.p.y();
            if (cmp < 0)
                node.left = insert(node.left, p, x0, y0, x1, node.p.y(), !xcmp);
            else
                node.right = insert(node.right, p, x0, node.p.y(), x1, y1, !xcmp);
        }
        return node;
    }

    /* Approach:
     * Traverse the tree, alternating between vertical and horizontal nodes,
     * until you either find the node (return true) or reach a null pointer
     * (return false).
     *
     * Params:
     * node: Current node to be considered
     * p:    Input point (checked for inclusion)
     * xcmp: true if the current node is vertical (requires x-val comparison) */
    private boolean contains(KdNode node, Point2D p, boolean xcmp) {
        // false if you didn't find it
        if (node == null) return false;
        // true if you found it
        else if (node.p.x() == p.x() && node.p.y() == p.y()) return true;
        else {
            // The current node is vertical: compare x-coordinates
            if (xcmp) {
                double cmp = p.x() - node.p.x();
                if (cmp < 0) return contains(node.left, p, !xcmp);
                else return contains(node.right, p, !xcmp);
            }
            // The current node is horizontal: compare y-coordinates
            else {
                double cmp = p.y() - node.p.y();
                if (cmp < 0) return contains(node.left, p, !xcmp);
                else return contains(node.right, p, !xcmp);
            }
        }
    }

    /* Approach:
     * Traverse the tree, alternating between vertical and horizontal nodes,
     * drawing each point and the appropriate line containing that point.
     *
     * Params:
     * node:     Current node to be considered
     * drawVert: true if current node is vertical (need to draw vertical line) */
    private void draw(KdNode node, boolean drawVert) {
        if (node == null) return;
        // Draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        // Draw vertical line with x-coordinates of the point and y-coordinates
        // of the parent rectangle
        if (drawVert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.r.ymin(), node.p.x(), node.r.ymax());
        }
        // Draw horizontal line with y-coordinates of the point and x-coordinates
        // of the parent rectangle
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.r.xmin(), node.p.y(), node.r.xmax(), node.p.y());
        }
        // Draw subtrees
        draw(node.left, !drawVert);
        draw(node.right, !drawVert);
    }

    /* Approach:
     * Traverse the tree, enqueueing nodes if they are inside the input rectangle.
     * Only consider subtrees whose parent rectangles intersect the input rectangle.
     *
     * Params:
     * node: Current node to be considered
     * rect: Input rectangle (checked for which points are inside of it)
     * q:    Queue containing the points inside the rectangle */
    private void range(KdNode node, RectHV rect, Queue<Point2D> q) {
        if (node == null) return;
        // If the current point is in the input rectangle, enqueue that point
        if (rect.contains(node.p)) {
            q.enqueue(node.p);
        }
        // Check the left and right subtrees if the input rectangle intersects
        // the current rectangle
        if (rect.intersects(node.r)) {
            range(node.left, rect, q);
            range(node.right, rect, q);
        }
    }

    /* Approach:
     * Traverse the tree, alternating between vertical and horizontal nodes,
     * keeping track of the closest point to the input point found so far.
     * Only consider subtrees whose parent rectangles are closer to the input
     * point than the closest point found so far.
     *
     * Params:
     * node: Current node to be considered
     * p:    Input point (finding closest point to it)
     * c:    Point currently closest to the input point
     * xcmp: true if the current node is vertical (requires x-val comparison) */
    private Point2D nearest(KdNode node, Point2D p, Point2D c, boolean xcmp) {
        Point2D closest = c;
        // If there are no more nodes, return the closest point found so far
        if (node == null) return closest;
        // If the current point is closer than the closest point found so far,
        // update the closest point
        if (node.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
            closest = node.p;
        // If the current rectangle is closer to p than the closest point, check its
        // subtrees
        if (node.r.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            // Find which subtree the p is in
            KdNode near;
            KdNode far;
            if ((xcmp && (p.x() < node.p.x())) || (!xcmp && (p.y() < node.p.y()))) {
                near = node.left;
                far = node.right;
            }
            else {
                near = node.right;
                far = node.left;
            }
            // Check subtree on the same side as p
            closest = nearest(near, p, closest, !xcmp);
            // Check the subtree on the opposite side as p
            closest = nearest(far, p, closest, !xcmp);
        }
        return closest;
    }
}
