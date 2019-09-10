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
        }

        private Node(boolean isXCoord) {
            this._isXCoord = isXCoord;
        }

        /**
         * Method to perform insertion of a point into the Node internal class
         *
         * @param point This is the point to insert that will be inserted according to the 2dtree
         *              rules
         * @param area  This argument is used internally for recursion. No matter what, will be
         *              ignored
         */
        public void insert(Point2D point, RectHV area) {
            if (this.p == null) {
                this.p = point;
                this.rect = area == null ? new RectHV(0, 0, 1, 1) : area;
            }
            else {
                double rectXmin = this.rect.xmin();
                double rectYmin = this.rect.ymin();
                double rectXmax = this.rect.xmax();
                double rectYmax = this.rect.ymax();

                // X or even | at the root we use the x-coordinate
                if (this._isXCoord) {
                    // X | if the point to be inserted has a smaller
                    // x-coordinate than the point at the root
                    if (point.x() < this.p.x()) {
                        // go left
                        if (this.lb == null)
                            this.lb = new Node(!this._isXCoord);

                        // X | overwrite right side
                        this.lb.insert(point, new RectHV(
                                rectXmin,
                                rectYmin,
                                this.p.x(),
                                rectYmax
                        ));
                    }
                    else {
                        // otherwise go right
                        if (this.rt == null)
                            this.rt = new Node(!this._isXCoord);

                        // X | overwrite left side
                        this.rt.insert(point, new RectHV(
                                this.p.x(),
                                rectYmin,
                                rectXmax,
                                rectYmax
                        ));
                    }
                }
                // Y or odd | then at the next level, we use the y-coordinate
                else {
                    // Y | if the point to be inserted
                    // has a smaller y-coordinate than the point in the node
                    if (point.y() < this.p.y()) {
                        // go left
                        if (this.lb == null)
                            this.lb = new Node(!this._isXCoord);

                        // Y | overwrite top side
                        this.lb.insert(point, new RectHV(
                                rectXmin,
                                rectYmin,
                                rectXmax,
                                this.p.y()
                        ));
                    }
                    else {
                        // otherwise go right); then at the next level the x-coordinate, and so forth.
                        if (this.rt == null)
                            this.rt = new Node(!this._isXCoord);

                        // Y | overwrite bottom side
                        this.rt.insert(point, new RectHV(
                                rectXmin,
                                this.p.y(),
                                rectXmax,
                                rectYmax
                        ));
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
                return null;
            }
            else {
                // Equals to point in the node
                if (this.p.equals(point))
                    return this.p;

                // X or even | at the root we use the x-coordinate
                if (this._isXCoord) {
                    // X | if the point to be inserted has a smaller
                    // x-coordinate than the point at the root
                    if (point.x() < this.p.x()) {
                        // go left
                        if (this.lb == null)
                            return null;

                        return this.lb.search(point);
                    }
                    else {
                        // otherwise go right
                        if (this.rt == null)
                            return null;

                        return this.rt.search(point);
                    }
                }
                // Y or odd | then at the next level, we use the y-coordinate
                else {
                    // Y | if the point to be inserted
                    // has a smaller y-coordinate than the point in the node
                    if (point.y() < this.p.y()) {
                        // go left
                        if (this.lb == null)
                            return null;

                        return this.lb.search(point);
                    }
                    else {
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
                // no draw - end
                return;
            }
            else {
                // Draw point
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                this.p.draw();

                // X or even | at the root we use the x-coordinate
                if (this._isXCoord) {
                    // Horizontal (blue)
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.setPenRadius();
                    StdDraw.line(this.p.x(), this.rect.ymin(), this.p.x(), this.rect.ymax());
                }
                // Y or odd | then at the next level, we use the y-coordinate
                else {
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

        /**
         * Method to find all points that belong to a given area
         *
         * @param tree Node to check
         * @param area Area to search
         * @param col  Initialized collection of points
         * @return A collection of points
         */
        public Iterable<Point2D> range(Node tree, RectHV area, SET<Point2D> col) {
            // To find all points contained in a given query rectangle,
            // start at the root and
            // recursively search for points in both subtrees using the following pruning rule:
            // if the query rectangle does not intersect the rectangle corresponding to a node,
            // there is no need to explore that node (or its subtrees).
            // A subtree is searched only if it might contain a point contained in the query
            // rectangle.

            // Empty point in the node
            if (tree.p == null) {
                return col;
            }
            else {
                if (area.contains(tree.p))
                    col.add(tree.p);

                if (tree.lb != null && tree.lb.rect.intersects(area))
                    range(tree.lb, area, col);

                if (tree.rt != null && tree.rt.rect.intersects(area))
                    range(tree.rt, area, col);
            }

            return col;
        }

        /**
         * Method to recursevily search thru the trees for the closest point
         *
         * @param target  Query point
         * @param tree    This arguent is used for recursion. Tree to search
         * @param closest This argument is used for recursion. Closest point so far
         * @return Null if nothing was found (in case of an empty tree). Nearest/Closest point
         */
        public Point2D nearest(Point2D target, Node tree, Point2D closest) {

            // DEBUG -------
            /*
            String closestValue = closest == null ? "null" : closest.toString();
            String nodePoint = tree == null ? "null" : tree.p.toString();
            Debug("nearest | start / target: " + target.toString() + " / closest: " + closestValue
                          + " / tree: " + nodePoint);
            */

            // Flaws in this method:
            // - Closest is wrongly propagated because its inheritance overwrittes the values
            // - AI: break down into 2 methods, one that does recursion, the other that stores
            // and compares the closests points
            // ---------------

            if (tree == null || tree.p == null) {
                return closest;
            }
            else {
                // Same
                if (tree.p.equals(target))
                    return tree.p;

                // Closest null
                if (closest == null)
                    closest = tree.p;
                else {
                    // this.p is closer than `closest`
                    if (tree.p.distanceSquaredTo(target) < closest.distanceSquaredTo(target))
                        closest = tree.p;
                }

                // PRE-CONDITIONS
                // IF closest point discovered so far is closer than the distance between the
                // query point and the rectangle corresponding to a node | CHOOSE THE OTHER ONE
                // search a node only only if it might contain a point that is closer than the
                // best one found so far.
                boolean isLeftNodePossible = tree.lb != null
                        && closest.distanceSquaredTo(target) >= tree.lb.rect
                        .distanceSquaredTo(target);

                boolean isRightNodePossible = tree.rt != null
                        && closest.distanceSquaredTo(target) >= tree.rt.rect
                        .distanceSquaredTo(target);

                // PRUNING
                // search needs to go to both branches
                // go FIRST to the one the query point belongs (rectangle that contains the query point)
                if (isLeftNodePossible && isRightNodePossible) {
                    if (tree.lb.rect.contains(target)) {
                        closest = nearest(target, tree.lb, closest);
                        // this should be executed to check all values
                        closest = nearest(target, tree.rt, closest);
                    }
                    else {
                        closest = nearest(target, tree.rt, closest);
                        // this should be executed to check all values
                        closest = nearest(target, tree.lb, closest);
                    }
                }
                else if (isLeftNodePossible) {
                    closest = nearest(target, tree.lb, closest);
                }
                else {
                    closest = nearest(target, tree.rt, closest);
                }

                return closest;
            }
        }
    }

    private final Node _rootNode;
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
        this._rootNode.draw();
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null)
            throw new IllegalArgumentException();

        SET<Point2D> result = new SET<Point2D>();

        return this._rootNode.range(this._rootNode, rect, result);
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException();

        return this._rootNode.nearest(p, this._rootNode, null);
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

        StdOut.println("\nRange --------------------------------");
        RectHV area = new RectHV(0, 0, 1, 1);
        for (Point2D p : set.range(area)) {
            StdOut.println("range | " + p);
        }

        StdOut.println("\nNearest -------------------------------");
        Point2D nearestToSearch = new Point2D(0.39, 0.69);
        //KdTree set2 = new KdTree();
        //Point2D nearestToSearch = new Point2D(0, 0);
        StdOut.println("nearest from " + nearestToSearch + ": " + set.nearest(nearestToSearch));

        //set.draw();
        /*
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
        */

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
}
