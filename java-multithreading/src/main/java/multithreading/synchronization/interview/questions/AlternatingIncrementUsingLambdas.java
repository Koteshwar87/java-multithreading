package multithreading.synchronization.interview.questions;

public class AlternatingIncrementUsingLambdas {
    private static int counter = 0;
    private static final int MAX_COUNT = 10;

    public static void main(String[] args) {
        Object lock = new Object();

        Thread thread1 = new Thread(() -> {  // Odd Thread
            for (int i = 0; i < MAX_COUNT; i++) {
                synchronized (lock) {
                    if (counter % 2 != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    counter++;
                    System.out.println("Thread 1: Counter = " + counter);
                    lock.notify();
                }
            }
        });

        Thread thread2 = new Thread(() -> {  // Even Thread
            for (int i = 0; i < MAX_COUNT; i++) {
                synchronized (lock) {
                    if (counter % 2 == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    counter++;
                    System.out.println("Thread 2: Counter = " + counter);
                    lock.notify();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Counter Value: " + counter);
    }
}
