package language.thread.lock.juc;

public class ReentrantLockAble {


    boolean isLocked = true;
    Thread lockedBy = null;
    int lockCount = 0;

    public synchronized void lock() throws InterruptedException {

        Thread  thread = Thread.currentThread();

        while (isLocked && lockedBy != thread) {
            wait();
        }

        lockedBy = thread;
        isLocked = true;
        ++lockCount;
    }


    public synchronized void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == lockedBy) {
            --lockCount;
            if (lockCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }


}
