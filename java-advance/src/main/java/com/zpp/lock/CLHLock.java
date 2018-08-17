package com.zpp.lock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * CLH锁也是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，它不断轮询前驱的状态，如果发现前驱释放了锁就结束自旋。
 * https://coderbee.net/index.php/concurrent/20131115/577
 *
 * @author pingpingZhong
 * Date: 2018/3/1
 * Time: 10:28
 */

public class CLHLock {
    public static class CLHNode {
        private volatile boolean isLocked = true;// 默认是在等待锁
    }

    @SuppressWarnings("unused")
    private volatile CLHNode tail;
    private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<CLHNode>();
    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class,
            CLHNode.class, "tail");

    public void lock() {
        CLHNode node = new CLHNode();
        LOCAL.set(node);
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {//已有线程占用了锁，进入自旋
            while (preNode.isLocked) {
            }
            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();
        // 如果队列里只有当前线程，则释放对当前线程的引用（for GC）。
        if (!UPDATER.compareAndSet(this, node, null)) {
            // 还有后续线程
            node.isLocked = false;// 改变状态，让后续线程结束自旋
        }
        node = null;
    }
}