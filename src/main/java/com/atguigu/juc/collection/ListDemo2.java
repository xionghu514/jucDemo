package com.atguigu.juc.collection;

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 12:10
 * @Email: 1796235969@qq.com
 */
public class ListDemo2 {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("d");
        ListIterator<String> iterator = list.listIterator();

        while (iterator.hasNext()) {

            while (iterator.next().equals("b")) {
                list.add("c");
            }
        }
        System.out.println(list);
    }
}
