import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
  private LineSegment[] lineSegments;

  /**
   * Using an effective algorithms by the slop between two points. And find all
   * line segments containing more than 3 points
   * 
   * 
   * @param points
   *          an array of different points
   */
  public FastCollinearPoints(Point[] points) {
    ArrayList<LineSegment> bufferLineSegments = new ArrayList<>();

    if (points == null) {
      throw new java.lang.IllegalArgumentException("Should include some points.");
    } else if (checkPoints(points)) {
      throw new java.lang.IllegalArgumentException("Points could not be none.");
    } else if (checkDuplicatePoints(points)) {
      throw new java.lang.IllegalArgumentException("No duplicate points allowed.");
    }
    //assert !checkDuplicatePoints(points);
    //assert !checkPoints(points);
    
    
    // Sort the points according to the slopes they makes with p.
    // Check if any 3 (or more) adjacent points in the sorted order
    // have equal slopes with respect to p. If so, these points,
    // together with p, are collinear.
    Point[] copyPoints = Arrays.copyOf(points, points.length);
    
    for (int i = 0; i < copyPoints.length - 3; i++) {
      Arrays.sort(copyPoints);
      Arrays.sort(copyPoints, copyPoints[i].slopeOrder());
      
      for (int p = 0, first = 1, last = 2; last < copyPoints.length; last++) {
        
        while (last < copyPoints.length 
                   && Double.compare(copyPoints[p].slopeTo(copyPoints[first]), copyPoints[p].slopeTo(copyPoints[last])) == 0) {
           last++;
        }  
        if (last - first >= 3 && copyPoints[p].compareTo(copyPoints[first]) < 0) {
            bufferLineSegments.add(new LineSegment(copyPoints[p], copyPoints[last - 1]));
          }
        
        first = last;
      }
      
    }
    
    if (bufferLineSegments.size() == 0) {
      lineSegments = new LineSegment[0];
    } else {
      lineSegments = bufferLineSegments.toArray(new LineSegment[bufferLineSegments.size()]);
    }
  } // finds all line segments containing 4 or more points

  public int numberOfSegments() {
    return lineSegments.length;// the number of line segments
  }

  public LineSegment[] segments() {
    return Arrays.copyOf(lineSegments, lineSegments.length);// the line segments
  }

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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}