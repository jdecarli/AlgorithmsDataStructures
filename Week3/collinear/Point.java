/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point


    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null)
            throw new NullPointerException("argument is null");

        double hor = that.y - this.y;
        double ver = that.x - this.x;

        if (hor == 0 && ver == 0) // degenerate line segment
            return Double.NEGATIVE_INFINITY;

        if (hor == 0) // horizontal line segment
            return 0.0;

        if (ver == 0) // vertical line segment
            return Double.POSITIVE_INFINITY;

        return hor / ver;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null)
            throw new NullPointerException("argument is null");

        if (this.x == that.x && this.y == that.y)
            return 0;                                                   // equal point
        if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;                                                  // this less than that

        return 1;                                                       // this greater than that
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */

        return new PointComparer(this);
    }

    private class PointComparer implements Comparator<Point> {
        private final Point reference;

        public PointComparer(Point point) {
            this.reference = point;
        }

        public int compare(Point current, Point input) {
            // Intead of doing manual comparison, using the native Double compare method
            return Double.compare(this.reference.slopeTo(current), this.reference.slopeTo(input));
        }
    }

    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */

        // unitTestSlopeTo();
        // System.out.println("\n------------------------\n");
        // unitTestComparator();
    }

    /*
    private static void unitTestSlopeTo() {
        System.out.println("Testing slopeTo method...\n");
        System.out.println("Positive - Different points");
        Point aFirstp = new Point(0, 0);
        Point aSecondp = new Point(3, 3);
        System.out.println("firstp slopeTo secondp: " + aFirstp.slopeTo(aSecondp));
        System.out.println("secondp slopeTo firstp: " + aSecondp.slopeTo(aFirstp));

        System.out.println("\nPositive - Horizontal line");
        Point bFirstp = new Point(0, 3);
        Point bSecondp = new Point(3, 3);
        System.out.println("firstp slopeTo secondp: " + bFirstp.slopeTo(bSecondp));
        System.out.println("secondp slopeTo firstp: " + bSecondp.slopeTo(bFirstp));

        System.out.println("\nPositive - Vertical line");
        Point cFirstp = new Point(3, 7);
        Point cSecondp = new Point(3, 3);
        System.out.println("firstp slopeTo secondp: " + cFirstp.slopeTo(cSecondp));
        System.out.println("secondp slopeTo firstp: " + cSecondp.slopeTo(cFirstp));

        System.out.println("\nSame");
        Point unique = new Point(5, 5);
        System.out.println("slopeTo: " + unique.slopeTo(unique));

        System.out.println("\nNegative - Different points");
        Point dFirstp = new Point(0, 0);
        Point dSecondp = new Point(-3, 3);
        System.out.println("firstp slopeTo secondp: " + dFirstp.slopeTo(dSecondp));
        System.out.println("secondp slopeTo firstp: " + dSecondp.slopeTo(dFirstp));
    }

    private static void unitTestComparator() {
        System.out.println("Start testing Comparator method...\n");
        System.out.println("Connected");
        Point aInvoking = new Point(1, 1);
        Point aFirstp = new Point(0, 0);
        Point aSecondp = new Point(3, 3);
        System.out.println("invoking -> first against second: " + aInvoking.slopeOrder().compare(aFirstp, aSecondp));
        System.out.println("invoking -> second against first: " + aInvoking.slopeOrder().compare(aSecondp, aFirstp));

        System.out.println("\nNon-Connected");
        Point bInvoking = new Point(1, 1);
        Point bFirstp = new Point(6, 0);
        Point bSecondp = new Point(2, 3);
        System.out.println("invoking -> first against second: " + bInvoking.slopeOrder().compare(bFirstp, bSecondp));
        System.out.println("invoking -> second against first: " + bInvoking.slopeOrder().compare(bSecondp, bFirstp));

        System.out.println("\nHorizontal");
        Point cInvoking = new Point(1, 1);
        Point cFirstp = new Point(0, 1);
        Point cSecondp = new Point(3, 1);
        System.out.println("invoking -> first against second: " + cInvoking.slopeOrder().compare(cFirstp, cSecondp));
        System.out.println("invoking -> second against first: " + cInvoking.slopeOrder().compare(cSecondp, cFirstp));

        System.out.println("\nVertical");
        Point dInvoking = new Point(1, 7);
        Point dFirstp = new Point(1, 6);
        Point dSecondp = new Point(1, -4);
        System.out.println("invoking -> first against second: " + dInvoking.slopeOrder().compare(dFirstp, dSecondp));
        System.out.println("invoking -> second against first: " + dInvoking.slopeOrder().compare(dSecondp, dFirstp));
    }
    */
}
