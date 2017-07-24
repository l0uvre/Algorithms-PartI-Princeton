import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] queue; // array to stand for the queue
  private int size; // size of the queue

  public RandomizedQueue() {
    queue = (Item[]) new Object[2];
    size = 0;
  } // construct an empty randomized queue

  public boolean isEmpty() {
    return size == 0;
  } // is the queue empty?

  public int size() {
    return size;
  } // return the number of items on the queue

  private void resize(int capacity) {
    assert capacity >= size;
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      temp[i] = queue[i];
    }
    queue = temp;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new java.lang.NullPointerException();
    }
    if (size == queue.length) {
      resize(2 * size);
    }
    queue[size++] = item;
  } // add the item

  public Item dequeue() {
    if (size == 0) {
      throw new java.util.NoSuchElementException();
    }
    int randomNumber = StdRandom.uniform(size);
    Item item = queue[randomNumber];
    size--;
    queue[randomNumber] = queue[size];
    queue[size] = null;
    if (size > 0 && size <= queue.length / 4) {
      resize(queue.length / 2);
    }
    return item;
  } // remove and return a random item

  public Item sample() {
    if (size == 0)
      throw new java.util.NoSuchElementException("Empty queue");
    int randomNumber = StdRandom.uniform(size);
    Item item = queue[randomNumber];
    return item;
  } // return (but do not remove) a random item

  public Iterator<Item> iterator() {
    return new RandomArrayIterator();
  }// return an independent iterator over items in random order

  private class RandomArrayIterator implements Iterator<Item> {
    int count;
    int[] temp;

    public RandomArrayIterator() {
      count = 0;
      temp = new int[size];
      for (int i = 0; i < temp.length; i++) {
        temp[i] = i;
      }
      StdRandom.shuffle(temp);
    }

    public boolean hasNext() {
      return count < size;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {

      if (!hasNext())
        throw new NoSuchElementException();
      return queue[temp[count++]];
    }

  }

  /*
   * private class RandomArrayIterator implements Iterator<Item> { int count;
   * public RandomArrayIterator() { count = 0; } public boolean hasNext() {
   * return count < size; } public void remove() { throw new
   * UnsupportedOperationException();} public Item next(){
   * 
   * if (!hasNext()) throw new NoSuchElementException(); int randomNumber =
   * StdRandom.uniform(size); Item item = queue[randomNumber]; count++; return
   * item; } }
   */
  public static void main(String[] args) {
    RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
    for (int j = 0; j < 10; j++) {
      rQueue.enqueue(j);
    }
    System.out.println(rQueue.size);

    for (int j = 0; j < 10; j++) {
      StdOut.print(StdRandom.uniform(rQueue.size));
    }
    StdOut.println();
    for (int j : rQueue) {
      StdOut.print(j);
    }
    StdOut.println();
    for (int j = 0; j < 10; j++) {
      StdOut.print(rQueue.dequeue());
    }

  } // unit testing (optional)
}