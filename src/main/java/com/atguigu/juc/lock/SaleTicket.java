package com.atguigu.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/16 20:49
 * @Email: 1796235969@qq.com
 */
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.saleTicket();
            }
        }, "aaa").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.saleTicket();
            }
        }, "bbb").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.saleTicket();
            }
        }, "ccc").start();
    }
}

class Ticket {
    private int num = 30;

    public void saleTicket() {

        if (num <= 0) {
            System.out.println("票已售罄");
            return;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "正在售卖第" + num-- + "号票" + "，还剩下" + num + "张票");
    }
}

class Ticket1 {
    private int num = 30;

    ReentrantLock lock = new ReentrantLock(true);
    public void saleTicket() {
        lock.lock();
        try {
            if (num <= 0) {
                System.out.println("票已售罄");
//                Thread.currentThread().stop();
                return;
            }
            check();
            System.out.println(Thread.currentThread().getName() + "正在售卖第" + num-- + "号票" + "，还剩下" + num + "张票");
        }finally {
            lock.unlock();
        }
    }

    public void check() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "检测余票");
        lock.unlock();
    }
}