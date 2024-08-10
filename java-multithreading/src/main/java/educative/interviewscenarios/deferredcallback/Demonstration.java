package educative.interviewscenarios.deferredcallback;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Demonstration {
    public static void main(String[] args) throws InterruptedException {
        DeferredCallbackExecutor.runTestTenCallbacks();
//        DeferredCallbackExecutor.runLateThenEarlyCallback();
    }
}

class DeferredCallbackExecutor {
    PriorityQueue<Callback> priorityQueue = new PriorityQueue<>(
            (callback1, callback2) -> (int) (callback1.executeAt - callback2.executeAt));
    ReentrantLock lock = new ReentrantLock();
    Condition newCallbackArrived = lock.newCondition();

    // This method is used by Executor thread
    public void start() throws InterruptedException {
        long timeToWait = 0;
        while (true) {
            lock.lock();
            while (priorityQueue.size() == 0) {
                newCallbackArrived.await();
            }

            while (priorityQueue.size() != 0) {
                timeToWait = findSleepDuration();

                if (timeToWait <= 0) {
                    break;
                }

                newCallbackArrived.await(timeToWait, TimeUnit.MICROSECONDS);
            }
            Callback callback = priorityQueue.poll();
            System.out.println("Executed at " + System.currentTimeMillis() / 1000 + " required at " +
                    callback.executeAt / 1000 + ": message: " + callback.message);
            lock.unlock();
        }
    }

    private long findSleepDuration() {
        long currentTime = System.currentTimeMillis();
        return priorityQueue.peek().executeAt - currentTime;
    }

    // This method is used by Consumer thread
    public void registerCallback(Callback callback) {
        lock.lock();
        priorityQueue.add(callback);
        newCallbackArrived.signal();
        lock.unlock();
    }

    public static void runTestTenCallbacks() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        final DeferredCallbackExecutor deferredCallbackExecutor = new DeferredCallbackExecutor();

        Thread service = new Thread(() -> {
            try {
                deferredCallbackExecutor.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.start();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                Callback cb = new Callback(1, "Hello this is " + Thread.currentThread().getName());
                deferredCallbackExecutor.registerCallback(cb);
            });
            thread.setName("Thread " + (i + 1));
            thread.start();
            allThreads.add(thread);
            Thread.sleep(1000);
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }

    public static void runLateThenEarlyCallback() throws InterruptedException {
        final DeferredCallbackExecutor deferredCallbackExecutor = new DeferredCallbackExecutor();

        Thread service = new Thread(() -> {
            try {
                deferredCallbackExecutor.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.start();

        Thread lateThread = new Thread(() -> {
            Callback cb = new Callback(8, "Hello this is the callback submitted first");
            deferredCallbackExecutor.registerCallback(cb);
        });
        lateThread.start();
        Thread.sleep(3000);

        Thread earlyThread = new Thread(() -> {
            Callback cb = new Callback(1, "Hello this is callback sumbitted second");
            deferredCallbackExecutor.registerCallback(cb);;
        });
        earlyThread.start();

        lateThread.join();
        earlyThread.join();
    }

    static class Callback {
        long executeAt;
        String message;

        public Callback(long executeAfter, String message) {
            this.executeAt = System.currentTimeMillis() + executeAfter * 1000;
            this.message = message;
        }
    }
}
