package com.example.spireforjava.controller.refelct;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 10:52
 **/
public class Demo {
    static {
        System.out.println("Demo：静态代码块");
    }
    {
        System.out.println("Demo：动态代码块");
    }
    public Demo(){
        System.out.println("Demo：构造方法");
    }
}
