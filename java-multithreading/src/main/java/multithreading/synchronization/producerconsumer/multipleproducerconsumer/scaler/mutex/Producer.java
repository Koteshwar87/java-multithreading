package multithreading.synchronization.producerconsumer.multipleproducerconsumer.scaler.mutex;

import java.util.Queue;

public class Producer implements Runnable{
    private Queue<Object> queue;
    private int maxSize;
    private String threadName;

    Producer(Queue<Object> queue, int maxSize, String threadName){
        this.queue = queue;
        this.maxSize = maxSize;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        while (true){
            synchronized (queue){  // lock.lock()
                if (queue.size() < maxSize){
                    queue.add(new Object());
                    System.out.println(threadName + " is producing an element & size is " + queue.size());
                    /*try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }*/
                }
            } // lock.unLock()
        }
    }
}
