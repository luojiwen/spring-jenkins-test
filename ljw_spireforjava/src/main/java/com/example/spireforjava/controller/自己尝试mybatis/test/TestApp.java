package com.example.spireforjava.controller.自己尝试mybatis.test;

import com.example.spireforjava.controller.自己尝试mybatis.app.Appconfig;
import com.example.spireforjava.controller.自己尝试mybatis.dao.CardDao;
import com.example.spireforjava.controller.自己尝试mybatis.service.CardServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 14:11
 **/
public class TestApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Appconfig.class);

        annotationConfigApplicationContext.getBean(CardServer.class);
        CardDao cardDao = (CardDao) annotationConfigApplicationContext.getBean("cardDao");
        cardDao.selectSysList("aa");
    }
}
