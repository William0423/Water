package language.thread.problems.problem0003;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AnswerFive {

    public static void main(String[] args) {
//        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
//        char[] chars = { 'a', 'b', 'c', 'd', 'e' };
//        StringBuffer stringBuffer = new StringBuffer();
//        int cnt = 0;
//        final AtomicInteger atomicInteger = new AtomicInteger();
//        Executor executor = Executors.newFixedThreadPool(1);
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, ()-> {
//            executor.execute(()-> {
//                for (int i=atomicInteger.get()*2; i<atomicInteger.get()*2+2; i++) {
//                    stringBuffer.append(nums[i]);
//                    atomicInteger.incrementAndGet();
//                }
//            });
//        });
//
//
//        Thread t1 = new Thread(() -> {
//            for (int i=0;i<chars.length;i++) {
//                stringBuffer.append(chars[i]);
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        t1.start();
//        System.out.println(stringBuffer.toString());




            String s = new String("1");
            s.intern();
            String s2 = "1";
            System.out.println(s == s2);

            String s3 = new String("1") + new String("1");
            s3.intern();
            String s4 = "11";
            System.out.println(s3 == s4);


//        String s = new String("1");
//        String s2 = "1";
//        s.intern();
//        System.out.println(s == s2);
//
//        String s3 = new String("1") + new String("1");
//        String s4 = "11";
//        s3.intern();
//        System.out.println(s3 == s4);
    }

}
