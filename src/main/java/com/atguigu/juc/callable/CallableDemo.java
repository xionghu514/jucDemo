package com.atguigu.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 16:13
 * @Email: 1796235969@qq.com
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask(myCallable);
        new Thread(futureTask).start();
        String s = futureTask.get();
        System.out.println(s);
    }
}

class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("sjaldfjsad");
        return "1324";
    }
}
