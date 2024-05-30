package multithreading.synchronization.producerconsumer.singleproducerconsumer.practice;

import java.util.LinkedList;

/**
 * Basic example with a Buffer class, Producer class, and Consumer class and a Main class.
 * Buffer have produce() and consume() methods
 * Both ProducerThread and ConsumerThread should be synchronized which are executing produce() and consume() methods respectively.
 */
class Buffer{
    private LinkedList<Integer> queue = new LinkedList<>();
    private int capacity = 0;
    public Buffer(int capacity){
        this.capacity = capacity;
    }

    public void produce(int item) {
        // No synchronization, potential race conditions

        // Produce an item and add it to the buffer
        queue.add(item);
        System.out.println("Produced: " + item);
    }

    /*public void produce(int num) throws InterruptedException {
        synchronized (this){
            if(queue.size() == capacity){
                wait();
            }
            queue.add(num);
            System.out.println("Produced : " + num);

            notifyAll();
        }
    }*/

    public void consume() {
        // No synchronization, potential race conditions

        // Consume an item from the buffer
        if (!queue.isEmpty()) {
            int item = queue.removeFirst();
            System.out.println("Consumed: " + item);
        }
    }
    /*public void consume() throws InterruptedException {
        synchronized (this){
            if(queue.isEmpty()){
                wait();
            }
            int num = queue.removeFirst();
            System.out.println(("Consumed : " + num));
            notifyAll();
        }
    }*/
}

class Producer implements Runnable{
    private Buffer buffer;

    public Producer(Buffer buffer){
        this.buffer = buffer;
    }

    /*public void run() {
        for (int i = 0; i < 5; i++) {
            buffer.produce(i);
            try {
                Thread.sleep(1000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }*/

    @Override
    public void run() {

            try {
                for (int i = 0; i < 5; i++) {
                    buffer.produce(i);
                }
                Thread.sleep(1000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }
}

class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    /*public void run() {
        for (int i = 0; i < 5; i++) {
            buffer.consume();
            try {
                Thread.sleep(1500); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }*/

    public void run() {

            try {
                for (int i = 0; i < 5; i++) {
                    buffer.consume();
                }
                Thread.sleep(1500); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


public class BasicProducerConsumer {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(2);

        Thread producerThread = new Thread(new Producer(buffer));
        Thread consumerThread = new Thread(new Consumer(buffer));

        producerThread.start();
        consumerThread.start();
    }
}
