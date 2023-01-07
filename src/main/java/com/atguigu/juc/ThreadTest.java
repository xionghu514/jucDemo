package com.atguigu.juc;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/16 15:12
 * @Email: 1796235969@qq.com
 */
public class ThreadTest {
    public static void main(String[] args) {
        new MyThread().start();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("继承Thread类创建线程" + Thread.currentThread().getName());
    }
}