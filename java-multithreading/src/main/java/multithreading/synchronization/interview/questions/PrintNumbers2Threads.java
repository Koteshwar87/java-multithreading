package multithreading.synchronization.interview.questions;

/**
 * This is my approach
 */
class SharedResource implements Runnable {
    private int counter;
    private boolean isEven;
    private Object lock = new Object();

    SharedResource(int counter, boolean isEven) {
        this.counter = counter;
        this.isEven = isEven;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 1; i <= 10; i++) {
                try {
                    while ((isEven && (counter % 2 != 0)) || (!isEven && (counter % 2 == 0))) {
                        lock.wait();
                    }
                    System.out.println(Thread.currentThread().getName() + " :: counter = " + counter);
                    counter++;
                    isEven = !isEven; // Toggle for the next turn
                    lock.notifyAll(); // Notify all waiting threads to check the condition again
                    lock.wait();  // Release the lock and wait for the next turn
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

public class PrintNumbers2Threads {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(0, true);

        Thread evenThread = new Thread(sharedResource, "Even-Thread");
        Thread oddThread = new Thread(sharedResource, "Odd-Thread");

        evenThread.start();
        oddThread.start();
    }
}


