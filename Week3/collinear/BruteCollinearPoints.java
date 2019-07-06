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

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j > i && j < points.length; j++) {
                for (int k = j + 1; k > j && k < points.length; k++) {
                    for (int q = k + 1; q > k && q < points.length; q++) {
                        Point pi = points[i];
                        Point pj = points[j];
                        Point pk = points[k];
                        Point pq = points[q];

                        Comparator<Point> pointSlopeComparator = pi.slopeOrder();

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
        return numOfSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        return segmentsOfFour;
    }

    public static void main(String[] args) {

        Point[] points = new Point[10];

        for (int i = 0; i < 6; i++) {
            points[i] = new Point(i, i);
        }

        points[6] = new Point(0, 1);
        points[7] = new Point(0, 2);
        points[8] = new Point(0, 3);
        points[9] = new Point(0, 4);

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        StdOut.println("Number of points: " + points.length);

        StdOut.println("Number of discovered 4-point segments: " + bcp.numberOfSegments());

        for (LineSegment segment : bcp.segments()) {
            StdOut.println(segment.toString());
        }


    }
}
