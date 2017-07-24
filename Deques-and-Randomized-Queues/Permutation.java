import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;;

public class Permutation {
  public static void main(String[] args){
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    
    String[] simpleStrings = new String[k];
    
    for (int i = 0; i < simpleStrings.length; i++) {
      simpleStrings[i] = StdIn.readString();
      randomizedQueue.enqueue(simpleStrings[i]);
    }
    
    StdRandom.shuffle(simpleStrings);
    for (String str: simpleStrings) {
		StdOut.print(str);
	}
	}
}
