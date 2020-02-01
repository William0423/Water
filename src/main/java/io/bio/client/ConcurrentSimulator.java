package io.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentSimulator {

    /**
     * 模拟多线程并发请求
     * @param args
     */

    public static void main(String[] args) {

        final CountDownLatch begin = new CountDownLatch(1); //为0时开始执行
        final ExecutorService exec = Executors.newFixedThreadPool(9);

        for (int i = 0; i < 3; i++) {
            final int NO = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        begin.await(); //等待直到 CountDownLatch减到1
                        Socket socket = null;
                        BufferedReader in = null;
                        PrintWriter out = null;

                        try {
                            socket = new Socket("127.0.0.1", 8080);
                            in = new BufferedReader(new InputStreamReader(
                                    socket.getInputStream()));
                            out = new PrintWriter(socket.getOutputStream(), true);
                            out.println("QUERY TIME ORDER : " + NO);
                            System.out.println("Send order 2 server succeed.");
                            String resp = in.readLine();
                            System.out.println("Now is : " + resp);
                        } catch (Exception e) {
                            //不需要处理
                        } finally {
                            if (out != null) {
                                out.close();
                                out = null;
                            }
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                in = null;
                            }
                            if (socket != null) {
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                socket = null;
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.submit(runnable);
        }
        System.out.println("开始执行");
        begin.countDown(); // begin减一，开始并发执行
        exec.shutdown();
    }


}
