package com.example.spireforjava;

import com.example.spireforjava.config.WebAppConfig;
import javafx.util.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpireforjavaApplication {

    public static void main(String[] args) {

//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpireforjavaApplication.class);
//        AnnotationConfigServletWebServerApplicationContext annotationConfigServletWebServerApplicationContext = new AnnotationConfigServletWebServerApplicationContext(SpireforjavaApplication.class);
//        AnnotationConfigReactiveWebServerApplicationContext annotationConfigReactiveWebServerApplicationContext = new AnnotationConfigReactiveWebServerApplicationContext(SpireforjavaApplication.class);

//        System.out.println(11);
//        ConfigurableApplicationContext configurableApplicationContext =  SpringApplication.run(SpireforjavaApplication.class, args);
//        WebAppConfig webAppConfig = configurableApplicationContext.getBean(WebAppConfig.class);
//        configurableApplicationContext.refresh();
//        webAppConfig.LoginInterceptor();


//        List<Pair<String, Double>> pairArrayList = new ArrayList<>(3);
//        pairArrayList.add(new Pair<>("version", 6.19));
//        pairArrayList.add(new Pair<>("version", 10.24));
//        pairArrayList.add(new Pair<>("version", 13.14));
//        Map<String, Double> map = pairArrayList.stream().collect(
//                // 生成的 map 集合中只有一个键值对：{version=13.14}
//                Collectors.toMap(Pair::getKey, Pair::getValue));
//        System.out.println();

//        String[] departments = new String[] {"iERP", "iERP", "EIBU"};
//// 抛出 IllegalStateException 异常
//        Map<Integer, String> map1 = Arrays.stream(departments)
//                .collect(Collectors.toMap(String::hashCode, str -> str));
    }

}
