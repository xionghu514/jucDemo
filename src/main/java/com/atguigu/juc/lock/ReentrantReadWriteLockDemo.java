package com.atguigu.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/16 23:39
 * @Email: 1796235969@qq.com
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }

    }
}

class MyCache {
    private Map<String, String> cache = new HashMap<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() +"开始写入");
            Thread.sleep(2000);
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key) {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() +"开始读取");
            Thread.sleep(2000);

            System.out.println(Thread.currentThread().getName() + "读取成功" + cache.get(key));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }
}
