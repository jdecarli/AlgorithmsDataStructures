import edu.princeton.cs.algs4.MergeX;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] segmentArr;
    private int numSegments;

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        int n = points.length;

        numSegments = 0;
        segmentArr = new LineSegment[n * n];

        // Sort the points
        Arrays.sort(points, Point::compareTo);
        // Go over all points
        for (int i = 0; i < n; i++) {
            // Pick the "Focal Point" and create a slope comparator
            Comparator<Point> pointSlopeComparator = points[i].slopeOrder();
            // Subarray of points: all those to the right from Point i
            Point[] pointsToCompare = Arrays.copyOfRange(points, i, n - 1);
            // Do the Merge Sort of the Points, according to their slopes
            // (with respect to the Focal Point)
            MergeX.sort(pointsToCompare, pointSlopeComparator);
            // mergeSort(pointsToCompare, pointSlopeComparator);
            for (int j = 0; j < pointsToCompare.length; j++) {
                Point firstPoint = pointsToCompare[j];
                int pointCounter = 2;

                for (int k = 1; j + k < pointsToCompare.length; k++) {
                    if (pointSlopeComparator.compare(firstPoint, pointsToCompare[j + k]) == 0) {
                        pointCounter++;
                    }
                    else {
                        if (pointCounter > 3) {
                            this.segmentArr[this.numSegments++] = new LineSegment(firstPoint,
                                                                                  pointsToCompare[
                                                                                          j + k
                                                                                                  - 1]);
                        }
                        break;
                    }
                }

            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return this.numSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        return this.segmentArr;
    }

}
