package multithreading.synchronization.producerconsumer.singleproducerconsumer.practice;

import java.util.LinkedList;

/**
 * Create Producer and Consumer using Lambdas directly in Main class.
 * Instead of 2 seperate classes.
 */
class SharedResource{
    private int capacity;
    LinkedList<Integer> list = new LinkedList<>();

    public SharedResource(int capacity){
        this.capacity = capacity;
    }

    public synchronized void produce(int data) throws InterruptedException {
        if(list.size() == capacity){
            System.out.println("Buffer is full so waiting for signal");
            wait();
            System.out.println("Received signal");
        }
        System.out.println("Produced item: " + data);
        list.add(data);
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        if(list.isEmpty()){
            System.out.println("Buffer is empty waiting for signal");
            wait();
            System.out.println("Received signal");
        }
        int data = list.getFirst();
        list.removeFirst();
        System.out.println("Consumed item: " + data);
        notifyAll();
    }
}

public class ProducerConsumerLambda {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(2);

        Thread producer = new Thread(() -> {
            while (true){
                int data = (int) (Math.random()*100);
                try {
                    sharedResource.produce(data);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                /*try {
//                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
            }
        });

        Thread consumer = new Thread(() -> {
           while (true){
               try {
                   sharedResource.consume();
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               /*try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }*/
           }
        });

        producer.start();
//        producer2.start();
        consumer.start();
    }
}
