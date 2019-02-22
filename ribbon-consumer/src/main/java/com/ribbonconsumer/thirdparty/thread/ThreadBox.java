package com.ribbonconsumer.thirdparty.thread;

/**
 * @author qian.wang
 * @description 多线程并发之生产者消费者模式的两种实现
 * @date 2019/2/20
 */
/*
 生产者和消费者模式在生活当中随处可见，它描述的是协调与协作的关系。比如一个人正在准备食物（生产者），而另一个人正在吃（消费者），
 他们使用一个共用的桌子用于放置盘子和取走盘子，生产者准备食物，如果桌子上已经满了就等待，消费者（那个吃的）等待如果桌子空了的话。这里桌子就是一个共享的对象。
 我们看这样一个例子：
 生产者：  往一个公共的盒子里面放苹果
 消费者：从公共的盒子里面取苹果
 盒子：盒子的容量不能超过5
*/

public class ThreadBox {
    private int orange = 0;

    public synchronized void create() {
        while (orange==10){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orange++;
        System.out.println("橘子生成成功。。。。。。");
        notify();
    }

    public synchronized void use(){
        while (orange==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orange--;
        System.out.println("橘子消费成功。。。。。。");
        notify();
    }


    //消费者生产者测试
    public static void main(String[] args) {
        ThreadBox box= new ThreadBox();

        Consumer con= new Consumer(box);
        Producer pro= new Producer(box);

        Thread t1= new Thread(con);
        Thread t2= new Thread(pro);

        t1.start();
        t2.start();
    }


}
