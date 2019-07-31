/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class PointSET {

    private SET<Point2D> redBlackTree;
    private int setSize;

    // construct an empty set of points
    public PointSET() {
        this.redBlackTree = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.setSize == 0;
    }

    // number of points in the set
    public int size() {
        return this.setSize;
    }

    // add the point to the set (if it is not already in the set)
    // MUST be ~ log(N)
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.redBlackTree.add(p);
        this.setSize++;
    }

    // does the set contain point p?
    // MUST be ~ log(N)
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        return this.redBlackTree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : this.redBlackTree) {
            StdDraw.filledCircle(p.x(), p.y(), 0.01);
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    // MUST be ~ N
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();

        Queue<Point2D> q = new Queue<Point2D>();

        for (Point2D p : this.redBlackTree) {
            if (p.x() >= xmin && p.x() <= xmax) {
                if (p.y() >= ymin && p.y() <= ymax) {
                    q.enqueue(p);
                }
            }
        }

        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    // MUST be ~ N
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Point2D nearestNeighbor = null;

        for (Point2D point : this.redBlackTree) {
            if (nearestNeighbor != null) {
                if (p.distanceTo(point) < p.distanceTo(nearestNeighbor)) {
                    nearestNeighbor = point;
                }
            }
            else {
                nearestNeighbor = point;
            }

        }

        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        unitTestCountAndDraw(args, false);

    }

    private static void unitTestCountAndDraw(String[] args, boolean draw) {
        PointSET rbt = new PointSET();

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

                    rbt.insert(new Point2D(x, y));
                }
                catch (NoSuchElementException e) {
                    break;
                }

            }
            StdOut.println("------------------------------------------------");
            StdOut.println("Number of points: " + rbt.size());
            StdOut.println("------------------------------------------------");

            if (draw) {
                StdOut.println("Drawing the points... ");
                rbt.draw();
                StdOut.println("------------------------------------------------");
            }

        }
    }
}
