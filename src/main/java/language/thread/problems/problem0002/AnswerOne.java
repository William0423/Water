package src.thread.problem2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这3个线程的ID分别为A、B、C，每个线程将自己的ID在屏幕上打印10遍，要求输出结果必须按ABC的顺序显示；如：ABCABC….依次递推。
 * 题目要求：1、只能起三个线程，每次只有一个线程打印，其他线程等待，三个线程按顺序打印；
 *
 * https://blog.csdn.net/feicongcong/article/details/78563003
 */
public class AnswerOne {

    public static void main(String[] args) {

        LetterPrint letterPrint = new LetterPrint();

        // 启三个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    System.out.println("线程A:"+Thread.currentThread().getId());
                    letterPrint.aPrint(i);

                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    System.out.println("线程B:"+Thread.currentThread().getId());
                    letterPrint.bPrint(i);

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    System.out.println("线程C:"+Thread.currentThread().getId());
                    letterPrint.cPrint(i);

                }
            }
        }).start();

    }

    // 内部类，利用锁，控制打印数量和打印顺序
    static class LetterPrint {

        String letterTurn = "A";

        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();


        void aPrint(int i) {

            lock.lock();
            try {
                while (!"A".equals(letterTurn)) {
                    try {
                        conditionA.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letterTurn);
                letterTurn = "B";
                conditionB.signal(); // 通知B线程启动
            } finally {
                lock.unlock();
            }


        }

        void bPrint(int i) {

            lock.lock();
            try {
                while (!"B".equals(letterTurn)) {
                    try {
                        conditionB.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letterTurn);
                letterTurn = "C";
                conditionC.signal();
            } finally {
                lock.unlock();
            }
        }

        void cPrint(int i) {
            lock.lock();
            try {
                while (!"C".equals(letterTurn)) {
                    try {
                        conditionC.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letterTurn);
                letterTurn = "A";
                conditionA.signal();
            } finally {
                lock.unlock();
            }

        }

    }

















    /**
     * 错误代码
     * @param args
     */
//    public static void main(String[] args) {
//        Thread thread1 = new Thread();
//        Thread thread2 = new Thread();
//        Thread thread3 = new Thread();
//        AbcThread abcThread = new AbcThread();
//        for (int i=1;i<=10;i++) {
//            abcThread.aPrint("A");
//            System.out.println();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("线程B:"+Thread.currentThread().getId());
//                    abcThread.bPrint("B");
//                }
//            }).start();
//
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("##线程C##:"+Thread.currentThread().getId());
//                    abcThread.cPrint("C");
//                }
//            }).start();
//
//        }
//
//    }
//
//    static class AbcThread implements Runnable {
//        void aPrint(String letter) {
//            System.out.print(letter);
//        }
//        void bPrint(String letter) {
//            System.out.print(letter);
//        }
//        void cPrint(String letter) {
//            System.out.print(letter);
//        }
//        @Override
//        public void run() {
//
//        }
//    }



}
