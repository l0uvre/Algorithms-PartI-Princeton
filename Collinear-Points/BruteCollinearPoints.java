import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private LineSegment[] lineSegments;
  
  /** finds all line segments containing 4 points.
   * 
   */
  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new java.lang.IllegalArgumentException("Should include some points.");
    } else if (checkPoints(points)) {
      throw new java.lang.IllegalArgumentException("Points could not be none.");
    } else if (checkDuplicatePoints(points)) {
      throw new java.lang.IllegalArgumentException("No duplicate points allowed.");
    }
    //assert !checkDuplicatePoints(points);
    ArrayList<LineSegment> bufferLineSegments = new ArrayList<>();
    
    Point[] pointsCopy = Arrays.copyOf(points, points.length);
    Arrays.sort(pointsCopy);

    for (int i = 0; i < pointsCopy.length - 3; i++) {
      for (int j = i + 1; j < pointsCopy.length - 2; j++) {
        for (int k = j + 1; k < pointsCopy.length - 1; k++) {
          for (int l = k + 1; l < pointsCopy.length; l++) {
            if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k])
                && pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l])) {
              bufferLineSegments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
            }
          }
        }
      }
    }
    if (bufferLineSegments.size() == 0) {
      lineSegments = new LineSegment[0];
    } else {
      lineSegments = bufferLineSegments.toArray(new LineSegment[bufferLineSegments.size()]);
    }
  } // finds all line segments containing 4 points
  
  public int numberOfSegments() {
    return lineSegments.length;
  } // the number of line segments
  
  public LineSegment[] segments() {
    return lineSegments;
    
  } // the line segments
  
  private boolean checkDuplicatePoints(Point[] points) {
    boolean bool = false;
    for (int i = 0  ; i < points.length - 1; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private boolean checkPoints(Point[] points) {
    boolean bool = false;
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        bool = true;
      }
    }
    return bool;
  }
  /**main methods.
   * 
   * @param args main arguments
   */
  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}