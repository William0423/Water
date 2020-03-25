package language.thread.threadlocaldemo.demo1;

import java.util.concurrent.CountDownLatch;

public class MyStringDemo {
    private String string;
    private String getString() {
        return string;
    }
    private void setString(String string) {
        this.string = string;
    }
    public static void main(String[] args) throws InterruptedException {
        int threads = 9;
        MyStringDemo demo = new MyStringDemo();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(() -> {
                demo.setString(Thread.currentThread().getName());
                System.out.println(demo.getString());
//                try {
                    countDownLatch.countDown();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }, "thread - " + i);
            thread.start();
        }
        countDownLatch.countDown();

        System.out.println("hah");
    }
}