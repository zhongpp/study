package com.zpp.lock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * MCS Spinlock 是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，
 * 直接前驱负责通知其结束自旋，从而极大地减少了不必要的处理器缓存同步的次数，降低了总线和内存的开销。
 *
 * @author pingpingZhong
 * Date: 2018/3/1
 * Time: 10:29
 */
public class MCSLock {
    public static class MCSNode {
        volatile MCSNode next;
        volatile boolean isLocked = true;// 默认是在等待锁
    }

    private static final ThreadLocal<MCSNode> NODE = new ThreadLocal<MCSNode>();
    private volatile MCSNode queue;// 指向最后一个申请锁的MCSNode
    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(MCSLock.class,
            MCSNode.class, "queue");

    public void lock() {
        MCSNode currentNode = new MCSNode();
        NODE.set(currentNode);
        MCSNode preNode = UPDATER.getAndSet(this, currentNode);//step1
        System.out.print("当前线程：" + Thread.currentThread() + " 前驱节点：" + preNode);
        if (preNode != null) {
            preNode.next = currentNode;//step2
            while (currentNode.isLocked) {//step3

            }
        } else {// 只有一个线程在使用锁，没有前驱来通知它，所以得自己标记自己为非阻塞
            currentNode.isLocked = false;
        }
    }

    public void unlock() {
        System.out.println();
        MCSNode currentNode = NODE.get();
        if (currentNode.next == null) {// 检查是否有人排在自己后面
            if (UPDATER.compareAndSet(this, currentNode, null)) {//step4
                // compareAndSet返回true表示确实没有人排在自己后面
                return;
            } else {
                // 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
                // 这里之所以要忙等是因为：step 1执行完后，step 2可能还没执行完
                while (currentNode.next == null) {
                }
            }
        } else {
            currentNode.next.isLocked = false;
            currentNode.next = null; //gc
        }
    }
}