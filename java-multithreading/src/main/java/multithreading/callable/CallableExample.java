package multithreading.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * For Callable its always preferrable to use ExecutorService
 * Callable can take a generic type which will become the return type of call()
 */
public class CallableExample implements Callable<String> {

    private String message;

    public CallableExample(String message) {
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(10000);
        return "Hello, " + message;
    }

    public static void main(String[] args) {
        // Create a Callable task
        Callable<String> callable = new CallableExample("world");

        // Create an ExecutorService to manage the execution of tasks
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            // Submit the task for execution, which returns a Future
            Future<String> future = executor.submit(callable);

            // Wait for the result and print it
            String result = future.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown the ExecutorService
            executor.shutdown();
        }
    }
}
