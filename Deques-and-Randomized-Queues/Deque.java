import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  private Node first; // top of the queue
  private Node last; // bottom of the queue
  private int size; // size of the queue

  private class Node {
    private Item item;
    private Node next;
    private Node prev;
  }

  public Deque() {
    first = null;
    last = first;
    size = 0;
  } // construct an empty deque

  public boolean isEmpty() {
    return size == 0;
  } // is the deque empty?

  public int size() {
    return size;
  } // return the number of items on the deque

  public void addFirst(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    if (oldfirst != null)
      oldfirst.prev = first;// must have this for the queue is on the two
                            // directions
    if (last == null) {
      last = first;
    }
    size++;
    assert check();
  } // add the item to the front

  public void addLast(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    Node oldlast = last;
    last = new Node();
    last.prev = oldlast;
    last.item = item;
    if (oldlast != null) {
      oldlast.next = last;
    } // must have this for the queue is on the two directions
    if (first == null) {
      first = last;
    }
    size++;
    assert check();
  } // add the item to the end

  public Item removeFirst() {
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    Item item = first.item;
    if (size == 1) {
      last = first = null;
    } else {
      first = first.next;
      first.prev = null;
    }
    size--;
    assert check();
    return item;
  } // remove and return the item from the front

  public Item removeLast() {
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    Item item = last.item;
    if (size == 1) {
      last = first = null;
    } else {
      last = last.prev;
      last.next = null;
    }
    size--;
    assert check();
    return item;
  } // remove and return the item from the end

  public Iterator<Item> iterator() {
    return new ListIterator();

  } // return an iterator over items in order from front to end

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

  }

  private boolean check() {

    // check a few properties of instance variable 'first'
    if (size < 0) {
      return false;
    }
    if (size == 0) {
      if (first != null)
        return false;
    } else if (size == 1) {
      if (first == null)
        return false;
      if (first.next != null)
        return false;
    } else {
      if (first == null)
        return false;
      if (first.next == null)
        return false;
    } // check internal consistency of instance variable n

    int numberOfNodes = 0;
    for (Node x = first; x != null && numberOfNodes <= size; x = x.next) {
      numberOfNodes++;
    }
    if (numberOfNodes != size)
      return false;

    return true;
  }

  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();
    for (int i = 0; i < 10; i++) {
      deque.addLast(i);
    }
    for (int i : deque) {
      StdOut.print(i);
    }
    StdOut.println();
    for (int i = 0; i < 10; i++) {
      StdOut.print(deque.removeFirst());
    }

  } // unit testing (optional)
}
