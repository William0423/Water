package designpatterns.prodconsumer.block;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) {
        ShareData myResource = new ShareData(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            try {
                myResource.product();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            try {
                myResource.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("5s后main叫停，线程结束");
        try {
            myResource.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ShareData {
        AtomicInteger atomicInteger = new AtomicInteger();
        BlockingQueue<String> blockingQueue = null;
        private volatile boolean flag = true;

        public ShareData(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        void product() throws InterruptedException {
            String data = null;
            Boolean retValue = null;
            while (flag) {
                data = atomicInteger.incrementAndGet()+"";
                retValue = blockingQueue.offer(data,2, TimeUnit.SECONDS);
                if (retValue) {
                    System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println(Thread.currentThread().getName() + "\t大老板叫停了，flag=false，生产结束");
        }

        void consumer() throws InterruptedException {
            String retValue;

            while (flag) {
                retValue = blockingQueue.poll(2,TimeUnit.SECONDS);
                if (null == retValue || retValue.equalsIgnoreCase("")) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + "\t超过2s没有取到蛋糕，消费退出");
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t消费队列" + retValue + "成功");
            }
        }

        public void stop() throws Exception {
            flag = false;
        }

    }
}
