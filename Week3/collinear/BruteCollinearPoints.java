/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Comparator;

public class BruteCollinearPoints {

    private LineSegment[] segmentsOfFour;
    private int numOfSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        segmentsOfFour = new LineSegment[points.length / 4];
        numOfSegments = 0;
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
                                segmentsOfFour[numOfSegments] = segmentOf4;
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

    }
}
