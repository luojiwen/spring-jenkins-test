package com.example.spireforjava.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: myProject
 * @description:
 * Java中i++,++i都表示 i = i+1
 * i++是先是使用 i的原值，然后再原值的基础上加1.
 * ++i是先在原值的基础上加1，然后在使用i的值。
 * @author: luojiwen
 * @create: 2020-12-07 14:41
 **/
public class TEST20201207 {

    public static void main(String[] args) {
//        int i = 1;
//        i = i++;
//        int j = i++;
//        int k = i + ++i * i++;
//        System.out.println("i="+i);
//        System.out.println("j="+j);
//        System.out.println("k="+k);
//        int a =1;
//        System.out.println(a++);
//        System.out.println(++a);
//=========================================================
//            String s1 = "runoob";
//            String s2 = "runoob";
//            System.out.println(s1 == s2);
//            System.out.println(s1.equals(s2));
        test1();
    }


    public static void test1(){
        String luojiwen = "opop";
        String aaa = "jkluio";
        Map map = new HashMap();
        map.put("123","123");
        map.put("123","123");
        map.put("123","123");
        System.out.printf("lwekwe %s w王鹏",luojiwen);
        System.out.printf("lwekwe %s w王鹏",luojiwen);

        String aa = "jjjjj%skkkk%s";
        String uu = String.format(aa,"hh","bb");
        System.out.println(uu);
    }
}
