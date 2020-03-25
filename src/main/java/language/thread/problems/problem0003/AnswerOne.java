package language.thread.problems.problem0003;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://segmentfault.com/a/1190000006671595
 * 数组A内容为 1,2,3,4...52 ,数组B内容为26个英文字母，使用两个线程分别输入两个数组，
 * 打印内容为：12a34b56c78e....... 这样的规律
 */

public class AnswerOne {


    // 这个实现也可以在while循环中用join实现：
    public static void main(String[] args) throws InterruptedException {
        StringBuffer stringBuffer = new StringBuffer();
        int[] arrOne = new int[52];
        for (int i=1;i<53;i++) {
            arrOne[i-1] = i;
        }

        String[] arrTwo = new String[26];

        for (int i=0;i<26;i++) {
            arrTwo[i]= String.valueOf((char )('a'+i));
        }

        Executor executor = Executors.newFixedThreadPool(2);
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        int cnt = 0;
        while (cnt < 26) {
            System.out.println(cnt);

            executor.execute(new numOperator(arrOne,countDownLatch,cnt,stringBuffer));
            countDownLatch.await();
            executor.execute(new strOperator(arrTwo, cnt, stringBuffer));
            cnt++;
            countDownLatch = new CountDownLatch(2);
        }
        System.out.println(stringBuffer.toString());
        ((ExecutorService) executor).shutdown();

    }

    private static class numOperator implements Runnable {
        int[] arr;
        CountDownLatch countDownLatch;
        int cnt;
        StringBuffer stringBuffer;
        public numOperator(int[] arrOne, CountDownLatch countDownLatch,
                           int cnt, StringBuffer stringBuffer) {
            this.arr = arrOne;
            this.countDownLatch = countDownLatch;
            this.cnt = cnt;
            this.stringBuffer = stringBuffer;
        }

        @Override
        public void run() {
            for (int i=cnt*2;i<cnt*2+2;i++) {
                stringBuffer.append(arr[i]);
                countDownLatch.countDown();
            }
        }
    }


    private static class strOperator implements Runnable {
        String[] arr;
        int cnt;
        StringBuffer stringBuffer;
        public strOperator(String[] arrTwo, int cnt, StringBuffer stringBuffer) {
            this.arr = arrTwo;
            this.cnt = cnt;
            this.stringBuffer = stringBuffer;
        }

        @Override
        public void run() {
            stringBuffer.append(arr[cnt]);
        }
    }
}
