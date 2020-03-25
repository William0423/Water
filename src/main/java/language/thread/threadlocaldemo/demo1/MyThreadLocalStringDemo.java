package language.thread.threadlocaldemo.demo1;

import java.util.concurrent.CountDownLatch;

public class MyThreadLocalStringDemo {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private String getString() {
        return threadLocal.get();
    }
    private void setString(String string) {
        threadLocal.set(string);
    }
    public static void main(String[] args) {
        int threads = 9;
        MyThreadLocalStringDemo demo = new MyThreadLocalStringDemo();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(() -> {
                demo.setString(Thread.currentThread().getName());
                System.out.println(demo.getString());
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                    , "thread - " + i);
            thread.start();
        }

        countDownLatch.countDown();
    }

}
