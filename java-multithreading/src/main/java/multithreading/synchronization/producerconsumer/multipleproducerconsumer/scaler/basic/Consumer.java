package multithreading.synchronization.producerconsumer.multipleproducerconsumer.scaler.basic;

import java.util.Queue;

public class Consumer implements Runnable{
    private Queue<Object> queue;
    private int maxSize;
    private String threadName;

    Consumer(Queue<Object> queue, int maxSize, String threadName){
        this.queue = queue;
        this.maxSize = maxSize;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        while (true){
            if (queue.size() > 0){
                System.out.println(threadName + " is consuming an element & size is " + queue.size());
                queue.remove();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
