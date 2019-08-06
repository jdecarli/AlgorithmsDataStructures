/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
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

        // -----------------------------------------------
        public KdNode(Point2D p) {
            // Root Node constructor
            this.p = p;
            this.rect = new RectHV(0.0, 0.0, 1.0, 1.0);

            this.parent = null;
            this.keyIsX = true;
            this.left = null;
            this.right = null;

        }

        // -----------------------------------------------
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

        // -----------------------------------------------
        public boolean isKeyX() {
            return keyIsX;
        }

        // -----------------------------------------------
        // public void addChild(Point2D pNew) {
        //     double xmin, ymin, xmax, ymax;
        //     // IF Key = x
        //     if (this.keyIsX) {
        //         if (this.p.x() < pNew.x()) {
        //
        //             xmin = this.rect.xmin();
        //             xmax = this.p.x();
        //             ymin = this.rect.ymin();
        //             ymax = this.rect.ymax();
        //
        //             RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
        //             this.left = new KdNode(pNew, rectChild, this);
        //         }
        //         else {
        //             xmin = this.p.x();
        //             xmax = this.rect.xmax();
        //             ymin = this.rect.ymin();
        //             ymax = this.rect.ymax();
        //
        //             RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
        //             this.right = new KdNode(pNew, rectChild, this);
        //         }
        //     }
        //     else { // ELSE (If Key = y)
        //         if (this.p.y() < pNew.y()) {
        //
        //             xmin = this.rect.xmin();
        //             xmax = this.rect.xmax();
        //             ymin = this.rect.ymin();
        //             ymax = this.p.y();
        //
        //             RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
        //             this.left = new KdNode(pNew, rectChild, this);
        //         }
        //         else {
        //             xmin = this.rect.xmin();
        //             xmax = this.rect.xmax();
        //             ymin = this.p.y();
        //             ymax = this.rect.ymax();
        //
        //             RectHV rectChild = new RectHV(xmin, ymin, xmax, ymax);
        //             this.right = new KdNode(pNew, rectChild, this);
        //         }
        //     }
        //
        // }

        // -----------------------------------------------
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
        // -----------------------------------------------
    }

    // -----------------------------------------------
    // construct an empty set of points
    public KdTree() {
        this.size = 0;
        this.root = null;
    }

    // -----------------------------------------------
    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // -----------------------------------------------
    // number of points in the set
    public int size() {
        return this.size;
    }

    // -----------------------------------------------
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

    // -----------------------------------------------
    // does the set contain point p?
    public boolean contains(Point2D p) {
        return this.get(p) != null;
    }

    // -----------------------------------------------
    // draw all points to standard draw
    public void draw() {
        KdNode n = this.root;
        drawNode(n);
    }

    // -----------------------------------------------
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

    // -----------------------------------------------
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        // TODO
        return null;
    }

    // -----------------------------------------------
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        // TODO
        return null;
    }

    // -----------------------------------------------
    // unit testing of the methods (optional)
    public static void main(
            String[] args) {
        unitTestCountAndDraw(args, true);
    }

    // ****************************************************************
    // private KdNode put(KdNode n, Point2D p) {
    //     if (n == null) {
    //         KdNode newNode = new KdNode(p, rect, parent);
    //         return newNode;
    //     }
    //     else {
    //         // Compare the keys (x or y) and decide which way to go: left/right
    //         if (n.keyIsX) { // IF Key = x
    //             if (p.x() < n.p.x()) {
    //                 n.left = put(n.left, p);
    //             }
    //             else {
    //                 n.right = put(n.right, p);
    //             }
    //         }
    //         else { // IF Key = y
    //             if (p.y() < n.p.y()) {
    //                 n.left = put(n.left, p);
    //             }
    //             else {
    //                 n.right = put(n.right, p);
    //             }
    //         }
    //     }
    //     return n;
    // }

    // -----------------------------------------------
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

    // -----------------------------------------------
    private static void unitTestCountAndDraw(String[] args, boolean draw) {
        KdTree kdt = new KdTree();

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
}
