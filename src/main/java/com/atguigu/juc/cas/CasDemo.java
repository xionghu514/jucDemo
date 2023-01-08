package com.atguigu.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/8 15:37
 * @Email: 1796235969@qq.com
 */
public class CasDemo {
    public static void main(String[] args) {
        DataOne dataOne = new DataOne();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Integer incr = dataOne.incr();
                System.out.println(incr);
            }).start();
        }
    }
}


class DataOne {
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public Integer incr() {
        return atomicInteger.incrementAndGet();
    }
}
