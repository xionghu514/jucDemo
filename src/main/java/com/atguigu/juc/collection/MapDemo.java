package com.atguigu.juc.collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/6 12:42
 * @Email: 1796235969@qq.com
 */
public class MapDemo {
//    public static void main(String[] args) {
//        Map<Object, Object> map = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            new Thread(()-> {
//                map.put(new Object(), new Object());
//                System.out.println(map);
//            }).start();
//        }
//    }
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                map.put(new Object(), new Object());
                System.out.println(map);
            }).start();
        }
    }
}
