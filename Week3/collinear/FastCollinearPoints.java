import edu.princeton.cs.algs4.MergeX;

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
            // Pick the "Focal Point"
            Point focalPoint = points[i];
            // Create a slope comparator
            Comparator<Point> pointSlopeComparator = focalPoint.slopeOrder();
            // Subarray of points: all those to the right from Point i
            Point[] pointsToCompare = Arrays.copyOfRange(points, i, n - 1);
            // Do the Merge Sort of the Points, according to their slopes
            // (with respect to the Focal Point)
            MergeX.sort(pointsToCompare, pointSlopeComparator);
            // Loop over
            int j = 0;
            while (j < pointsToCompare.length) {
                Point firstPoint = pointsToCompare[j];
                int pointCounter = 2;

                for (int k = 1; j + k < pointsToCompare.length; k++) {
                    if (pointSlopeComparator.compare(firstPoint, pointsToCompare[j + k]) == 0) {
                        pointCounter++;
                    }
                    else {
                        if (pointCounter > 3) {
                            this.segmentArr[this.numOfSegments++] = new LineSegment(focalPoint,
                                                                                    pointsToCompare[
                                                                                            j + k
                                                                                                    - 1]);
                        }
                        j = j + k;
                        break;
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
        return getRightsizedArray(this.segmentArr);
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
