package educative.interviewscenarios.implementsemaphore;

public class Demonstration {
    public static void main(String[] args) throws InterruptedException {
        CountingSemaphore countingSemaphore = new CountingSemaphore(1);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    countingSemaphore.acquire();
                    System.out.println("Ping " + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i= 0; i < 5; i++) {
                try {
                    countingSemaphore.release();
                    System.out.println("Pong " + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t2.start();

        t1.join();
        t2.join();
    }
}

class CountingSemaphore {
    int maxCount;
    int usedPermits = 0;

    public CountingSemaphore(int maxCount) {
        this.maxCount = maxCount;
    }

    public synchronized void acquire() throws InterruptedException {
        if (usedPermits == maxCount) {
            wait();
        }

        usedPermits++;
        System.out.println("Acquired lock");
        notifyAll();
    }

    public synchronized void release() throws InterruptedException {
        if (usedPermits == 0) {
            wait();
        }

        usedPermits--;
        System.out.println("Released lock");
        notifyAll();
    }
}
