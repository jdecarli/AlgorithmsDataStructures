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

public class PointSET {
    private final SET<Point2D> internalPointSet;

    public PointSET()                               // construct an empty set of points
    {
        this.internalPointSet = new SET<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return this.size() == 0;
    }

    public int size()                         // number of points in the set
    {
        return this.internalPointSet.size();
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new IllegalArgumentException();

        if (this.contains(p))
            return;

        this.internalPointSet.add(p);

        // Your implementation should support insert() and contains() in time proportional to the
        // logarithm of the number of points in the set in the worst case; it should support nearest()
        // and range() in time proportional to the number of points in the set.
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new IllegalArgumentException();

        return this.internalPointSet.contains(p);

        // Your implementation should support insert() and contains() in time proportional to the
        // logarithm of the number of points in the set in the worst case; it should support nearest()
        // and range() in time proportional to the number of points in the set.
    }

    public void draw()                         // draw all points to standard draw
    {
        // Draw a point (black)
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point : this.internalPointSet) {
            point.draw();
        }

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
        if (rect == null)
            throw new IllegalArgumentException();

        if (this.isEmpty())
            return null;

        // TODO: not implemented - check one by one (BF approach)
        SET<Point2D> pointsInRange = new SET<Point2D>();

        // cycle thru all points
        for (Point2D point : this.internalPointSet) {
            // rectangle contains
            if (rect.contains(point))
                pointsInRange.add(point); // if yes -> add to range set
        }

        return pointsInRange;
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException();

        if (this.isEmpty())
            return null;

        Point2D result = new Point2D(0, 0);
        double minimunDistance = 1;

        // cycle thru all points
        for (Point2D point : this.internalPointSet) {
            double distance = p.distanceTo(point);

            if (distance < minimunDistance) {
                minimunDistance = distance;
                result = point;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        StdOut.println("-------------------------------------");
        StdOut.println("Initialize");
        StdOut.println("-------------------------------------");

        StdOut.println("Empty -------------------------------");
        PointSET set = new PointSET();
        StdOut.println("Empty (true): " + set.isEmpty());
        Point2D point = new Point2D(0.6, 0.5);
        set.insert(point);
        StdOut.println("Insert p1 + Empty (false): " + set.isEmpty());

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
    }
}
