package designpatterns.prodconsumer.tradition;

public class Synchronized_Demo {

    public static void main(String[] args) {
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

    static class ShareData {

        int resource = 0;

        synchronized void product() {
            try {
                while ( resource!=0 ) {
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + resource);
                resource++;
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized void consumer() {
            try {
                while ( resource == 0 ) {
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + resource);
                resource--;
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
