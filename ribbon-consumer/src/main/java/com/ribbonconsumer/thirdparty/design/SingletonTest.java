package com.ribbonconsumer.thirdparty.design;

/**
 * @author qian.wang
 * @description  单例设计模式
 * @date 2019/2/27
 */

//懒汉式-双重for循环式
public class SingletonTest {

    private volatile static SingletonTest singletonTest;

    public SingletonTest(){}

    public static SingletonTest getSingle(){
        if(singletonTest==null){
            synchronized (SingletonTest.class){
                if(singletonTest==null){
                    singletonTest=new SingletonTest();
                }
            }
        }
        return singletonTest;
    }

}
