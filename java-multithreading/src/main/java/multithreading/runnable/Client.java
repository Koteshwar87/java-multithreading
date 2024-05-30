package multithreading.runnable;

class Adder implements Runnable{
    private int a;
    private int b;

    public Adder(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        System.out.println(a+b);
    }
}
public class Client {

    int data = 20;
    static int data2 = 20;

    void mt(){
        data2 = 50;
    }

    public static void main(String[] args) {
//        data = 30;
        Thread t = new Thread(new Adder(5,2));
        t.start();
    }
}
