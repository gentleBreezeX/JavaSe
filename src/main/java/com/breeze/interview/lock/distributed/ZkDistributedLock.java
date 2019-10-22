package com.breeze.interview.lock.distributed;


import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZkDistributedLock extends ZkAbstractTemplateLock {


    @Override
    public boolean tryZkLock() {
        try {
            zkClient.createEphemeral(path);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public void waitZkLock() {

        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };

        //开启监听
        zkClient.subscribeDataChanges(path, iZkDataListener);

        if (zkClient.exists(path)) {
            countDownLatch = new CountDownLatch(1);

            try {
                countDownLatch.await();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //关闭监听
        zkClient.unsubscribeDataChanges(path, iZkDataListener);
    }
}
