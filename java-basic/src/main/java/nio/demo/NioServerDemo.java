package nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhongpp
 * on 2018/3/31.
 */
public class NioServerDemo {
    public static final ThreadPoolExecutor threadPoolExectutor = new ThreadPoolExecutor(25, 50, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    private static ServerSocketChannel serverSocketChannel = null;
    private static Selector selector = null;

    public static void main(String[] args) throws Exception {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9092));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select(1000);
            Set<SelectionKey> result = selector.selectedKeys();
            Iterator iterator = result.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    selectionKey.cancel();
                    socketChannel.configureBlocking(false);
                    threadPoolExectutor.execute(() -> {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            socketChannel.read(buffer);
                            buffer.flip();
                            System.out.println("当前线程数量：" + threadPoolExectutor.getActiveCount() + ",收到数据：" + new String(buffer.array()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socketChannel.register(selector, SelectionKey.OP_READ);
                            } catch (ClosedChannelException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
            result.clear();
        }
    }
}
