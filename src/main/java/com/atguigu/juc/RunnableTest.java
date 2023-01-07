package com.atguigu.juc;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/16 16:26
 * @Email: 1796235969@qq.com
 */
public class RunnableTest{
    public static void main(String[] args) {
//        MyRunnable myRunnable = new MyRunnable();
//        new Thread(myRunnable).start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println("采用匿名内部类");
            }
        }).start();
    }
}

class MyRunnable implements Runnable {

    public void run() {
        System.out.println("MyRunnable");
    }
}
