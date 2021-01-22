package com.example.spireforjava.controller.自己尝试mybatis.test;

import com.example.spireforjava.controller.自己尝试mybatis.dao.CardDao;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @program: myProject
 * @description:原版，固定版
 * @author: luojiwen
 * @create: 2021-01-19 15:09
 **/
public class MyFactoryBean implements FactoryBean {


    @Override
    public Object getObject() throws Exception {
       CardDao cardDao = (CardDao) Proxy.newProxyInstance(this.getClass().getClassLoader(),new  Class[]{CardDao.class},(proxy, method, args1)->{
            System.out.println("proxy");
            return null;});
        return cardDao;
    }

    @Override
    public Class<?> getObjectType() {
        return CardDao.class;
    }
}
