package com.atguigu.juc;

/**
 * @Description:
 * @Author: xionghu514
 * @Date: 2022/12/16 19:26
 * @Email: 1796235969@qq.com
 */
public class LambadTest {
    //lambad使用前提 满足1、是接口  2、有且只有一个抽象方法的叫做函数式接口 用注解@FunctionalInterface标注

    public static void main(String[] args) {
//        Test test = new Test();
//        System.out.println("test.add(1,2) = " + test.add(1, 2));
        Foo foo = (int x, int y)->  x + y;

        System.out.println(foo.add(1, 2));
    }
}

@FunctionalInterface
interface Foo {
    int add(int x, int y);

//    static int add(int x, int y, int z) {
//        return x+y+z;
//    }
//
//    default int add(int x, int y, int z, int q) {
//        return x+y+z+q;
//    }
}

//class Test implements Foo {
//
//    public int add(int x, int y) {
//        return x + y;
//    }
//}
