package com.atguigu.juc.jucUtils;

import java.util.concurrent.Semaphore;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 15:42
 * @Email: 1796235969@qq.com
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 3个车位
//        Semaphore semaphore = new Semaphore(3, true);  fair: 设置是否是公平锁，默认false
        Semaphore semaphore = new Semaphore(3);

        // 6个车子
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到了车位");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "离开了车位");
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
