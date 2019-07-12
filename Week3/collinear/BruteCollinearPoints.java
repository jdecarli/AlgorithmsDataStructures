/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

    private LineSegment[] segmentsOfFour;
    private int numOfSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            pointsCopy[i] = points[i];
        }

        this.segmentsOfFour = new LineSegment[points.length * points.length];
        this.numOfSegments = 0;


        // Sort the points
        Arrays.sort(pointsCopy, Point::compareTo);

        for (int i = 0; i < pointsCopy.length - 3; i++) {

            Point pi = pointsCopy[i];
            Comparator<Point> pointSlopeComparator = pi.slopeOrder();

            for (int j = i + 1; j > i && j < pointsCopy.length - 2; j++) {
                Point pj = pointsCopy[j];
                if (pj.compareTo(pi) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
                for (int k = j + 1; k > j && k < pointsCopy.length - 1; k++) {
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

        Point[] points = new Point[7];

        // /*
        int m = 4;
        for (int i = 0; i < m; i++) {
            points[i] = new Point(i, i);
        }

        points[m] = new Point(0, 1);
        points[m + 1] = new Point(0, 2);
        points[m + 2] = new Point(0, 3);
        // */
        // points[0] = new Point(19000, 10000);
        // points[1] = new Point(18000, 10000);
        // points[2] = new Point(32000, 10000);
        // points[3] = new Point(21000, 10000);
        // points[4] = new Point(1234, 5678);
        // points[5] = new Point(14000, 10000);

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        // StdOut.println("Number of points: " + points.length);

        // StdOut.println("Number of discovered 4-point segments: " + bcp.numberOfSegments());

        // System.out.println("bcp.segments: " + bcp.segments().length);

        // for (LineSegment segment : bcp.segments()) {
        //     if (segment != null) {
        //         // StdOut.println(segment.toString());
        //     }
        //     else {
        //         break;
        //     }
        // }
    }
}
