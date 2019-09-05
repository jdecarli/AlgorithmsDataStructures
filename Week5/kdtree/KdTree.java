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

    // Class to implement 2d-tree
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

        public void insert(Point2D point) {
            if (this.p == null) {
                Debug("insert | empty node");
                this.p = point;
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

                        this.lb.insert(point);
                    }
                    else {
                        Debug("insert | X - go right");
                        // otherwise go right
                        if (this.rt == null)
                            this.rt = new Node(!this._isXCoord);

                        this.rt.insert(point);
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

                        this.lb.insert(point);
                    }
                    else {
                        Debug("insert | Y - go right");
                        // otherwise go right); then at the next level the x-coordinate, and so forth.
                        if (this.rt == null)
                            this.rt = new Node(!this._isXCoord);

                        this.rt.insert(point);
                    }
                }
            }
        }
    }

    private Node _rootNode;
    private int _size = 0;

    public KdTree() {
        _rootNode = new Node();
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

        // TODO: add node
        // at the root we use the x-coordinate (if the point to be inserted has a smaller
        // x-coordinate than the point at the root, go left; otherwise go right);
        // then at the next level, we use the y-coordinate (if the point to be inserted
        // has a smaller y-coordinate than the point in the node, go left; otherwise go
        // right); then at the next level the x-coordinate, and so forth.

        this._rootNode.insert(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new IllegalArgumentException();

        // TODO: search
        return false;
    }

    public void draw()                         // draw all points to standard draw
    {
        // Draw a point (black)
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

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
        /*
        StdOut.println("Contains ----------------------------");
        Point2D point2 = new Point2D(0, 0.9);
        set.insert(point2);
        StdOut.println("Contains p1: " + set.contains(point));
        Point2D point3 = new Point2D(0.1, 0.4);
        StdOut.println("Contains p3 (false): " + set.contains(point3));

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
