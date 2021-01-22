package com.example.spireforjava.controller.javaproxy;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

import java.lang.reflect.Proxy;

/**
 * @program: myProject
 * @description:Java动态代理
 * @author: luojiwen
 * @create: 2021-01-18 19:30
 **/
public class JavaDrudynamicProxy implements InvokeHandler {


    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();

        Developer developer = new JavaDeveloper("op");
        System.out.println(developer.getClass());
        System.out.println(Developer.class);




    }


    /**
     * @Author luojiwen
     * @Description 正确动态代理
     * @Date 19:59 2021/1/18
     * @param
     * @return
     */
    static void  test1(){
        JavaDeveloper luoluo = new JavaDeveloper("luoluo");

        Developer developer = (Developer) Proxy.newProxyInstance(luoluo.getClass().getClassLoader(),luoluo.getClass().getInterfaces(),(proxy, method, agrs)->{
            if (method.getName().equals("coding")) {
                System.out.println("luoluo is praying for the code!");
                return method.invoke(luoluo,agrs);
            }

            if (method.getName().equals("debug")) {
                System.out.println("luoluo have no bug！No need to debug!");
                return method.invoke(luoluo,agrs);
            }
            return null;
        });

        developer.coding();
        developer.debug();
    }

    /**
     * @Author luojiwen
     * @Description 错误动态代理
     *
     * invoke的对象不是luoluo，而是proxy，根据上面的说明猜猜会发生什么？
     * 是的，会不停地循环调用。因为proxy是代理类的对象，
     * 当该对象方法被调用的时候，会触发InvocationHandler，
     * 而InvocationHandler里面又调用一次proxy里面的对象，
     * 所以会不停地循环调用。并且，proxy对应的方法是没有实现的。
     * 所以是会循环的不停报错
     *
     * @Date 19:59 2021/1/18
     * @param
     * @return
     */
    static void  test2(){
        JavaDeveloper luoluo = new JavaDeveloper("luoluo");

        Developer developer = (Developer) Proxy.newProxyInstance(luoluo.getClass().getClassLoader(),luoluo.getClass().getInterfaces(),(proxy, method, agrs)->{
            if (method.getName().equals("coding")) {
                System.out.println("luoluo is praying for the code!");
                return method.invoke(proxy,agrs);
            }

            if (method.getName().equals("debug")) {
                System.out.println("luoluo have no bug！No need to debug!");
                return method.invoke(proxy,agrs);
            }
            return null;
        });

        developer.coding();
        developer.debug();
    }



    /**
     * @Author luojiwen
     * @Description 有點問題，縂的來說，還沒有完全弄明白（差不都了）
     * @Date 9:19 2021/1/19
     * @param
     * @return
     */
    void  test3(){


        Developer developer1 = (Developer) Proxy.newProxyInstance(JavaDrudynamicProxy.class.getClassLoader(),new Class[]{Developer.class},(proxy, method, agrs)->{
            if (method.getName().equals("coding")) {
                System.out.println("luoluo is praying for the code!");
                return null;
            }

            if (method.getName().equals("debug")) {
                System.out.println("luoluo have no bug！No need to debug!");
                return null;
            }
            return null;
        });
        developer1.coding();
        developer1.debug();

        Developer developer2 = (Developer) Proxy.newProxyInstance(JavaDrudynamicProxy.class.getClassLoader(),new Class[]{Developer.class},new MyInvocationHadler());
        developer2.debug();


    }

    @Override
    public OutputStream _invoke(String method, InputStream input, ResponseHandler handler) throws SystemException {
        return null;
    }
}
