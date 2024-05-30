package multithreading.synchronization.threadcommunication;

class SharedResource2 {
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
        System.out.println("Sending signal to waiting threads.");
        // notify(); // Notify one waiting thread
        notifyAll(); // Notify all waiting threads
    }
}

public class NotifyAllExample {
    public static void main(String[] args) {
        SharedResource2 sharedResource = new SharedResource2();

        Thread waitingThread1 = new Thread(() -> sharedResource.waitForSignal(), "WaitingThread1");
        Thread waitingThread2 = new Thread(() -> sharedResource.waitForSignal(), "WaitingThread2");
        Thread signalingThread = new Thread(() -> sharedResource.sendSignal(), "SignalingThread");

        waitingThread1.start();
        waitingThread2.start();
        signalingThread.start();
    }
}

