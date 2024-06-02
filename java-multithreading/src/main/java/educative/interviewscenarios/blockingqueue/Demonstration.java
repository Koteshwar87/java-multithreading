package educative.interviewscenarios.blockingqueue;

public class Demonstration {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(5);

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    try {
                        blockingQueue.enqueue(i);
                        System.out.println("Enqueued: " + i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        for (int i = 0; i < 25; i++) {
                            Integer dequeuedValue = blockingQueue.dequeue();
                            System.out.println("Thread " + Thread.currentThread().getName() + " Dequeued value: " + dequeuedValue);
                        }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Consumer1");

        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        for (int i = 0; i < 25; i++) {
                            Integer dequeuedValue = blockingQueue.dequeue();
                            System.out.println("Thread " + Thread.currentThread().getName() + " Dequeued value: " + dequeuedValue);
                        }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Consumer2");

        producer.start();
        Thread.sleep(4000);

        consumer1.start();
        consumer1.join();

        consumer2.start();

        producer.join();
        consumer2.join();




    }
}

class BlockingQueue<T> {
    T[] array;
    Object lock = new Object();
    int capacity;
    int size = 0;
    int tail = 0;
    int head = 0;

    public BlockingQueue(int capacity) {
        // How to initialize the type of BlockingQueue?
        this.capacity = capacity;
        array = (T[]) new Object[capacity];
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (size == capacity) {
                lock.wait();
            }

            if (tail == capacity) {
                tail = 0;
            }
            array[tail] = item;
            tail++;
            size++;

            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }

            if (head == capacity) {
                head = 0;
            }

            item = array[head];
            head++;
            size--;
            lock.notifyAll();
        }
        return item;
    }
}
