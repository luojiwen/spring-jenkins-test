package com.example.spireforjava.controller.自己尝试mybatis.service;

import com.example.spireforjava.controller.自己尝试mybatis.dao.CardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 14:29
 **/
@Component
public class CardServer {

    @Autowired
    CardDao cardDao;

    @PostConstruct
    public void init(){}

    public void selectSysList(){
        System.out.println(cardDao.selectSysList("123123"));
    }

}
