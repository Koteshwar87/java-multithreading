package multithreading.volatileexamples;

class SharedResource {
    private boolean flag = false;
//    private volatile boolean flag = false;

    public void setFlag() {
        System.out.println(Thread.currentThread().getName() + " set flag");
        flag = true;  // Thread 1 sets the flag to true
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkFlag() {
        System.out.println(Thread.currentThread().getName() + " check flag");
        if (flag) {
            // Thread 2 checks the flag
            System.out.println("Flag is true");
        } else {
            System.out.println("Flag is false");
        }
    }
}
public class FlagExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Thread 1
        Thread thread1 = new Thread(() -> {
            sharedResource.setFlag();
        });

        // Thread 2
        Thread thread2 = new Thread(() -> {
            sharedResource.checkFlag();
        });

        // Start the threads
        thread1.start();
        thread2.start();
    }
}
