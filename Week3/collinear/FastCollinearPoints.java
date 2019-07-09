import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        int n = points.length;
        // Sort the points
        Arrays.sort(points, Point::compareTo);
        // Go over all points
        for (int i = 0; i < n; i++) {
            // Pick the focal point and create a slope comparator
            Comparator<Point> pointSlopeComparator = points[i].slopeOrder();
            // Subarray of points: all those to the right from Point i
            Point[] pointsToCompare = Arrays.copyOfRange(points, i, n - 1);
            // Do the Merge Sort of the slopes
            mergeSort(pointsToCompare, pointSlopeComparator);
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return 0;
    }

    public LineSegment[] segments()                // the line segments
    {
        return null;
    }

    private static void mergeSort(Object[] arr, Comparator comp) {
        // Create the auxiliary array
        Object[] aux = new Object[arr.length];
        // Run the Sort
        sort(arr, aux, 0, arr.length - 1, comp);
    }

    private static void sort(Object[] arr, Object[] aux, int lo, int hi,
                             Comparator comp) {
        if (hi <= lo) return;
        // Define the mid-point index in the array
        int mid = lo + (hi - lo) / 2;
        // Sort the left half
        sort(arr, aux, lo, mid, comp);
        // Sort the right half
        sort(arr, aux, mid + 1, hi, comp);
        // Merge them
        merge(arr, aux, lo, mid, hi, comp);
    }

    private static void merge(Object[] arr, Object[] aux, int lo, int mid, int hi,
                              Comparator comp) {

        // TODO: Implement isSorted().
        // assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        // assert isSorted(a, mid+1, hi); // precondition: a[mid+1..hi] sorted

        // Fill the Aux array
        for (int k = lo; k <= hi; k++)
            aux[k] = arr[k];
        // Initialize the indices i and j
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else if (comp.compare(aux[j], aux[i]) < 0) arr[k] = aux[j++];
            else arr[k] = aux[i++];
        }
        // assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }


}
