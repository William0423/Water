package designpatterns.reactor.demo2.one;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8080);
            while (!Thread.interrupted())
                new Thread(new Handler(ss.accept())).start(); //创建新线程来handle
            // or, single-threaded, or a thread pool
        } catch (IOException ex) { /* ... */ }
    }

    static class Handler implements Runnable {
        final Socket socket;
        Handler(Socket s) { socket = s; }
        public void run() {
            try {
                byte[] input = new byte[10000];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException ex) { /* ... */ }
        }
        private byte[] process(byte[] cmd) { return cmd;/* ... */ }
    }

}