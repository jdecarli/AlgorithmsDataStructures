/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class KdTree {

    private int size;
    private KdNode root;

    private static class KdNode {

        private Point2D p;
        private RectHV rect;
        private boolean keyIsX;
        private KdNode parent;
        private KdNode left, right;

        // ***********************************************
        public KdNode(Point2D p) {
            // Root Node constructor
            this.p = p;
            this.rect = new RectHV(0.0, 0.0, 1.0, 1.0);

            this.parent = null;
            this.keyIsX = true;
            this.left = null;
            this.right = null;

        }

        // ***********************************************
        public KdNode(Point2D p, RectHV rect, KdNode parent) {
            // Non-Root Node constructor: with a parent Node
            if (p == null || rect == null) {
                throw new java.lang.IllegalArgumentException();
            }
            this.p = p;
            this.rect = rect;
            this.parent = parent;
            this.keyIsX = !this.parent.keyIsX;
            this.left = null;
            this.right = null;
        }

        // ***********************************************
        public KdNode put(Point2D pNew) {
            double xmin, ymin, xmax, ymax;
            // IF Key = x
            if (this.keyIsX) {
                // IF p_x < pNew_x --> GO LEFT
                if (pNew.x() < this.p.x()) {
                    // IF No Left Child
                    if (this.left == null) {

                        xmin = this.rect.xmin();
                        xmax = this.p.x();
                        ymin = this.rect.ymin();
                        ymax = this.rect.ymax();

                        RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
                        this.left = new KdNode(pNew, rectChild, this);
                        return this.left;
                    }
                    // ELSE --> Recursive call:
                    // Pass the pNew further down the tree
                    else {
                        return this.left.put(pNew);
                    }
                }
                // ELSE --> GO RIGHT
                else {
                    // IF No Right Child
                    if (this.right == null) {

                        xmin = this.p.x();
                        xmax = this.rect.xmax();
                        ymin = this.rect.ymin();
                        ymax = this.rect.ymax();

                        RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
                        this.right = new KdNode(pNew, rectChild, this);
                        return this.right;
                    }
                    // ELSE --> Recursive call:
                    // Pass the pNew further down the tree
                    else {
                        return this.right.put(pNew);
                    }
                }
            }
            // ELSE (If Key = y)
            else {
                if (pNew.y() < this.p.y()) {
                    // IF No Left Child
                    if (this.left == null) {
                        xmin = this.rect.xmin();
                        xmax = this.rect.xmax();
                        ymin = this.rect.ymin();
                        ymax = this.p.y();

                        RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
                        this.left = new KdNode(pNew, rectChild, this);
                        return this.left;
                    }
                    // ELSE --> Recursive call:
                    // Pass the pNew further down the tree
                    else {
                        return this.left.put(pNew);
                    }

                }
                else {
                    // IF No Right Child
                    if (this.right == null) {
                        xmin = this.rect.xmin();
                        xmax = this.rect.xmax();
                        ymin = this.p.y();
                        ymax = this.rect.ymax();

                        RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
                        this.right = new KdNode(pNew, rectChild, this);
                        return this.right;
                    }
                    // ELSE --> Recursive call:
                    // Pass the pNew further down the tree
                    else {
                        return this.right.put(pNew);
                    }
                }
            }

        }
    }

    // ***********************************************
    // construct an empty set of points
    public KdTree() {
        this.size = 0;
        this.root = null;
    }

    // ***********************************************
    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // ***********************************************
    // number of points in the set
    public int size() {
        return this.size;
    }

    // ***********************************************
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (this.size == 0) {
            this.root = new KdNode(p);

        }
        else {
            this.root.put(p);
        }
        this.size++;
    }

    // ***********************************************
    // does the set contain point p?
    public boolean contains(Point2D p) {
        return this.get(p) != null;
    }

    // ***********************************************
    // draw all points to standard draw
    public void draw() {
        KdNode n = this.root;
        drawNode(n);
    }

    // ***********************************************
    private void drawNode(KdNode n) {
        if (n == null) return; // base case
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(n.p.x(), n.p.y());
        StdDraw.setPenRadius();
        // Draw the partition line
        if (n.keyIsX) {
            // Draw the vertical line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        }
        else {
            // Draw the horizontal line
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
        // Draw the left child
        drawNode(n.left);
        // Draw the right child
        drawNode(n.right);
    }

    // ***********************************************
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        KdNode n = this.root;
        Queue<Point2D> q = new Queue<Point2D>();
        rangeRecursive(n, q, rect);

        return q;
    }

    // ***********************************************
    private void rangeRecursive(KdNode node,
                                Queue<Point2D> queue, RectHV rect) {
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                queue.enqueue(node.p);
            }
            if (node.left != null) {
                rangeRecursive(node.left, queue, rect);
            }
            if (node.right != null) {
                rangeRecursive(node.right, queue, rect);
            }
        }
    }

    // ***********************************************
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        KdNode n = this.root;
        if (this.root == null) {
            throw new java.lang.IllegalArgumentException("No neighbors: empty tree!");
        }
        Point2D pNearest = n.p;
        pNearest = nearestRecursive(p, n, pNearest);
        return pNearest;
    }

    private Point2D nearestRecursive(Point2D p, KdNode node, Point2D pNearest) {
        // Sanity check:
        // IF the node is NULL,
        // THEN return the current pNearest
        if (node == null) {
            return pNearest;
        }
        // -----------------------------------------------------------------
        // Update the pNearest if the selected node's point is
        // closer to the point p than pNearest
        double minDistSquared = pNearest.distanceSquaredTo(p);
        double distSquared = node.p.distanceSquaredTo(p);

        if (distSquared < minDistSquared) {
            minDistSquared = distSquared;
            pNearest = node.p;
        }
        // -----------------------------------------------------------------
        // Sanity check:
        // IF both child nodes are NULL,
        // THEN return the current pNearest
        if (node.left == null && node.right == null) {
            return pNearest;
        }
        // IF LEFT node is NULL, OR
        // LEFT Rectangle is too far from p (prune the LEFT branch)
        // --> check the RIGHT ONLY, recursively
        else if (node.left == null ||
                node.left.rect.distanceSquaredTo(p) > minDistSquared) {
            pNearest = nearestRecursive(p, node.right, pNearest);
        }
        // IF RIGHT node is NULL, OR
        // RIGHT Rectangle is too far from p (prune the RIGHT branch)
        // --> check the LEFT ONLY, recursively
        else if (node.right == null ||
                node.right.rect.distanceSquaredTo(p) > minDistSquared) {
            pNearest = nearestRecursive(p, node.left, pNearest);
        }


        // If pruning is not possible at this stage
        // --> check both branches
        // IF Splitting by X
        else if (node.keyIsX) {
            // IF the point p lies LEFT of the split line
            if (p.x() < node.p.x()) {
                pNearest = nearestRecursive(p, node.left, pNearest);
                pNearest = nearestRecursive(p, node.right, pNearest);
            }
            // IF the point p lies RIGHT of the split line,
            // OR on the line
            else {
                pNearest = nearestRecursive(p, node.right, pNearest);
                pNearest = nearestRecursive(p, node.left, pNearest);
            }
        }
        // IF Splitting by Y
        else {
            // IF the point p lies BELOW the split line
            if (p.y() < node.p.y()) {
                pNearest = nearestRecursive(p, node.left, pNearest);
                pNearest = nearestRecursive(p, node.right, pNearest);
            }
            // IF the point p lies ABOVE the split line,
            // OR on the line
            else {
                pNearest = nearestRecursive(p, node.right, pNearest);
                pNearest = nearestRecursive(p, node.left, pNearest);
            }
        }

        return pNearest;
    }

    // ***********************************************
    // unit testing of the methods (optional)
    public static void main(
            String[] args) {
        unitTestCountAndDraw(args, true);
        unitTestRange(args, new RectHV(0.02, 0.02, 0.6, 0.6), true);
    }

    // ***********************************************
    private KdNode get(Point2D p) {
        KdNode n = this.root;
        boolean keyIsX = true;
        while (n != null) {
            if (keyIsX) {
                if (p.x() < n.p.x()) {
                    n = n.left;
                }
                else if (p.x() > n.p.x()) {
                    n = n.right;
                }
                else {
                    if (p.y() == n.p.y()) {
                        return n;
                    }
                    else {
                        return null;
                    }
                }
            }
            else {
                if (p.y() < n.p.y()) {
                    n = n.left;
                }
                else if (p.y() > n.p.y()) {
                    n = n.right;
                }
                else {
                    if (p.x() == n.p.x()) {
                        return n;
                    }
                    else {
                        return null;
                    }
                }
            }
            // Update the key flag:
            keyIsX = !keyIsX;
        }
        return null;
    }

    // ***********************************************
    private static void unitTestCountAndDraw(String[] args, boolean draw) {
        KdTree kdt = new KdTree();

        for (String filename : args) {
            StdOut.println("------------------------------------------------");
            StdOut.println("TEST: Count & Draw");
            StdOut.println("------------------------------------------------");
            StdOut.println("Reading points from the file: " + filename);
            StdOut.println("------------------------------------------------");
            In in = new In(filename);
            int ix = 0;
            while (in.hasNextLine()) {

                try {
                    double x = in.readDouble();
                    double y = in.readDouble();
                    ix++;
                    StdOut.printf("%10d. (x, y) = (%8.6f, %8.6f)\n", ix, x, y);

                    kdt.insert(new Point2D(x, y));
                }
                catch (NoSuchElementException e) {
                    break;
                }

            }
            StdOut.println("------------------------------------------------");
            StdOut.println("Number of points: " + kdt.size());
            StdOut.println("------------------------------------------------");

            if (draw) {
                StdOut.println("Drawing the points... ");
                kdt.draw();
                StdOut.println("------------------------------------------------");
            }

        }
    }

    // ***********************************************
    private static void unitTestRange(String[] args, RectHV rect, boolean draw) {
        KdTree kdt = new KdTree();

        StdOut.println("------------------------------------------------");
        StdOut.println("------------------------------------------------");
        StdOut.println("TEST: Range");

        for (String filename : args) {

            StdOut.println("------------------------------------------------");
            StdOut.println("Reading points from the file: " + filename);
            StdOut.println("------------------------------------------------");
            In in = new In(filename);
            int ix = 0;
            while (in.hasNextLine()) {

                try {
                    double x = in.readDouble();
                    double y = in.readDouble();
                    ix++;

                    kdt.insert(new Point2D(x, y));
                }
                catch (NoSuchElementException e) {
                    break;
                }

            }

            if (draw) {
                StdDraw.setPenColor(StdDraw.MAGENTA);
                StdDraw.setPenRadius(0.005);
                // This option ("draw a rectangle") didn't work very well...
                // StdDraw.rectangle(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax());

                // Draw the rectangle as 4 lines
                StdDraw.line(rect.xmin(), rect.ymin(), rect.xmin(), rect.ymax());
                StdDraw.line(rect.xmin(), rect.ymax(), rect.xmax(), rect.ymax());
                StdDraw.line(rect.xmax(), rect.ymin(), rect.xmax(), rect.ymax());
                StdDraw.line(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymin());


                // Reset the radius before drawing the points
                StdDraw.setPenRadius(0.015);
            }

            StdOut.println("Listing the points in the rectangle: " + rect.toString());
            StdOut.println("------------------------------------------------");
            ix = 0;
            for (Point2D pp : kdt.range(rect)) {
                ix++;
                StdOut.printf("%10d. (x, y) = (%8.6f, %8.6f)\n",
                              ix, pp.x(), pp.y());
                if (draw) {
                    StdDraw.point(pp.x(), pp.y());
                }

            }
        }
    }
}
