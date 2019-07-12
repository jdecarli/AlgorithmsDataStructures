import edu.princeton.cs.algs4.MergeX;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] segmentArr;
    private int numOfSegments;

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
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

        int n = pointsCopy.length;

        numOfSegments = 0;
        segmentArr = new LineSegment[n * n];

        // Sort the points
        Arrays.sort(pointsCopy, Point::compareTo);

        // Go over all points
        for (int i = 0; i < n; i++) {

            if (n - i < 2) {
                break;
            }

            // Pick the "Focal Point"
            Point focalPoint = pointsCopy[i];

            // Create a slope comparator
            Comparator<Point> pointSlopeComparator = focalPoint.slopeOrder();
            // Subarray of points: all those to the right from Point i
            Point[] pointsToCompare = Arrays.copyOfRange(pointsCopy, i + 1, n);
            // Do the Merge Sort of the Points, according to their slopes
            // (with respect to the Focal Point)
            MergeX.sort(pointsToCompare, pointSlopeComparator);

            // Loop over
            int j = 0;
            // Loop over all remaining points. Each point here can potentially
            // be a "second point" of a 4-point segment.
            while (j < pointsToCompare.length) {

                // Get the second Point object
                Point secondPoint = pointsToCompare[j];

                if (focalPoint.compareTo(secondPoint) == 0) {
                    throw new IllegalArgumentException();
                }

                int pointCounter = 2;

                // Iterate over all remaining points to see if they align with
                // the first and second points
                for (int k = 1; j + k < pointsToCompare.length; k++) {
                    if (pointSlopeComparator.compare(secondPoint, pointsToCompare[j + k]) == 0) {
                        pointCounter++;
                    }
                    else {
                        if (pointCounter > 3) {
                            updateSegmentArray(
                                    new LineSegment(focalPoint, pointsToCompare[j + k - 1]));
                        }
                        j = j + k - 1;
                        break;
                    }

                    if (j + k == pointsToCompare.length - 1) {
                        if (pointCounter > 3) {
                            LineSegment newSegment = new LineSegment(focalPoint,
                                                                     pointsToCompare[j + k]);
                            updateSegmentArray(newSegment);
                        }

                        j = j + k + 1;
                    }

                }

                j++;
            }
        }

        // StdOut.println("\n *** Segment Search Done! *** \n");
    }

    public int numberOfSegments()        // the number of line segments
    {
        return this.numOfSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        return getRightsizedArray(this.segmentArr);
    }

    private void updateSegmentArray(LineSegment s) {
        if (this.numOfSegments == this.segmentArr.length) {

            LineSegment[] newArray = new LineSegment[this.numOfSegments * 2];

            for (int i = 0; i < this.numOfSegments; i++) {
                newArray[i] = this.segmentArr[i];
            }

            this.segmentArr = newArray;

        }

        this.segmentArr[this.numOfSegments] = s;
        this.numOfSegments++;
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

    /*
    public static void main(String[] args) {

        // Point[] points = new Point[12];

        // System.out.println("Loading points...");

        Point[] points = new Point[15];
        points[0] = new Point(1000, 17000);
        points[1] = new Point(1000, 27000);
        points[2] = new Point(1000, 31000);
        points[3] = new Point(1000, 28000);
        // points[4] = new Point(1000, 17000);
        points[4] = new Point(29000, 17000);
        points[5] = new Point(13000, 17000);
        points[6] = new Point(17000, 17000);
        points[7] = new Point(14000, 24000);
        points[8] = new Point(25000, 24000);
        points[9] = new Point(2000, 24000);
        points[10] = new Point(9000, 24000);
        points[11] = new Point(28000, 29000);
        points[12] = new Point(22000, 29000);
        points[13] = new Point(2000, 29000);
        points[14] = new Point(4000, 29000);


        int m = 4;

        points[0] = new Point(16, 5);
        points[1] = new Point(8, 5);
        points[2] = new Point(10, 5);
        points[3] = new Point(5, 5);

        points[m] = new Point(0, 1);
        points[m + 1] = new Point(2, 0);
        points[m + 2] = new Point(0, 3);
        points[m + 3] = new Point(4, 0);

        points[m + 4] = new Point(1, 0);
        points[m + 5] = new Point(0, 2);
        points[m + 6] = new Point(3, 0);
        points[m + 7] = new Point(0, 4);


        // System.out.println("Done loading\n");


        // points[0] = new Point(19000, 10000);
        // points[1] = new Point(18000, 10000);
        // points[2] = new Point(32000, 10000);
        // points[3] = new Point(21000, 10000);
        // points[4] = new Point(1234, 5678);
        // points[5] = new Point(14000, 10000);

        FastCollinearPoints fast = new FastCollinearPoints(points);

        // StdOut.println("Number of points: " + points.length);

        // StdOut.println("Number of discovered 4-point segments: " + fast.numberOfSegments());

        // System.out.println("fast.segments: " + fast.segments().length);

        for (LineSegment segment : fast.segments()) {
            if (segment != null) {
                // StdOut.println(segment.toString());
            }
            else {
                break;
            }
        }

        // printArray(fast.segments(), "Line Segments");
    }
    */

    /*
    private static void printArray(Point[] points, String text) {
        // System.out.println("\n" + text + " - Printing array...");
        for (Point p : points) {
            // System.out.println(p.toString());
        }
    }

    private static void printArray(LineSegment[] points, String text) {
        // System.out.println("\n" + text + " - Printing array...");
        for (LineSegment p : points) {
            if (p != null)
            // System.out.println(p.toString());
        }
    }
    */
}
