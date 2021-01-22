package com.example.spireforjava.controller.自己尝试mybatis.test;

import com.example.spireforjava.controller.自己尝试mybatis.dao.CardDao;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 14:49
 **/
public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {



        BeanDefinitionBuilder builder= BeanDefinitionBuilder.genericBeanDefinition(CardDao.class);
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();
        genericBeanDefinition.setBeanClass(MyFactoryDynamic.class);

        genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue("com.example.spireforjava.controller.自己尝试mybatis.dao.CardDao");
        registry.registerBeanDefinition("cardDao",genericBeanDefinition);
    }
}
