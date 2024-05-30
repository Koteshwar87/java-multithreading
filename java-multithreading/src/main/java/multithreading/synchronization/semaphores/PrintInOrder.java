package multithreading.synchronization.semaphores;

import java.util.concurrent.Semaphore;

public class PrintInOrder {
    private static class Foo {  // This is a shared object?
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);
        Semaphore s3 = new Semaphore(0);
        public Foo() {
        }

        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            s1.acquire();
            printFirst.run();
            s2.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            // printSecond.run() outputs "second". Do not change or remove this line.
            s2.acquire();
            printSecond.run();
            s3.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            // printThird.run() outputs "third". Do not change or remove this line.
            s3.acquire();
            printThird.run();
        }
    }
    public static void main(String[] args) {
        Foo foo = new Foo();

        Thread first = new Thread(() -> {
            try {
                foo.first(() -> System.out.println("first"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread second = new Thread(() -> {
            try {
                foo.second(() -> System.out.println("second"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread third = new Thread(() -> {
            try {
                foo.third(() -> System.out.println("third"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        first.start();
        second.start();
        third.start();


        try {
            first.join();
            second.join();
            third.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
