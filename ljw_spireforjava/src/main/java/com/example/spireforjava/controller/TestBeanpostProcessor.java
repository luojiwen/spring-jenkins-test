package com.example.spireforjava.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;

/**
 * @program: myProject
 * @description: 干預spring bean的初始化和加載
 * BeanPostProcessor:干預spring bean的初始化前和后
 * BeanFactoryPostProcessor:
 * @author: luojiwen
 * @create: 2021-01-15 17:07
 **/
public class TestBeanpostProcessor implements BeanPostProcessor, BeanFactoryPostProcessor, PriorityOrdered {
    /**
     * 在spring bean初始化值之前執行
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在spring bean初始化值之前執行
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }



    /**
     * @Author luojiwen
     * @Description 返回出去的值越大，加载的顺序越高，优先加载的顺序越高
     * @Date 17:22 2021/1/15
     * @param
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
