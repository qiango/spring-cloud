package com.cloud.ribbon.rabbitmq;

import java.util.LinkedList;
import java.util.List;

/**
 * @Authod: wangqian    阻塞队列的实现
 * 阻塞队列与普通队列的区别在于，当队列是空的时，从队列中获取元素的操作将会被阻塞，或者当队列是满时，往队列里添加元素的操作会被阻塞。
 * 试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程往空的队列插入新的元素。同样，
 * 试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其他的线程使队列重新变得空闲起来，
 * 如从队列中移除一个或者多个元素，或者完全清空队列。
 * @Date: 2020-11-06  11:05
 */
public class TestQueue {


    private int limit = 10;
    public List listQueue = new LinkedList();


    //添加的时候满长度即等待，删除的时候等于0即等待
    public void addQueue(Object item) throws InterruptedException {
        if (listQueue.size() == limit) {
            wait();
        }
        if (listQueue.size() == 0) {
            notifyAll();
        }
        listQueue.add(item);
    }

    public void removeQueue() throws InterruptedException {
        if (listQueue.size() == 0) {
            wait();
        }
        if (listQueue.size() == limit) {
            notifyAll();
        }
        listQueue.remove(0);
    }


}
