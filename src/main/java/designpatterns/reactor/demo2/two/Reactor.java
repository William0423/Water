package designpatterns.reactor.demo2.two;

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

    /**
     * 初始化，同时绑定一个Acceptor线程
     */
    Reactor(int port) throws IOException { //Reactor初始化
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false); //非阻塞
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT); //分步处理,第一步,接收accept事件

        /**
         * 利用SelectionKey.attach()方法，把一个selectionKey和一个对象“绑定”起来。
         */
        sk.attach(new Acceptor()); //attach callback object, Acceptor


    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();

                Iterator it = selected.iterator();

                while (it.hasNext()) {
                    /**
                     * 来一个事件 第一次触发一个accepter线程，以后触发SocketReadHandler
                     */
                    dispatch((SelectionKey)(it.next())); //Reactor负责dispatch收到的事件
                }

                selected.clear();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment()); //调用之前注册的callback对象
        if (r != null) {
            System.out.println("  not null");
            r.run();
        } else {
            System.out.println("  null");
        }

    }

    class Acceptor implements Runnable { // inner
        public void run() {
            try {
                System.out.println("#### hahah");
                SocketChannel c = serverSocket.accept();

                if (c != null) {
                    new Handler(selector, c);
                }

            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Thread r = new Thread(new Reactor(8080));
        r.start();
        r.join();

    }

}

