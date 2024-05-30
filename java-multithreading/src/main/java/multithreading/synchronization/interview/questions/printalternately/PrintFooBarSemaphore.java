package multithreading.synchronization.interview.questions.printalternately;

import java.util.concurrent.Semaphore;

public class PrintFooBarSemaphore {
    private static class FooBar {
        private int n;
        Semaphore foo = new Semaphore(1);
        Semaphore bar = new Semaphore(0);

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                foo.acquire();
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                bar.release();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                bar.acquire();
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                foo.release();
            }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(10);

        Thread foo = new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread bar = new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        foo.start();
        bar.start();
    }
}
