package com.atguigu.juc.jucUtils;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 15:00
 * @Email: 1796235969@qq.com
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "出来了");

                countDownLatch.countDown();

            }, "同学" + i).start();
        }
        countDownLatch.await();
        System.out.println("老师锁门");
    }
}
