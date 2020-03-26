package designpatterns.prodconsumer.tradition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsumer_TraditionDemo {

    public static void  main(String[] args) {
        ShareData shareData = new ShareData();

        for (int i=0;i<5;i++) {
            new Thread(() -> {
                shareData.product();
            }, "Product_"+i).start();
        }

        for (int i=0;i<5;i++) {
            new Thread(() -> {
                shareData.consumer();
            }, "Consumer_" + i).start();
        }
    }

    public static class ShareData {

        ReentrantLock lock = new ReentrantLock();
        Condition prodCondition = lock.newCondition();
        Condition consumerCondition = lock.newCondition();


        int resouce = 0;

        void product() {
            try {
                lock.lock();
                while (resouce != 0) {
                    prodCondition.await();
                }
                // 生产
                System.out.println(Thread.currentThread().getName() + "\t" + resouce);

                resouce++;
                consumerCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void consumer() {
            try {
                lock.lock();
                while (resouce == 0) {
                    consumerCondition.await();
                }
                // 消费：
                System.out.println(Thread.currentThread().getName() + "\t" + resouce);

                resouce--;

                prodCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

}

