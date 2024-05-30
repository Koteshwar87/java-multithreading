package multithreading.synchronization.producerconsumer.multipleproducerconsumer.scaler.basic;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/***
 * Queue size is becoming Overflow or Underflow. Synchronization issue on queue object.
 *
 * NoSuchElement Exception also occuring meaning Consumer is trying to remove element from the queue even when the Queue is empty.
 *
 * There is no Synchronization on the Queue between Producer threads and Consumer threads.
 *
 * Here we have used a Concurrent collection still we face issue because more that 1 trhreads are entering into Critical section.
 */
public class Main {
    public static void main(String[] args) {
        Queue<Object> queue = new ConcurrentLinkedQueue<>();
        int maxSize = 6;

        /*new Thread(new Producer(queue, maxSize, "p1")).start();
        new Thread(new Producer(queue, maxSize, "p2")).start();
        new Thread(new Producer(queue, maxSize, "p3")).start();
        new Thread(new Producer(queue, maxSize, "p4")).start();
        new Thread(new Producer(queue, maxSize, "p5")).start();
        new Thread(new Producer(queue, maxSize, "p6")).start();

        new Thread(new Producer(queue, maxSize, "C1")).start();
        new Thread(new Producer(queue, maxSize, "C2")).start();
        new Thread(new Producer(queue, maxSize, "C3")).start();
        new Thread(new Producer(queue, maxSize, "C4")).start();*/

        Producer p1 = new Producer(queue, maxSize, "P1");
        Thread t1 = new Thread(p1);
        t1.start();

        Producer p2 = new Producer(queue, maxSize, "P2");
        Thread t2 = new Thread(p2);
        t2.start();

        Producer p3 = new Producer(queue, maxSize, "P3");
        Thread t3 = new Thread(p3);
        t3.start();

        Producer p4 = new Producer(queue, maxSize, "P4");
        Thread t4 = new Thread(p4);
        t4.start();

        Producer p5 = new Producer(queue, maxSize, "P5");
        Thread t5 = new Thread(p5);
        t5.start();

        Producer p6 = new Producer(queue, maxSize, "P6");
        Thread t6 = new Thread(p6);
        t6.start();

        Consumer c1 = new Consumer(queue, maxSize, "C1");
        Thread t7 = new Thread(c1);
        t7.start();

        Consumer c2 = new Consumer(queue, maxSize, "C2");
        Thread t8 = new Thread(c2);
        t8.start();

        Consumer c3 = new Consumer(queue, maxSize, "C3");
        Thread t9 = new Thread(c3);
        t9.start();

        Consumer c4 = new Consumer(queue, maxSize, "C4");
        Thread t10 = new Thread(c4);
        t10.start();
    }
}
