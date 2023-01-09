package com.atguigu.juc.aqs;

import sun.awt.Mutex;

import java.util.concurrent.CountDownLatch;

public class AqsDemo {

    public static void main(String[] args) throws InterruptedException {

        DataThree dataThree = new DataThree();
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                dataThree.incr();
                countDownLatch.countDown();
            }, "aaa").start();
        }

        countDownLatch.await();
        System.out.println(dataThree.getNum());
    }
}

class DataThree {

    private volatile int num;
    Mutex mutex = new Mutex();
    public void incr(){
        mutex.lock();
        for (int i = 0; i < 1000; i++) {
            num++;
        }
        mutex.unlock();
    }
    public int getNum(){
        return num;
    }
}