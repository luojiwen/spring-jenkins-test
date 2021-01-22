package com.example.spireforjava.controller.自己尝试mybatis.app;

import com.example.spireforjava.controller.自己尝试mybatis.test.MyImportBeanDefinitionRegister;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-19 14:10
 **/
@Configuration
@ComponentScan("com.example.spireforjava.controller.自己尝试mybatis")
@Import(MyImportBeanDefinitionRegister.class)
public class Appconfig {


}
