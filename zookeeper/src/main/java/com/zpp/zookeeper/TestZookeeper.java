package com.zpp.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author pingpingZhong
 * Date: 2018/2/2
 * Time: 11:40
 */
public class TestZookeeper {
    public static void main(String[] args) throws IOException, InterruptedException,
            KeeperException {
        //创建ZooKeeper实例
        ZooKeeper zk = new ZooKeeper("192.168.20.17:2181", 100, event -> {
            // 监控所有被触发的事件
            System.out.println("已经触发了" + event.getType() + "事件！");
        });

        String path = "/db2";

        //创建一个节点,模式是PERSISTENT
        zk.create(path, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建节点" + path + ",数据为：" + new String(zk.getData(path, null, null)));
        //修改节点数据
        zk.setData(path, "2".getBytes(), -1);
        System.out.println("修改节点" + path + ",数据为：" + new String(zk.getData(path, null, null)));
        //删除一个节点
        System.out.println(zk.exists(path, null));
        //zk.delete(path, -1);
        //节点是否存在
        System.out.println(zk.exists(path, null));

    }
}
