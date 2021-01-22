package com.example.spireforjava.controller.自己尝试mybatis.anno;

import com.example.spireforjava.controller.自己尝试mybatis.test.MyImportBeanDefinitionRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(MyImportBeanDefinitionRegister.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface luoScan {

}
