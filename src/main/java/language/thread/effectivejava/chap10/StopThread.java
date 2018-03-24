package language.thread.effectivejava.chap10;

import java.util.concurrent.TimeUnit;

public class StopThread {

    private static volatile boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i =0;
                while (!stopRequested)
                    i++;
            }
        }).start();

        System.out.println("*****");

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;


    }


}
