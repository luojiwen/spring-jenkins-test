package com.example.spireforjava.controller.refelct;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 10:52
 **/
public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
//        Demo demo = new Demo();
        Class<?> clz1 = Demo.class;
        System.out.println("===================================");
        Class<?> clz2 = Class.forName("com.example.spireforjava.controller.refelct.Demo");
        System.out.println("===================================");
        Class<?> clz3 = new Demo().getClass();
        System.out.println("===================================");
    }
}
