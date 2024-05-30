package multithreading.synchronization.producerconsumer.multipleproducerconsumer.scaler.semaphore;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable{
    private Queue<Object> queue;
    private int maxSize;
    private String threadName;
    private Semaphore producerSemaphore;
    private Semaphore consumerSemaphore;

    Consumer(Queue<Object> queue, int maxSize, String threadName, Semaphore producerSemaphore, Semaphore consumerSemaphore){
        this.queue = queue;
        this.maxSize = maxSize;
        this.threadName = threadName;
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
    }

    @Override
    public void run() {
        while (true){
            try {
                consumerSemaphore.acquire();  // cs--
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(threadName + " is consuming an element & size is " + queue.size());
            queue.remove();
            producerSemaphore.release();  // ps++
        }
    }
}
