package multithreading.deadlock;

class SharedResource{
    Object objA = new Object();
    Object objB = new Object();
}

class Thread1 implements Runnable{
    SharedResource sharedResource;

    public Thread1(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        synchronized (sharedResource.objA){
            System.out.println("Acquired lock on objA");
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        synchronized (sharedResource.objB){
            System.out.println("Acquired lock on objB");
        }
    }
}

class Thread2 implements Runnable{
    SharedResource sharedResource;

    public Thread2(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        synchronized (sharedResource.objB){
            System.out.println("Acquired lock on objB");
        }
        synchronized (sharedResource.objA){
            System.out.println("Acquired lock on objA");
        }
    }
}

public class DeadlockEx2 {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread t1 = new Thread(new Thread1(sharedResource));
        Thread t2 = new Thread(new Thread2(sharedResource));

        t1.start();
        t2.start();
    }
}
