package multithreading.synchronization.threadcommunication;

class SharedResource{
    public synchronized void waitForSignal(){
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting for a signal.");
            wait();
            System.out.println(Thread.currentThread().getName() + " received the signal.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void sendSignal(){
        System.out.println("Sending signal to waiting thread.");
        notify(); // Notify one waiting thread
        // notifyAll(); // Notify all waiting threads
    }
}

class WaitingThread implements Runnable{
    private SharedResource sharedResource;

    public WaitingThread(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        sharedResource.waitForSignal();
    }
}

class NotifyingThread implements Runnable{
    private SharedResource sharedResource;

    public NotifyingThread(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        sharedResource.sendSignal();
    }
}

public class WaitExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread waitingThread = new Thread(new WaitingThread(sharedResource));
//        Thread waitingThread1 = new Thread(new WaitingThread(sharedResource));
        Thread notifyingThread = new Thread(new NotifyingThread(sharedResource));

        waitingThread.start();
//        waitingThread1.start(); // Here 2 threads are waiting for signal, it will be random notify signal will be sent to any thread
        notifyingThread.start();

        try {
            waitingThread.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
