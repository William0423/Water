package io.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }

        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                System.out.println("@#######");

                /**
                 * 每次接收到一个请求，就起一个线程，处理数据，单个线程不会阻塞的
                 */
                new Thread(new TimeServerHandler(socket)).start();

//                ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
//                int noThreads = currentGroup.activeCount();
//                Thread[] lstThreads = new Thread[noThreads];
//                currentGroup.enumerate(lstThreads);
//
//                for (int i = 0; i < noThreads; i++){
//                    System.out.println("Thread No:" + i + " = " + lstThreads[i].getName());
//                }

            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
    
    
}
