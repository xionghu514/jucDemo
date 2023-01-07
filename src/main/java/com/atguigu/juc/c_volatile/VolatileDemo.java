package com.atguigu.juc.c_volatile;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/7 22:10
 * @Email: 1796235969@qq.com
 */
public class VolatileDemo {

    public static void main(String[] args) {
        Resource resource = new Resource();

//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + "获取数据");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            resource.addNum();
//            System.out.println(Thread.currentThread().getName() + "获取到数据" + resource.num);
//        }, "BBB").start();
//
//        while (resource.num == 0) {
//
//        }
//        System.out.println(Thread.currentThread().getName() + "获取到数据" + resource.num);

        // volatile不具备原子性
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println(resource.incr());
            }).start();
        }
    }
}

class Resource {
    volatile Integer num = 0;

    public Integer incr() {
        return ++num;
    }

    public void addNum() {
        this.num = 698;
    }
}