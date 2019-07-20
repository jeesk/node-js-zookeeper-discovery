package com.shanjiancaofu.massmapleapi;


import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component

public class ServiceRegistryImpl implements ServiceRegistry, Watcher {
    private CountDownLatch cdl = new CountDownLatch(1);
    private ZooKeeper zooKeeper;
    private static final String path = "/registry";
    private static final int st = 5000;

    public ServiceRegistryImpl() {
    }

    public ServiceRegistryImpl(String zkString) {

        try {
            zooKeeper = new ZooKeeper(zkString, 5000, this);
            cdl.await();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void rigistry(String serviceName, String serviceAddress) throws KeeperException, InterruptedException {

        // 创建根节点
        if (zooKeeper.exists(path, null) == null) {
            zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        // 创建服务节点
        String servicePath = path + "/" + serviceName;
        if (zooKeeper.exists(servicePath, null) == null) {
            zooKeeper.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        // 创建地址节点
        String addrPath = servicePath + "/addr-";
        if (zooKeeper.exists(addrPath, null) == null) {
            String addNode = zooKeeper.create(addrPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }


    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            cdl.countDown();
        }
    }
}
