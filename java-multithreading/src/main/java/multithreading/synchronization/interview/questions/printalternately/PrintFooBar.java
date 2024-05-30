package multithreading.synchronization.interview.questions.printalternately;

import java.util.concurrent.ConcurrentHashMap;

public class PrintFooBar {
    private static class FooBar {
        private int n;
        boolean flag = true;
        Object lock = new Object();

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            synchronized(lock){
                for (int i = 0; i < n; i++) {
                    if(!flag){
                        try{
                            lock.wait();
                        }catch(InterruptedException e){
                            throw new RuntimeException(e);
                        }
                    }
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    flag = !flag;
                    lock.notify();
//                    lock.wait();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            synchronized(lock){
                for (int i = 0; i < n; i++) {
                    if(flag){
                        try{
                            lock.wait();
                        }catch(InterruptedException e){
                            throw new RuntimeException(e);
                        }
                    }
                    // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    flag = !flag;
                    lock.notify();
//                    lock.wait();
                }
            }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(10);

        Thread t1 = new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
    }
}
