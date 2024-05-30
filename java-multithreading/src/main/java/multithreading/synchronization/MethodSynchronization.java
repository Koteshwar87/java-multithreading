package multithreading.synchronization;
class Counter{
    private int counter = 0;

    public int getCount(){
        return counter;
    }
    public synchronized void increment(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter++;
//        System.out.println(Thread.currentThread().getId() + " incremented the count.");
    }
}

class IncrementThread extends Thread{
    private Counter counter;

    public IncrementThread(Counter counter){
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i =0; i < 10000; i++){
            counter.increment();
        }
    }
}

public class MethodSynchronization {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new IncrementThread(counter);
        Thread t2 = new IncrementThread(counter);

        t1.run();
        t2.run();

        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }
}
