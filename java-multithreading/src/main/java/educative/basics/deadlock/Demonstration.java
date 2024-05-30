package educative.basics.deadlock;

public class Demonstration {
    public static void main(String[] args) throws InterruptedException {
        Deadlock deadlock = new Deadlock();
        deadlock.runTest();
        String str = "";
    }
}

class Deadlock {
    private int counter = 0;
    Object lock1 = new Object();
    Object lock2 = new Object();

    Runnable incrementer = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++){
                try {
                    incrementer();
                    System.out.println("Incrementing " + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    Runnable decrementer = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++){
                try {
                    decrementer();
                    System.out.println("Decrementing " + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    void incrementer() throws InterruptedException {
        synchronized (lock1){
            System.out.println("Acquired lock1");

            Thread.sleep(100);
            synchronized (lock2){
                counter++;
            }
        }
    }

    void decrementer() throws InterruptedException {
        synchronized (lock2){
            System.out.println("Acquired lock2");

            Thread.sleep(100);
            synchronized (lock1){
                counter--;
            }
        }
    }

    public void runTest() throws InterruptedException {
        Thread t1 = new Thread(incrementer);
        Thread t2 = new Thread(decrementer);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Done : " + counter);
    }
}
