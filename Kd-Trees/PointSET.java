
import edu.princeton.cs.algs4.*;

/**
 * Using a BST structure to implement range search algorithm and nearest point
 * algorithms
 * 
 * @author Simon
 *
 */

public class PointSET {
  private SET<Point2D> pSet;

  public PointSET() {
    pSet = new SET<Point2D>();// construct an empty set of points
  }

  /**
   * Is the point set empty?
   * 
   * @return true if empty
   */
  public boolean isEmpty() {
    return pSet.size() == 0; // is the set empty?
  }

  /**
   * the size of the point set
   * 
   * @return size of the point set
   */
  public int size() {
    return pSet.size(); // number of points in the set
  }

  /**
   * let p insert into the point set
   * 
   * @param p
   *          a point
   */
  public void insert(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException("Empty argument is not allowed.");
    }
    if (pSet.contains(p)) {
      return;
    }
    pSet.add(p);
    // add the point to the set (if it is not already in the set)
  }

  /**
   * Does the point set contain point p?
   * 
   * @param p
   *          one point
   * @return True if it is, but false otherwise
   */
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException("Empty argument is not allowed.");
    }
    return pSet.contains(p);
  } // does the set contain point p?

  /**
   * draw all points in the set.
   */
  public void draw() {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);
    for (Point2D point2d : pSet) {
      point2d.draw();
    }
  } // draw all points to standard draw

  /**
   * return a iterable collection which is comprised of points inside the
   * rectangular.
   * 
   * @param rect
   *          a rectangular
   * @return a iterable collection which is comprised of points inside the
   *         rectangular.
   */
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> pInrect = new Queue<>();
    for (Point2D point2d : pSet) {
      if (rect.contains(point2d)) {
        pInrect.enqueue(point2d);
      }
    }

    return pInrect; // all points that are inside the rectangle
  }

  /**
   * return the nearest points from the set to p.
   * 
   * @param p
   * @return the nearest points from the set to p.
   */
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException("Empty argument is not allowed.");
    }

    Point2D nearestPoint = null;
    
    for (Point2D point2d : pSet) {
      if (nearestPoint == null || point2d.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {
        nearestPoint = point2d;
      }
    }

    return nearestPoint;
  } // a nearest neighbor in the set to point p; null if the set is empty

  public static void main(String[] args) {

  } // unit testing of the methods (optional)
}