package language.thread.problems.problem0001;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用BlockingQueue中的put和take完成互斥
 */
public class AnswerThree {

    public static void main(String[] args) {

        Business business = new Business();

        for (int i=1;i<=50;i++) {
            final int k = i;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    business.child(k);
                }
            }).start();


            business.parent(i);
        }

    }


    static class Business {

        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);

        {
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        public void child(int i) {

            try {
                queue1.put(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int j = 1; j <= 10; j++) {
                System.out.println("sub run " + j + " in round " + i);
            }
            try {
                queue2.take();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void parent(int i) {
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int j = 1; j <= 100; j++) {
                System.out.println("main run " + j + " in round " + i);
            }
            try {
                queue1.take();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
