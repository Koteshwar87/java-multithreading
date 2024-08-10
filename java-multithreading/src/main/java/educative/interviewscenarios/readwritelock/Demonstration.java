package educative.interviewscenarios.readwritelock;

public class Demonstration {

    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLock lock = new ReadWriteLock();

        Thread t1 = new Thread(() -> {
            try {
                System.out.println("Attempting to acquire write lock in t1: " + System.currentTimeMillis());
                lock.acquireWriteLock();
                System.out.println("write lock acquired t1: " + +System.currentTimeMillis());

                // Simulates write lock being held indefinitely
                for (; ; ) {
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("Attempting to acquire write lock in t2: " + System.currentTimeMillis());
                lock.acquireWriteLock();
                System.out.println("write lock acquired t2: " + System.currentTimeMillis());

            } catch (InterruptedException ie) {
            }
        });

        Thread tReader1 = new Thread(() -> {
            try {
                lock.acquireReadLock();
                System.out.println("tReader1 Read lock acquired: " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread tReader2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("tReader2 Read lock about to release: " + System.currentTimeMillis());
                lock.releaseReadLock();
                System.out.println("tReader2 Read lock released: " + System.currentTimeMillis());
            }
        });

        tReader1.start();
        t1.start();
        Thread.sleep(3000);
        tReader2.start();
        Thread.sleep(1000);
        t2.start();
        tReader1.join();
        tReader2.join();
        t2.join();
    }
}

class ReadWriteLock {
    boolean isWriteLock = false;
    int reads = 0;

    public synchronized void acquireReadLock() throws InterruptedException {
        while (isWriteLock) {
            wait();
        }

        reads++;
    }

    public synchronized void releaseReadLock() {
        reads--;
        notify();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
        while (isWriteLock || reads != 0) {
            wait();
        }

        isWriteLock = true;
    }

    public synchronized void releaseWriteLock() {
        isWriteLock = false;
        notify();
    }
}