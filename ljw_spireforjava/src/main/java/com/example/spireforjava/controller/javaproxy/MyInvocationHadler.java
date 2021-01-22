package com.example.spireforjava.controller.javaproxy;

import org.mybatis.spring.annotation.MapperScan;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 09:55
 **/
@MapperScan
public class MyInvocationHadler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("bbbbbbbbbbbbbbbbbbb");
        return null;
    }
}
