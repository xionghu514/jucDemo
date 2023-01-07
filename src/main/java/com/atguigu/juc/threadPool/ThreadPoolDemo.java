package com.atguigu.juc.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 21:26
 * @Email: 1796235969@qq.com
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 不限制长度的线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 指定大小
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);

        // 一个线程
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        // 自定义
        ThreadPoolExecutor executorService3 = new ThreadPoolExecutor(200, 600, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 100; i++) {
                executorService3.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行了业务逻辑");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService3.shutdown();
        }
    }
}
