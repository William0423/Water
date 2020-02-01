package designpatterns.reactor.demo3;

import javax.annotation.processing.Processor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class MultithreadingReactor {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if(selector.selectNow() < 0) {
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while(iterator.hasNext()) {

                SelectionKey key = iterator.next();

                iterator.remove();

                if (key.isAcceptable()) {

                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("Accept request from {}" + socketChannel.getRemoteAddress());
                    SelectionKey readKey = socketChannel.register(selector, SelectionKey.OP_READ);

                    readKey.attach(new Processor());

                } else if (key.isReadable()) {
                    Processor processor = (Processor) key.attachment();
                    processor.process(key);
                }
            }
        }
    }

    static class Processor {

        public void process(SelectionKey selectionKey) {
            ExecutorService service = Executors.newFixedThreadPool(16);
            service.submit(() -> {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                int count = socketChannel.read(buffer);
                if (count < 0) {
                    socketChannel.close();
                    selectionKey.cancel();

                    System.out.println(socketChannel + " Read ended");

                    return null;
                } else if(count == 0) {
                    return null;
                }
                System.out.println(socketChannel + " Read message " + new String(buffer.array()));
                return null;
            });
        }

    }


}
