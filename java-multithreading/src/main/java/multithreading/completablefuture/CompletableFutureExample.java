package multithreading.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    // We want to perform three tasks asynchronously but executed sequentially, and then combine their results
    public static CompletableFuture<String> task1() {
        /*System.out.println("task 1 waiting 10000 milli secs");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return CompletableFuture.supplyAsync(() -> "Task 1 Result");
    }

    public static CompletableFuture<String> task2() {
//        System.out.println("task 2 no waiting");
        return CompletableFuture.supplyAsync(() -> "Task 2 Result");
    }

    public static CompletableFuture<String> task3() {
        /*System.out.println("task 3 waiting 30000 milli sec");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return CompletableFuture.supplyAsync(() -> "Task 3 Result");
    }

    public static void main(String[] args) {
        CompletableFuture<String> future1 = task1();
        CompletableFuture<String> future2 = task2();
        CompletableFuture<String> future3 = task3();

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

        combinedFuture.thenRun(() -> {
            try {
                String result1 = future1.get();
                String result2 = future2.get();
                String result3 = future3.get();

                System.out.println(result1);
                System.out.println(result2);
                System.out.println(result3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
