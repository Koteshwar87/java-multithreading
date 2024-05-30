package multithreading.deadlock;

public class Example {
    static class SharedResource {
        final Object lockA = new Object();
        final Object lockB = new Object();
    }

    static class Thread1 extends Thread {
        private final SharedResource resources;

        Thread1(SharedResource resources) {
            this.resources = resources;
        }

        // To fix this issue acquire lock in same order
        @Override
        public void run() {
            synchronized (resources.lockA) {
                System.out.println("Thread1 locked resource A");

                // Simulate some work
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resources.lockB) {
                    System.out.println("Thread1 locked resource B");
                }
            }
        }
    }

    static class Thread2 extends Thread {
        private final SharedResource resources;

        Thread2(SharedResource resources) {
            this.resources = resources;
        }

        // To fix this issue acquire lock in same order
        @Override
        public void run() {
            synchronized (resources.lockB) {
                System.out.println("Thread2 locked resource B");

                // Simulate some work
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resources.lockA) {
                    System.out.println("Thread2 locked resource A");
                }
            }
        }
    }

    public static void main(String[] args) {
        SharedResource resources = new SharedResource();

        Thread1 thread1 = new Thread1(resources);
        Thread2 thread2 = new Thread2(resources);

        thread1.start();
        thread2.start();
    }
}
