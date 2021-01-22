package com.example.spireforjava.controller.自己尝试mybatis.test;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: myProject
 * @description: 升级 · 动态版
 * @author: luojiwen
 * @create: 2021-01-19 15:37
 **/
public class MyFactoryDynamic implements FactoryBean, InvocationHandler {

    Class clazz;

    public MyFactoryDynamic(Class clazz){
        this.clazz =clazz;
    }

    @Override
    public Object getObject(){
        Object proxy =  Proxy.newProxyInstance(this.getClass().getClassLoader(),new  Class[]{clazz},this);

        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy");
        Method md1 = proxy.getClass().getInterfaces()[0].getMethod(method.getName(),String.class);
        Select select = md1.getDeclaredAnnotation(Select.class);
        System.out.println(select.value()[0]);
        return null;
    }
}
