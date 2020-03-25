package language.thread.concurrency.deadlock;

public class DeadLockTest_Simple {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldThead(lockA, lockB), "threadAAA").start();
        new Thread(new HoldThead(lockB, lockA), "threadBBB").start();
    }
}



class HoldThead implements Runnable  {

    // 一组互相竞争资源 的线程因互相等待，导致“永久”阻塞的现象
    String lockA;
    String lockB;

    public HoldThead(String lock1, String lock2) {
        this.lockA = lock1;
        this.lockB = lock2;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockA + "\t尝试获得：" + lockB);

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockB + "\t尝试获得：" + lockA);

            }

        }

    }


}
