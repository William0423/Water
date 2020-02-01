package designpatterns.reactor.demo3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiReactor {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.bind(new InetSocketAddress(1234));

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int coreNum = Runtime.getRuntime().availableProcessors();

        Processor[] processors = new Processor[coreNum];

        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor();
        }

        int index = 0;

        while (selector.select() > 0) {

            Set<SelectionKey> keys = selector.selectedKeys();

            for (SelectionKey key : keys) {

                keys.remove(key);

                if (key.isAcceptable()) {

                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();

                    SocketChannel socketChannel = acceptServerSocketChannel.accept();

                    socketChannel.configureBlocking(false);

                    System.out.println("Accept request from {}" +  socketChannel.getRemoteAddress());
                    Processor processor = processors[(int) ((index++) % coreNum)];

                    processor.addChannel(socketChannel);

                    processor.wakeup();

                }
            }
        }
    }


    static class Processor {

        ExecutorService service = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

        private Selector selector;

        public Processor() throws IOException {
            this.selector = SelectorProvider.provider().openSelector();
            start();
        }

        public void addChannel(SocketChannel socketChannel) throws ClosedChannelException {
            socketChannel.register(this.selector, SelectionKey.OP_READ);
        }
        public void wakeup() {
            this.selector.wakeup();
        }

        public void start() {
            service.submit(() -> {
                while (true) {
                    if (selector.select(500) <= 0) {
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            int count = socketChannel.read(buffer);
                            if (count < 0) {
                                socketChannel.close();
                                key.cancel();
                                System.out.println(socketChannel + "{}\t Read ended");
                                continue;
                            } else if (count == 0) {
                                System.out.println("{}\t Message size is 0" + socketChannel);
                                continue;
                            } else {
                                System.out.println(socketChannel + "{}\t Read message {}"  + new String(buffer.array()));
                            }
                        }
                    }
                }
            });
        }



    }


}
