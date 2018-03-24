package language.thread.concurrency.common;

public class CpuTest {

    public static void main(String args[]) {

        while (true) {

            System.out.println(Thread.currentThread().getName());
            for(int i=0; i<2000; i++){
                new Thread("" + i){
                    public void run(){
                        System.out.println("Thread: " + getName() + " running");
                    }
                }.start();
            }


        }

    }



}
