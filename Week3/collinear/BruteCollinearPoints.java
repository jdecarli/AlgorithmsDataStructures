/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

    private final LineSegment[] segmentsOfFour;
    private int numOfSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            pointsCopy[i] = points[i];
        }

        this.segmentsOfFour = new LineSegment[points.length * points.length];
        this.numOfSegments = 0;

        // Sort the points
        Arrays.sort(pointsCopy, Point::compareTo);

        // for (int i = 0; i < pointsCopy.length - 3; i++) {
        for (int i = 0; i < pointsCopy.length; i++) {
            Point pi = pointsCopy[i];
            Comparator<Point> pointSlopeComparator = pi.slopeOrder();

            // for (int j = i + 1; j > i && j < pointsCopy.length - 2; j++) {
            for (int j = i + 1; j > i && j < pointsCopy.length; j++) {
                Point pj = pointsCopy[j];

                if (pi.compareTo(pj) == 0) {
                    throw new IllegalArgumentException();
                }

                // for (int k = j + 1; k > j && k < pointsCopy.length - 1; k++) {
                for (int k = j + 1; k > j && k < pointsCopy.length; k++) {
                    Point pk = pointsCopy[k];

                    for (int q = k + 1; q > k && q < pointsCopy.length; q++) {

                        Point pq = pointsCopy[q];

                        if (pointSlopeComparator.compare(pj, pk) == 0) {
                            if (pointSlopeComparator.compare(pj, pq) == 0) {

                                LineSegment segmentOf4 = new LineSegment(pi, pq);
                                this.segmentsOfFour[this.numOfSegments] = segmentOf4;
                                this.numOfSegments++;
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return this.numOfSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        return getRightsizedArray(this.segmentsOfFour);
    }

    private LineSegment[] getRightsizedArray(LineSegment[] input) {

        if (this.numOfSegments != input.length) {
            LineSegment[] newArray = new LineSegment[this.numOfSegments];

            for (int i = 0; i < this.numOfSegments; i++) {
                newArray[i] = input[i];
            }

            input = newArray;
        }

        return input;
    }

    public static void main(String[] args) {

        // unitTestRepeated2();
        // unitTestRepeated3();
        // unitTestRepeated4();
        // unitTestRepeated5();
    }

    /*
    private static void unitTestRepeated2() {
        Point[] points = new Point[2];

        points[0] = new Point(30340, 9524);
        points[1] = new Point(30340, 9524);

        printResult(points);
    }

    private static void unitTestRepeated3() {
        Point[] points = new Point[3];

        points[0] = new Point(26587, 31404);
        points[1] = new Point(12334, 7475);
        points[2] = new Point(26587, 31404);

        printResult(points);
    }

    private static void unitTestRepeated4() {
        Point[] points = new Point[4];

        points[0] = new Point(31624, 268);
        points[1] = new Point(17364, 12653);
        points[2] = new Point(14329, 12691);
        points[3] = new Point(14329, 12691);

        printResult(points);
    }

    private static void unitTestRepeated5() {
        Point[] points = new Point[5];

        points[0] = new Point(13422, 10771);
        points[1] = new Point(4958, 18654);
        points[2] = new Point(4958, 18654);
        points[3] = new Point(236, 21476);
        points[4] = new Point(8588, 5572);

        printResult(points);
    }

    private static void printResult(Point[] points) {
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        StdOut.println("\nNumber of points: " + points.length);
        StdOut.println("Number of discovered 4-point segments: " + bcp.numberOfSegments());

        System.out.println("bcp.segments: " + bcp.segments().length);

        for (LineSegment segment : bcp.segments()) {
            if (segment != null) {
                StdOut.println(segment.toString());
            }
            else {
                break;
            }
        }
    }
    */
}
