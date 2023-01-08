package com.atguigu.juc.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/8 16:23
 * @Email: 1796235969@qq.com
 */
public class UnsafeDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        System.out.println(unsafe);

        // 获取偏移量
        User user = new User();
        System.out.println(user);
        long offset = unsafe.objectFieldOffset(User.class.getDeclaredField("name"));

        System.out.println("偏移量: " + offset);
        // 第一个参数: 要修改属性所属的类， 第二个参数: 要修改属性的偏移量， 第三个参数: 旧值  第四个参数: 新值
        unsafe.compareAndSwapObject(user, offset, "张三", "李四");
        System.out.println(user);

    }
}


class User {
    private String name = "张三";
    private Integer age = 18;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}