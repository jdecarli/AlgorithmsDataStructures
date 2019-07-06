/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segmentsOfFour;
    private int numOfSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        this.points = points;
        this.segmentsOfFour = new LineSegment[points.length * points.length];
        this.numOfSegments = 0;

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j > i && j < points.length - 2; j++) {
                for (int k = j + 1; k > j && k < points.length - 1; k++) {
                    for (int q = k + 1; q > k && q < points.length; q++) {

                        Point pi = points[i];
                        Point pj = points[j];
                        Point pk = points[k];
                        Point pq = points[q];

                        Comparator<Point> pointSlopeComparator = pi.slopeOrder();

                        if (pointSlopeComparator.compare(pj, pk) == 0) {
                            if (pointSlopeComparator.compare(pj, pq) == 0) {

                                StdOut.println(
                                        "Found a 4-Segment: " + i + ", " + j + ", " + k + ", "
                                                + q);

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
        return numOfSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        return getRightsizedArray(segmentsOfFour);
    }

    private LineSegment[] getRightsizedArray(LineSegment[] input) {

        if (numOfSegments != input.length) {
            LineSegment[] newArray = new LineSegment[numOfSegments];

            for (int i = 0; i < numOfSegments; i++) {
                newArray[i] = input[i];
            }

            input = newArray;
        }

        return input;
    }

    public static void main(String[] args) {

        Point[] points = new Point[6];

        /*
        for (int i = 0; i < 6; i++) {
            points[i] = new Point(i, i);
        }

        points[6] = new Point(0, 1);
        points[7] = new Point(0, 2);
        points[8] = new Point(0, 3);
        points[9] = new Point(0, 4);
        */
        points[0] = new Point(19000, 10000);
        points[1] = new Point(18000, 10000);
        points[2] = new Point(32000, 10000);
        points[3] = new Point(21000, 10000);
        points[4] = new Point(1234, 5678);
        points[5] = new Point(14000, 10000);

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        StdOut.println("Number of points: " + points.length);

        StdOut.println("Number of discovered 4-point segments: " + bcp.numberOfSegments());

        System.out.println("bcp.segments: " + bcp.segments().length);

        for (LineSegment segment : bcp.segments()) {
            if (segment != null)
                StdOut.println(segment.toString());
        }
    }
}
