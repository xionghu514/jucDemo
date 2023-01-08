package com.atguigu.juc.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2023/1/8 18:03
 * @Email: 1796235969@qq.com
 */
public class MyUnsafeDemoUtils {
    private int num = 0;

    public static void main(String[] args) {
        MyUnsafeDemoUtils myUnsafeDemoUtils = new MyUnsafeDemoUtils();
        System.out.println("修改之前: num = " + myUnsafeDemoUtils.num);
        myUnsafeDemoUtils.myCasForInt(0, 100);
        System.out.println("修改之后: num = " + myUnsafeDemoUtils.num);
    }

    public void myCasForInt(int oldV, int newV) {
        try {
            // 通过反射获取unsafe对象
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);

            Unsafe unsafe = (Unsafe) field.get(null);
            // 获取偏移量
            long offset = unsafe.objectFieldOffset(MyUnsafeDemoUtils.class.getDeclaredField("num"));
            // 比较并交换
            unsafe.compareAndSwapInt(this, offset, oldV, newV);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
