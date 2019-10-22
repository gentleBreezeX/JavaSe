package com.breeze.interview.lock.distributed;

public interface ZkLock {

    void lock();
    void unlock();

}
