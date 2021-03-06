package language.thread.concurrency.notify;

public class Blocker {

    synchronized void waitingCall() {
        try {
            while(!Thread.interrupted()) {
                wait();
                System.out.print(Thread.currentThread() + " ");
            }
        } catch(InterruptedException e) {
            // OK to exit this way
        }
    }
    synchronized void prod() { notify(); }
    synchronized void prodAll() { notifyAll(); }

}
