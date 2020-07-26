package interview.multithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculator {


    private static class SumTask extends RecursiveTask<Long> {

        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }


        @Override
        protected Long compute() {
            if (to - from < 6) {
                long total = 0;
                for (int i=from;i<=to;i++) {
                    total += numbers[i];
                }
                return total;
            } else {
                int middle = (to + from) / 2;
                SumTask leftTask = new SumTask(numbers, from, middle);
                SumTask rightTask = new SumTask(numbers, middle+1, to);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join()+rightTask.join();
            }
        }



    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ForkJoinPool pool = new ForkJoinPool();
        long[] nums = new long[100000000];
        for (int i = 1;i<=100000000; i++) {
            nums[i-1] = i;
        }

//        ForkJoinTask<Long> task = new SumTask(nums, 0, nums.length-1);
        Long r = pool.invoke(new SumTask(nums, 0, nums.length-1));
        System.out.println(r);


    }
}
