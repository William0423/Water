package designpatterns.reactor.demo2.three;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


class Reactor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocket;

    Reactor(int port) throws IOException { //Reactor初始化
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false); //非阻塞
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT); //分步处理,第一步,接收accept事件

        sk.attach(new Acceptor()); //attach callback object, Acceptor
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext())
                    dispatch((SelectionKey)(it.next())); //Reactor负责dispatch收到的事件
                selected.clear();
            }
        } catch (IOException ex) { /* ... */ }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment()); //调用之前注册的callback对象
        if (r != null)
            r.run();
    }

    class Acceptor implements Runnable { // inner
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();

                if (c != null) {
//                    new Handler(selector, c);
                }

            }
            catch(IOException ex) { /* ... */ }
        }
    }

}


//class Handler implements Runnable {
//    // uses util.concurrent thread pool
//    static PooledExecutor pool = new PooledExecutor(...);
//    static final int PROCESSING = 3;
//    // ...
//    synchronized void read() { // ...
//        socket.read(input);
//        if (inputIsComplete()) {
//            state = PROCESSING;
//            pool.execute(new Processer()); //使用线程pool异步执行
//        }
//    }
//
//    synchronized void processAndHandOff() {
//        process();
//        state = SENDING; // or rebind attachment
//        sk.interest(SelectionKey.OP_WRITE); //process完,开始等待write事件
//    }
//
//    class Processer implements Runnable {
//        public void run() { processAndHandOff(); }
//    }
//}