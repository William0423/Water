package interview.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 极可能快的用
 */
public class Calculation {

    static AtomicLong total = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(100000000L * (100000001L)/2);

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);


        // 1 ~ 10000000
        // 10000001 ~ 20000000
        long end = 100000000L;
        for (long i=0; i<10; i++) {
            long param = (end-i*10000000);
            pool.submit(new CalRun(param));
        }
        pool.shutdown();
        while (!pool.awaitTermination(1,TimeUnit.SECONDS));

        System.out.println(total.get());
    }

    private static class CalRun implements Runnable {
        long endNum;
        public CalRun(long startNum) {
            this.endNum = startNum;
        }

        @Override
        public void run() {
            total.getAndAdd((endNum + (endNum - 9999999)) * 10000000/2 );
        }
    }



}
