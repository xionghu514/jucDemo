package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/23 9:54
 * @Email: 1796235969@qq.com
 */
public class TaskTest {
    public static void main(String[] args) {
        FutureTask futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();


        try {
            System.out.println("返回结果集"  + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable {

    @Override
    public String call() throws Exception {
        System.out.println("fewrsr3434");
        return "hello word";
    }
}
