package com.atguigu.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/5 17:01
 * @Email: 1796235969@qq.com
 */
public class ConditionDemo {
    public static void main(String[] args) {

        ShareDemo shareDemo = new ShareDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                shareDemo.print5();
            }).start();
            new Thread(() -> {
                shareDemo.print10();
            }).start();
            new Thread(() -> {
                shareDemo.print15();
            }).start();
        }

    }
}

class ShareDemo {
    private int flag = 1;

    Lock lock = new ReentrantLock();

    Condition a = lock.newCondition();
    Condition b = lock.newCondition();
    Condition c = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (flag > 5) {
                a.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("AA");
                flag ++;
            }

            b.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (flag >= 10 || flag <= 5) {
                b.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("BB");
                flag ++;
            }
            c.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (flag <= 10 || flag >=25) {
                c.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("CC");
                flag ++;
            }
            flag = 1;

            a.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
