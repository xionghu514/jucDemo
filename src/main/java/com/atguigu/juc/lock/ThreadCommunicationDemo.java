package com.atguigu.juc.lock;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/17 13:27
 * @Email: 1796235969@qq.com
 */
public class ThreadCommunicationDemo {

    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    resource.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"加1线程").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    resource.decr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "减1线程").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    resource.decr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "减1线程....").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    resource.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"加1线程....").start();
    }
}

class Resource {
    private int num = 0;
//
//    Lock lock = new ReentrantLock(); // 初始化lock锁
//    Condition incrCondition = lock.newCondition(); // 初始化condition对象
//    Condition decrCondition = lock.newCondition(); // 初始化condition对象

    public synchronized void incr() throws InterruptedException {
        // 当num不等于0时，进行睡眠
        if (num != 0) {
            this.wait();
        }
        // 代码执行到这，证明num==0， 进行操作
        num ++ ;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        // 通知睡眠的线程运行
        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        if (num != 1) {
            this.wait();
        }
        // 运行到这证明num==1
        // 工作
        num --;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        // 通知睡眠的线程运行
        this.notifyAll();
    }

//    public void decr() {
//        lock.lock();
//        try {
//            while (num != 1) {
//                try {
//                    decrCondition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            // 运行到这证明num==1
//            // 工作
//            num --;
//            System.out.println(Thread.currentThread().getName() + "\t" + num);
//            // 通知睡眠的线程运行
//            incrCondition.signal();
//        } finally {
//            lock.unlock();
//        }
//
//    }
//
//    public void incr() {
//        lock.lock();
//        try {
//            // 当num不等于0时，进行睡眠
//            while (num != 0) {
//                try {
//                    incrCondition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            // 代码执行到这，证明num==0， 进行操作
//            num ++ ;
//            System.out.println(Thread.currentThread().getName() + "\t" + num);
//            // 通知睡眠的线程运行
//            decrCondition.signal();
//        } finally {
//            lock.unlock();
//        }
//    }
}
