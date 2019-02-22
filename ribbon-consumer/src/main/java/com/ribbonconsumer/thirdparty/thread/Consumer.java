package com.ribbonconsumer.thirdparty.thread;

/**
 * @author qian.wang
 * @description 消费者代码
 * @date 2019/2/20
 */
public class Consumer implements Runnable{

    private ThreadBox threadBox;

    public Consumer(ThreadBox threadBox){
        this.threadBox=threadBox;
    }

    @Override
    public void run() {
        for (int i=0;i<100;i++){
            System.out.println("消费者j="+i);
            try {
                Thread.sleep(1000);// 这里设置跟生产者的30不同是为了 盒子中的苹果能够增加，不会生产一个马上被消费
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadBox.use();
        }
    }
}
