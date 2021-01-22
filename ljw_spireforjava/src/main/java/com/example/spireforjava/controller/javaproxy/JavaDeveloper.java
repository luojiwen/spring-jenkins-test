package com.example.spireforjava.controller.javaproxy;

/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2021-01-18 19:28
 **/
public class JavaDeveloper implements Developer{

    String name;


    public JavaDeveloper(String name) {
        this.name = name;
    }

    @Override
    public void coding() {
        System.out.println(name + " is coding java");
    }

    @Override
    public void debug() {
        System.out.println(name + " is debug java");
    }
}
