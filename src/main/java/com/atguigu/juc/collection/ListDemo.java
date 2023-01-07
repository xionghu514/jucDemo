package com.atguigu.juc.collection;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 12:06
 * @Email: 1796235969@qq.com
 */
public class ListDemo {

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(() -> {
//                list.add(new Object());
//                System.out.println(list);
//            });
//            thread.start();
//            thread.join();

            new Thread(() -> {
                list.add(new Object());
                System.out.println(list);

            }).start();
        }

    }
}
