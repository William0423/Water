package jvm;

/**
 * 多线程环境下
 *
 */
public class JavaVMStackOOM {

    private void dontStop() {

        while (true) {

        }

    }


    public void stackLeakByThread() {
        while(true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            }).start();


        }
    }

    public static void main(String[] args) {

        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();

    }


}
