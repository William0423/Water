package designpatterns.reactor.demo2.two;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 此处做读写的拆分
 */
final class Handler implements Runnable {
    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(1000);
    ByteBuffer output = ByteBuffer.allocate(1000);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    Handler(Selector sel, SocketChannel c) throws IOException {
        socket = c; c.configureBlocking(false);
        // Optionally try first read now
        sk = socket.register(sel, 0);
        sk.attach(this); //将Handler作为callback对象
        sk.interestOps(SelectionKey.OP_READ); //第二步,接收Read事件
        sel.wakeup();
    }
    boolean inputIsComplete() { return true; /* ... */ }
    boolean outputIsComplete() { return true; /* ... */ }

    void process() { /* ... */ }

    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException ex) { /* ... */ }
    }

    void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            // Normally also do first write now
            sk.interestOps(SelectionKey.OP_WRITE); //第三步,接收write事件
        }
    }
    void send() throws IOException {
        socket.write(output);
        if (outputIsComplete()) sk.cancel(); //write完就结束了, 关闭select key
    }
}

//上面 的实现用Handler来同时处理Read和Write事件, 所以里面出现状态判断
//我们可以用State-Object pattern来更优雅的实现
//class Handler { // ...
//    public void run() { // initial state is reader
//        socket.read(input);
//        if (inputIsComplete()) {
//            process();
//            sk.attach(new Sender());  //状态迁移, Read后变成write, 用Sender作为新的callback对象
//            sk.interest(SelectionKey.OP_WRITE);
//            sk.selector().wakeup();
//        }
//    }
//    class Sender implements Runnable {
//        public void run(){ // ...
//            socket.write(output);
//            if (outputIsComplete()) sk.cancel();
//        }
//    }
//}
