package com.shanjiancaofu.massmapleapi;

import org.apache.zookeeper.KeeperException;

public interface ServiceRegistry {
    void rigistry(String serviceName, String serviceAddress) throws KeeperException, InterruptedException;
}
