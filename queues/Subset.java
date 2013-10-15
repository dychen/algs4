/*
 * Daniel Chen
 * 10/14/13
 */

public class Subset {
    /*
     * Create a new RandomizedQueue, enqueue the input, then dequeue k items.
     */
    public static void main(String[] args) {
        String input;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            input = StdIn.readString();
            rq.enqueue(input);
        }
        for (int i = 0; i < k; i++)
            StdOut.println(rq.dequeue());
    }
}