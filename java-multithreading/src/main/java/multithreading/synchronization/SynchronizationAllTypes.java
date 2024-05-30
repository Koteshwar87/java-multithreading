package multithreading.synchronization;

/*

        1. **Synchronized Methods:**
        - *Question:* What is the purpose of `synchronized` methods `incrementCounter1` and `incrementCounter2`?
        - *Answer:* The purpose is to ensure that only one thread can execute each of these methods at a time for a given instance of `SharedResources`. This helps in maintaining the
            integrity of the shared variables `counter1` and `counter2` by preventing concurrent modifications.
        - *Question:* How does the use of `synchronized` on these methods affect concurrent access?
        - *Answer:* Concurrent access to `incrementCounter1` and `incrementCounter2` is serialized. If one thread is executing `incrementCounter1`, other threads must wait until it completes.
            This prevents race conditions and ensures that increments are performed atomically.

        2. **Synchronized Blocks in Non-Synchronized Methods:**
        - *Question:* Why are there `synchronized` blocks within the non-synchronized methods `nonSynchronizedMethod1` and `nonSynchronizedMethod2`?
        - *Answer:* `synchronized` blocks within these methods aim to protect the critical sections of code (where shared resources are accessed) by ensuring that only one thread can execute
            those sections at a time.
        - *Question:* What is the purpose of using separate lock objects (`lock1` and `lock2`) for synchronization?
        - *Answer:* Separate lock objects are used to allow independent synchronization of different critical sections. This prevents contention between threads accessing different resources
            concurrently, improving concurrency.

        3. **Combination of Synchronized and Non-Synchronized Methods:**
        - *Question:* How does the class balance the use of synchronized and non-synchronized methods to ensure data consistency?
        - *Answer:* The class uses synchronization where necessary to protect shared resources (`counter1` and `counter2`). Non-synchronized methods are employed when concurrent access
            doesn't pose a risk of data inconsistency.
        - *Question:* What considerations should be taken into account when choosing which methods to synchronize?
        - *Answer:* Synchronization should be applied judiciously to balance thread safety and performance. Overuse of synchronization can lead to contention and reduced concurrency,
            while insufficient synchronization can result in data corruption.

        4. **Read and Write Operations:**
        - *Question:* Explain the purpose of `getCounter1` and `getCounter2`.
        - *Answer:* These methods provide a way to read the values of `counter1` and `counter2` in a thread-safe manner. The `synchronized` blocks ensure that reading and writing operations
            are atomic and prevent inconsistencies due to concurrent modifications.
        - *Question:* Why are `synchronized` blocks used in these read operations?
        - *Answer:* While read operations (`getCounter1` and `getCounter2`) might not require synchronization for consistency, using `synchronized` blocks ensures visibility of the latest
            values and provides a consistent view of the shared state.

        5. **Concurrency and Race Conditions:**
        - *Question:* Can you identify potential race conditions in this class?
        - *Answer:* Without synchronization, there could be race conditions during increments or read operations, where multiple threads concurrently modify or read the shared variables.
            This could lead to data corruption or inconsistent states.
        - *Question:* How does the class mitigate the risk of race conditions through synchronization?
        - *Answer:* Synchronization, through both `synchronized` methods and blocks, ensures that critical sections are executed atomically, preventing race conditions and maintaining
            the integrity of shared resources.
*/

class SharedResources {
    private int counter1 = 0;
    private int counter2 = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public synchronized void incrementCounter1() {
        // Synchronized method
        counter1++;
    }

    public synchronized void incrementCounter2() {
        // Synchronized method
        counter2++;
    }

    public void nonSynchronizedMethod1() {
        // Some non-critical section code
        synchronized (lock1) {
            // Synchronized block in a non-synchronized method
            counter1++;
        }
        // Some more non-critical section code
    }

    public void nonSynchronizedMethod2() {
        // Some non-critical section code
        synchronized (lock2) {
            // Synchronized block in a non-synchronized method
            counter2++;
        }
        // Some more non-critical section code
    }

    public int getCounter1() {
        synchronized (lock1) {
            // Synchronized block in a non-synchronized method
            return counter1;
        }
    }

    public int getCounter2() {
        synchronized (lock2) {
            // Synchronized block in a non-synchronized method
            return counter2;
        }
    }
}

public class SynchronizationAllTypes {
}
