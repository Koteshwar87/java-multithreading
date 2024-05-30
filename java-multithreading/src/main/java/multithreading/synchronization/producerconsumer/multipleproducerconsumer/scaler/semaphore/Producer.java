package multithreading.synchronization.producerconsumer.multipleproducerconsumer.scaler.semaphore;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable{
    private Queue<Object> queue;
    private int maxSize;
    private String threadName;
    private Semaphore producerSemaphore;
    private Semaphore consumerSemaphore;

    Producer(Queue<Object> queue, int maxSize, String threadName, Semaphore producerSemaphore, Semaphore consumerSemaphore){
        this.queue = queue;
        this.maxSize = maxSize;
        this.threadName = threadName;
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(threadName + " is producing an element & size is " + queue.size());
            try {
                producerSemaphore.acquire();  // ps--
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            queue.add(new Object());
            consumerSemaphore.release();  // cs++
        }
    }
}
