/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

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

        // Horizontal (blue)
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();

        // Vertical (red)
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();

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
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException();

        if (this.isEmpty())
            return null;
    }

    public static void main(String[] args) {

    }
}
