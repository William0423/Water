package language.thread.concurrency.deadlock;

import java.awt.font.TextHitInfo;
import java.util.concurrent.TimeUnit;

public class DeadLockTest {



    public static void main(String args[]) {

        // 两把锁：
//        第一个条件：至少有一个锁不能够共享
        Object lockA = new Object();
        Object lockB = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (lockA) {
                    System.out.println(name + " got lockA,  want LockB");
                    try {
//                        Thread.sleep(0);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 第二个条件：至少有一个任务它必须持有一个资源且正在等待获取一个当前被别的任务持有资源
                    synchronized (lockB) { // 第三个条件：对于已经被占用的lockB，线程A不能抢占它
                        System.out.println(name + " got lockB");
                        System.out.println(name + ": say Hello!");
                    }
                }
            }
        }, "xiancheng -A").start();

        // A等待B，B等待A：造成循环等待
        new Thread(new Runnable() {
            @Override
            public void run() {

                String name = Thread.currentThread().getName();
                synchronized (lockB) {
                    System.out.println(name + " got lockB, want LockA");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    synchronized (lockA) {
                        System.out.println(name + " got lockA");
                        System.out.println(name + ": say Hello!");
                    }
                }

            }
        }, "线程-B").start();
    }
}


class HoldThread implements Runnable {

    private String lockA;
    private String lockB;

    public HoldThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockA + "\t尝试获得：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockB + "\t尝试获得：" + lockA);
            }
        }
    }
}
