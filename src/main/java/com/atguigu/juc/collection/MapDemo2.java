package com.atguigu.juc.collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 12:43
 * @Email: 1796235969@qq.com
 */
public class MapDemo2 {
    public static void main(String[] args) throws InterruptedException {
//        Map map = new HashMap<>();
        ConcurrentHashMap map = new ConcurrentHashMap<>();
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                map.put("a", new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                map.put("1", new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map);
    }
}
