package multithreading.synchronization.threadcommunication;

class SharedResource1 {
    public synchronized void waitForSignal() {
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting for a signal.");
            wait(); // Thread waits until notified
            System.out.println(Thread.currentThread().getName() + " received the signal.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void sendSignal() {
        System.out.println("Sending signal to waiting thread.");
        notify(); // Notify one waiting thread
        // notifyAll(); // Notify all waiting threads
    }
}

public class NotifyExample {
    public static void main(String[] args) {
        SharedResource1 sharedResource = new SharedResource1();

        Thread waitingThread = new Thread(() -> sharedResource.waitForSignal(), "WaitingThread");
        Thread signalingThread = new Thread(() -> sharedResource.sendSignal(), "SignalingThread");

        waitingThread.start();
        signalingThread.start();
    }
}

