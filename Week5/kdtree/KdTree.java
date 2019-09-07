/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    /**
     * Internal class to implement the 2d-tree
     */
    private static class Node {
        private boolean _isXCoord;

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node() {
            // if default constructor
            this(true);
            Debug("Node Constructor with NO args (true)");
        }

        private Node(boolean isXCoord) {
            this._isXCoord = isXCoord;
            Debug("Node Constructor with args | this._isXCoord: " + this._isXCoord);
        }

        /**
         * Method to perform insertion of a point into the Node internal class
         *
         * @param point This is the point to insert that will be inserted according to the 2dtree
         *              rules
         * @param area  This argument is used internally in recursion. No matter what, will be
         *              ignored
         */
        public void insert(Point2D point, RectHV area) {
            if (this.p == null) {
                Debug("insert | empty node");
                this.p = point;
                this.rect = area == null ? new RectHV(0, 0, 1, 1) : area;
                Debug("insert | empty node - rect: " + this.rect);
            }
            else {
                // X or even | at the root we use the x-coordinate
                if (this._isXCoord) {
                    Debug("insert | X");
                    // X | if the point to be inserted has a smaller
                    // x-coordinate than the point at the root
                    if (point.x() < this.p.x()) {
                        Debug("insert | X - go left");
                        // go left
                        if (this.lb == null)
                            this.lb = new Node(!this._isXCoord);

                        // X | overwrite right side
                        this.lb.insert(point, new RectHV(
                                this.rect.xmin(),
                                this.rect.ymin(),
                                this.p.x(),
                                this.rect.ymax()
                        ));
                        Debug("insert | X - rect: " + this.lb.rect);
                    }
                    else {
                        Debug("insert | X - go right");
                        // otherwise go right
                        if (this.rt == null)
                            this.rt = new Node(!this._isXCoord);

                        // X | overwrite left side
                        this.rt.insert(point, new RectHV(
                                this.p.x(),
                                this.rect.ymin(),
                                this.rect.xmax(),
                                this.rect.ymax()
                        ));
                        Debug("insert | X - rect: " + this.rt.rect);
                    }
                }
                // Y or odd | then at the next level, we use the y-coordinate
                else {
                    Debug("insert | Y");
                    // Y | if the point to be inserted
                    // has a smaller y-coordinate than the point in the node
                    if (point.y() < this.p.y()) {
                        Debug("insert | Y - go left");
                        // go left
                        if (this.lb == null)
                            this.lb = new Node(!this._isXCoord);

                        // Y | overwrite top side
                        this.lb.insert(point, new RectHV(
                                this.rect.xmin(),
                                this.rect.ymin(),
                                this.rect.xmax(),
                                this.p.y()
                        ));
                        Debug("insert | Y - rect: " + this.lb.rect);
                    }
                    else {
                        Debug("insert | Y - go right");
                        // otherwise go right); then at the next level the x-coordinate, and so forth.
                        if (this.rt == null)
                            this.rt = new Node(!this._isXCoord);

                        // Y | overwrite bottom side
                        this.rt.insert(point, new RectHV(
                                this.rect.xmin(),
                                this.p.y(),
                                this.rect.xmax(),
                                this.rect.ymax()
                        ));
                        Debug("insert | Y - rect: " + this.rt.rect);
                    }
                }
            }
        }

        /**
         * Method to perform the search thru the 2d-tree
         *
         * @param point Point to be searched thru branches
         * @return A point if found, null if not found
         */
        public Point2D search(Point2D point) {
            // Empty point in the node
            if (this.p == null) {
                Debug("search | empty node");
                return null;
            }
            else {
                // Equals to point in the node
                if (this.p.equals(point))
                    return this.p;

                // X or even | at the root we use the x-coordinate
                if (this._isXCoord) {
                    Debug("search | X");
                    // X | if the point to be inserted has a smaller
                    // x-coordinate than the point at the root
                    if (point.x() < this.p.x()) {
                        Debug("search | X - go left");
                        // go left
                        if (this.lb == null)
                            return null;

                        return this.lb.search(point);
                    }
                    else {
                        Debug("search | X - go right");
                        // otherwise go right
                        if (this.rt == null)
                            return null;

                        return this.rt.search(point);
                    }
                }
                // Y or odd | then at the next level, we use the y-coordinate
                else {
                    Debug("search | Y");
                    // Y | if the point to be inserted
                    // has a smaller y-coordinate than the point in the node
                    if (point.y() < this.p.y()) {
                        Debug("search | Y - go left");
                        // go left
                        if (this.lb == null)
                            return null;

                        return this.lb.search(point);
                    }
                    else {
                        Debug("search | Y - go right");
                        // otherwise go right); then at the next level the x-coordinate, and so forth.
                        if (this.rt == null)
                            return null;

                        return this.rt.search(point);
                    }
                }
            }
        }

        /**
         * Method to draw all of the points to standard draw in black and the subdivisions in red
         * (for vertical splits) and blue (for horizontal splits). This method need not be
         * efficient—it is primarily for debugging
         */
        public void draw() {
            // Empty point in the node
            if (this.p == null) {
                Debug("draw | empty node no draw");
                // no draw - end
            }
            else {
                // Draw point
                Debug("draw | point: " + this.p);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                this.p.draw();

                // X or even | at the root we use the x-coordinate
                if (this._isXCoord) {
                    Debug("draw | X - horizontal line");
                    // Horizontal (blue)
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.setPenRadius();
                    StdDraw.line(this.p.x(), this.rect.ymin(), this.p.x(), this.rect.ymax());
                }
                // Y or odd | then at the next level, we use the y-coordinate
                else {
                    Debug("draw | Y - vertical line");
                    // Vertical (red)
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.setPenRadius();
                    StdDraw.line(this.rect.xmin(), this.p.y(), this.rect.xmax(), this.p.y());
                }

                if (this.lb != null)
                    this.lb.draw();

                if (this.rt != null)
                    this.rt.draw();
            }
        }
    }

    private Node _rootNode;
    private int _size = 0;

    public KdTree() {
        this._rootNode = new Node();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return this.size() == 0;
    }

    public int size()                         // number of points in the set
    {
        return this._size;
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new IllegalArgumentException();

        if (this.contains(p))
            return;

        // increase size
        this._size++;

        // at the root we use the x-coordinate (if the point to be inserted has a smaller
        // x-coordinate than the point at the root, go left; otherwise go right);
        // then at the next level, we use the y-coordinate (if the point to be inserted
        // has a smaller y-coordinate than the point in the node, go left; otherwise go
        // right); then at the next level the x-coordinate, and so forth.

        this._rootNode.insert(p, null);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new IllegalArgumentException();

        // Returns null when not found
        if (this._rootNode.search(p) == null)
            return false;

        return true;
    }

    public void draw()                         // draw all points to standard draw
    {
        // Draw a point (black)
        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.01);

        // TODO: draw lines
        // Horizontal (blue)
        // StdDraw.setPenColor(StdDraw.BLUE);
        // StdDraw.setPenRadius();

        // Vertical (red)
        // StdDraw.setPenColor(StdDraw.RED);
        // StdDraw.setPenRadius();

        // Use StdDraw.setPenColor(StdDraw.BLACK) and StdDraw.setPenRadius(0.01) before before drawing the points;
        // use StdDraw.setPenColor(StdDraw.RED) or StdDraw.setPenColor(StdDraw.BLUE) and StdDraw.setPenRadius()
        // before drawing the splitting lines.

        this._rootNode.draw();
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        // TODO: range


        return new SET<Point2D>();
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        // TODO: nearest
        return new Point2D(0, 0);
    }

    public static void main(String[] args) {
        StdOut.println("-------------------------------------");
        StdOut.println("Initialize");
        StdOut.println("-------------------------------------");

        StdOut.println("Empty -------------------------------");
        KdTree set = new KdTree();
        StdOut.println("Empty (true): " + set.isEmpty());
        StdOut.println("Insert p1");
        Point2D point = new Point2D(0.6, 0.5);
        set.insert(point);

        StdOut.println("Insert p2");
        Point2D point2 = new Point2D(0.5, 0.6);
        set.insert(point2);
        StdOut.println("Insert p3");
        Point2D point3 = new Point2D(0.7, 0.6);
        set.insert(point3);

        StdOut.println("A new level...");
        StdOut.println("Insert p4");
        Point2D point4 = new Point2D(0.4, 0.7);
        set.insert(point4);

        StdOut.println("Empty (false): " + set.isEmpty());
        StdOut.println("Size: " + set.size());


        StdOut.println("\nContains ----------------------------");
        StdOut.println("Insert p5 - same as p4");
        Point2D point5 = new Point2D(0.4, 0.7);
        set.insert(point5);
        StdOut.println("Contains p1 (true): " + set.contains(point5));
        Point2D point6 = new Point2D(0.1, 0.4);
        StdOut.println("Insert p6 - not in group");
        StdOut.println("Contains p3 (false): " + set.contains(point6));

        //set.draw();

        StdOut.println("---- test rect --------");
        double xmin = 0.05;
        double ymin = 0.1;
        double xmax = 0.2;
        double ymax = 0.3;
        RectHV testRec = new RectHV(xmin, ymin, xmax, ymax);
        Debug("testRec: " + testRec.toString());
        //testRec.draw();

        StdOut.println("---- test line --------");
        double x0 = 0;
        double y0 = 0.5;
        double x1 = 0.5;
        double y1 = 0.5;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        //StdDraw.line(x0, y0, x1, y1);
        /*
        StdOut.println("Size --------------------------------");
        StdOut.println("Size (2): " + set.size());

        StdOut.println("Range --------------------------------");
        RectHV range = new RectHV(0.0, 0.0, 0.9, 0.9);
        StdOut.println("Range: " + range);
        for (Point2D p : set.range(range)) {
            StdOut.println(p);
        }

        StdOut.println("Nearest ------------------------------");
        Point2D point4 = new Point2D(0.2, 0.2);
        set.insert(point4);
        StdOut.println("Add p4: " + point4);
        Point2D searchPoint = new Point2D(0.1, 0.1);
        Point2D result = set.nearest(searchPoint);
        StdOut.println("Nearest (0.2, 0.2): " + result);

        // Show ALL points
        //GetAll(set);

        set.draw();
        */
    }

    // TODO: DELETE BELOW METHODS ONCE READY ----------------------

    private static boolean IsDebugEnabled = true;

    private static void Debug(String message) {
        if (IsDebugEnabled)
            StdOut.println("Debug - " + message);
    }

        /*
        // exposed testing method
        private static void GetAll(kdTree set) {
            StdOut.println("\nAll points ------------------------------");
            for (Point2D p : set.GetAll()) {
                StdOut.println(p);
            }
        }

        public Iterable<Point2D> GetAll() {
            return this.internalPointSet;
        }
        */
}
