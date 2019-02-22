package com.ribbonconsumer.thirdparty.thread;

/**
 * @author qian.wang
 * @description 生产者代码
 * @date 2019/2/20
 */
public class Producer implements Runnable {

    private ThreadBox threadBox;

    public Producer(ThreadBox threadBox){
        this.threadBox=threadBox;
    }

    @Override
    public void run() {

        for (int i=0;i<100;i++){
            System.out.println("i="+i);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadBox.create();
        }
    }
}
