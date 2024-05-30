package multithreading.synchronization.threadcommunication;

public class JoinExample {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
           for (int i = 1; i <= 5; i++){
               System.out.println("Thread 1 - Count: " + i);

               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });

        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
                for (int i = 1; i <= 5; i++){
                    System.out.println("Thread 2 - Count: " + i);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
    }
}
