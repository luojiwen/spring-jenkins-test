package com.example.spireforjava.controller;

import ch.qos.logback.core.db.DriverManagerConnectionSource;
import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.DriverManager;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-18 18:32
 **/
@Configuration
public class sqlSessionTest {

    @Bean
    public SqlSessionFactoryBean  sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSource  sqlSessionFactoryBean(){
        DriverManagerDataSource driverManagerConnectionSource = new DriverManagerDataSource();
        driverManagerConnectionSource.setUser("root");
        driverManagerConnectionSource.setPassword("root");
        driverManagerConnectionSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        driverManagerConnectionSource.setDriverClass("com.mysql.jdbc.Driver");
        return driverManagerConnectionSource;
    }

    public static void main(String[] args) {
        TEST20191209 test20191209 = new TEST20191209();

        Proxy.newProxyInstance(
                test20191209.getClass().getClassLoader(),
                test20191209.getClass().getInterfaces(),
                (proxy,method,ags)->{return null;});

        Proxy.newProxyInstance(
                test20191209.getClass().getClassLoader(),
                test20191209.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
    }
}
