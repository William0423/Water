package interview.multithreading;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.List;

public class ExecutorServiceCalculator {
    private static int parallams;
    private static ExecutorService  pool;


    private static class Task implements Callable<Long> {

        int start;
        int end;
        int[] nums;

        public Task(int start, int end, int[] nums) {
            this.start = start;
            this.end = end;
            this.nums = nums;
        }


        @Override
        public Long call() throws Exception {
            long tmpResult = 0;
            for (int i=start; i<=end; i++) {
                tmpResult += nums[i];
            }
            return tmpResult;
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        parallams = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(parallams);
        int[] nums = new int[100000000];
        for (int i = 1;i<=100000000; i++) {
            nums[i-1] = i;
        }
        List<Future<Long>> lst = new ArrayList<>();
        int part = nums.length / parallams;
        for (int i=0; i<parallams; i++) {
            int start = i*part;
            System.out.println(start);
            int to = (i == parallams-1) ? nums.length-1:(i+1) * part-1;
            System.out.println(to);
            lst.add(pool.submit(new Task(start, to, nums)));
        }

        long total = 0L;
        for (Future<Long> result : lst) {
            total += result.get();
        }

        pool.shutdown();
        while (!pool.isTerminated())
        System.out.println(total);

    }

}
