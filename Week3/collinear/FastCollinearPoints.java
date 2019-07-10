import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] segmentArr;
    private int numOfSegments;

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        int n = points.length;

        numOfSegments = 0;
        segmentArr = new LineSegment[n * n];

        // Sort the points
        Arrays.sort(points, Point::compareTo);
        // Go over all points
        for (int i = 0; i < n; i++) {

            if (n - i < 2) {
                break;
            }

            int ii = i + 1;
            StdOut.println("Point " + ii + "/" + n);
            // Pick the "Focal Point"
            Point focalPoint = points[i];
            // Create a slope comparator
            Comparator<Point> pointSlopeComparator = focalPoint.slopeOrder();
            // Subarray of points: all those to the right from Point i
            Point[] pointsToCompare = Arrays.copyOfRange(points, i + 1, n);
            // Do the Merge Sort of the Points, according to their slopes
            // (with respect to the Focal Point)
            MergeX.sort(pointsToCompare, pointSlopeComparator);
            // Loop over
            int j = 0;
            int jj = j + 1;
            // Loop over all remaining points. Each point here can potentially
            // be a "second point" of a 4-point segment.
            while (j < pointsToCompare.length) {
                StdOut.println(" --- [" + ii + "/" + n + "]: Check Point " + jj + "/"
                                       + pointsToCompare.length);
                // Get the second Point object
                Point secondPoint = pointsToCompare[j];
                int pointCounter = 2;
                // Iterate over all remaining points to see if they align with
                // the first and second points
                for (int k = 1; j + k < pointsToCompare.length; k++) {
                    StdOut.println(" --- --- k: " + k);
                    if (pointSlopeComparator.compare(secondPoint, pointsToCompare[j + k]) == 0) {
                        pointCounter++;
                        StdOut.println(" --- --- --- Found " + pointCounter + " colinear points");
                        // continue;
                    }
                    else {
                        if (pointCounter > 3) {
                            updateSegmentArray(
                                    new LineSegment(focalPoint, pointsToCompare[j + k - 1]));
                        }
                        j = j + k;
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
        StdOut.println("\n *** Segment Search Done! *** \n");
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

}
