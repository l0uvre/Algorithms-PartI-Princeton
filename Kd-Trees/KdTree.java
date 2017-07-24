
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * 
 * @author Simon
 *
 */
public class KdTree {
  private Node root; // root of the KdTree
  private int treeSize; // size of the KdTree

  private static class Node {
    private Point2D p; // the point
    private RectHV rect; // the axis-aligned rectangle corresponding to this
                         // node
    private Node lb; // the left/bottom subtree
    private Node rt; // the right/top subtree

    public Node(Point2D p) {
      this.p = p;
    }

    public Point2D getPoint() {
      return this.p;
    }

    public void setRect(RectHV rect) {
      this.rect = rect;
    }

    public RectHV getRect() {
      return this.rect;
    }
  }

  private enum Orientation {
    vertical, horizontal;
    public Orientation next() {
      if (this.equals(vertical)) {
        return Orientation.horizontal;
      }
      return Orientation.vertical;
    }

  }

  public KdTree() {
    root = null;
    treeSize = 0;
  }

  public boolean isEmpty() {
    return root == null;

  } // is the set empty?

  public int size() {
    return treeSize;

  } // number of points in the set

  public void insert(Point2D p) {
    checkNull(p);
    if (root == null) {
      root = insert(null, p, Orientation.vertical);
      root.setRect(new RectHV(0, 0, 1, 1));
    } else {
      root = insert(root, p, Orientation.horizontal);
    }

  } // add the point to the set (if it is not already in the set)

  private Node insert(Node n, Point2D p, Orientation orientation) {
    if (n == null) {
      treeSize++;
      return new Node(p);
    }
    if (n.getPoint().equals(p)) {
      return n;
    }
    Orientation nextO = orientation.next();
    if (orientation.equals(Orientation.vertical) && p.y() < n.getPoint().y()) {
      n.lb = insert(n.lb, p, nextO);
      if (n.lb.getRect() == null) {
        n.lb.setRect(new RectHV(n.getRect().xmin(), n.getRect().ymin(), n.getRect().xmax(), n.getPoint().y()));
      }

    } else if (orientation.equals(Orientation.vertical) && p.y() >= n.getPoint().y()) {
      n.rt = insert(n.rt, p, nextO);
      if (n.rt.getRect() == null) {
        n.rt.setRect(new RectHV(n.getRect().xmin(), n.getPoint().y(), n.getRect().xmax(), n.getRect().ymax()));
      }

    } else if (orientation.equals(Orientation.horizontal) && p.x() < n.getPoint().x()) {
      n.lb = insert(n.lb, p, nextO);
      if (n.lb.getRect() == null) {
        n.lb.setRect(new RectHV(n.getRect().xmin(), n.getRect().ymin(), n.getPoint().x(), n.getRect().ymax()));
      }
    } else if (orientation.equals(Orientation.horizontal) && p.x() >= n.getPoint().x()) {
      n.rt = insert(n.rt, p, nextO);
      if (n.rt.getRect() == null) {
        n.rt.setRect(new RectHV(n.getPoint().x(), n.getRect().ymin(), n.getRect().xmax(), n.getRect().ymax()));
      }
    }
    return n;
  }

  public boolean contains(Point2D p) {
    checkNull(p);
    return get(root, p, Orientation.horizontal);
  } // does the set contain point p?

  private boolean get(Node n, Point2D p, Orientation orientation) {
    if (n == null) {
      return false;
    }
    if (n.getPoint().equals(p)) {
      return true;
    }

    Orientation next = orientation.next();
    if (orientation.equals(Orientation.horizontal) && p.x() < n.getPoint().x()) {
      return get(n.lb, p, next);

    } else if (orientation.equals(Orientation.horizontal) && p.x() >= n.getPoint().x()) {
      return get(n.rt, p, next);

    } else if (orientation.equals(Orientation.vertical) && p.y() < n.getPoint().y()) {
      return get(n.lb, p, next);

    } else if (orientation.equals(Orientation.vertical) && p.y() >= n.getPoint().y()) {
      return get(n.rt, p, next);
    }

    return false;
  }

  public void draw() {
    draw(root, Orientation.vertical);
  } // draw all points to standard draw

  private void draw(Node n, Orientation orientation) {
    if (n != null) {
      if (orientation.equals(Orientation.vertical)) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.getPoint().draw();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.001);
        StdDraw.line(n.getPoint().x(), n.getRect().ymin(), n.getPoint().x(), n.getRect().ymax());
      }
      if (orientation.equals(Orientation.horizontal)) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.getPoint().draw();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.001);
        StdDraw.line(n.getRect().xmin(), n.getPoint().y(), n.getRect().xmax(), n.getPoint().y());
      }
      Orientation next = orientation.next();

      if (n.lb != null) {
        draw(n.lb, next);
      }
      if (n.rt != null) {
        draw(n.rt, next);
      }

    }

  }

  public Iterable<Point2D> range(RectHV rect) {
    checkNull(rect);

    Queue<Point2D> points = new Queue<>();
    Points(root, points, rect);

    return points;

  } // all points that are inside the rectangle

  private void Points(Node n, Queue<Point2D> points, RectHV rect) {
    if (!n.getRect().intersects(rect)) {
      return;
    } else {
      if (rect.contains(n.getPoint())) {
        points.enqueue(n.getPoint());
      }

    }

    if (n.lb != null) {
      Points(n.lb, points, rect);
    }
    if (n.rt != null) {
      Points(n.rt, points, rect);
    }

  }

  public Point2D nearest(Point2D p) {
    checkNull(p);

    return nearestPoint(root, p, Orientation.horizontal, Double.MAX_VALUE, root.getPoint());

  } // a nearest neighbor in the set to point p; null if the set is empty

  private Point2D nearestPoint(Node n, Point2D p, Orientation orientation, double closestDistance, Point2D dPoint2d) {
    if (n == null) {
      return dPoint2d;
    }

    double pDistance = n.getPoint().distanceSquaredTo(p);
    double nearestDistance = closestDistance;
    Point2D nearest = dPoint2d;
    if (pDistance < nearestDistance) {
      nearestDistance = pDistance;
      nearest = n.getPoint();
    }
    /*
     * if (n.lb == null && n.rt == null) { return nearest; }
     */

    Node first, second;
    Orientation nextOr = orientation.next();
    if (orientation == Orientation.horizontal) {
      if (p.x() < n.getPoint().x()) {
        first = n.lb;
        second = n.rt;
      } else {
        first = n.rt;
        second = n.lb;
      }
    } else {
      if (p.y() < n.getPoint().y()) {
        first = n.lb;
        second = n.rt;
      } else {
        first = n.rt;
        second = n.lb;
      }
    }

    if (first != null && nearestDistance >= first.getRect().distanceSquaredTo(p)) {
      nearest = nearestPoint(first, p, nextOr, nearestDistance, nearest);
      nearestDistance = nearest.distanceSquaredTo(p);
    }

    if (second != null && nearest.distanceSquaredTo(p) >= second.getRect().distanceSquaredTo(p)) {
      nearest = nearestPoint(second, p, nextOr, nearestDistance, nearest);
      nearestDistance = nearest.distanceSquaredTo(p);
    }
    return nearest;

  }

  private void checkNull(Object b) {
    if (b == null) {
      throw new java.lang.IllegalArgumentException("Empty argument is not allowed.");
    }
  }

  public static void main(String[] args) {

  } // unit testing of the methods (optional)

}
