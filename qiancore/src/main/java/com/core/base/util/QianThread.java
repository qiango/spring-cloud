package com.core.base.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 双重检查加锁实现单例模式懒加载
 * volatile:
 * 1. 保证可见性：对变量的修改会立刻刷新主存，并且对该变量的读取都会到主存中读取最新的值
 * 2. 防止指令重排
 * 当前创建对象的过程是
 * 1. 栈内存开启空间给对象引用
 * 2. 堆内存分配对象地址，并准备初始化对象
 * 3. 初始化对象
 * 4. 栈内存引用指向堆内存中对象地址
 * 优化之后可能会变成 1,2,4,3。此时栈空间已经引用了堆空间的地址，但是此时对象还没初始化，那么对象的使用可能就会抛出
 * nullPointException.
 */

/**
 * @Authod: wangqian
 * @Date: 2020-07-09  17:35
 */
public class QianThread {

    private volatile static ExecutorService executorService;

    private QianThread() {
    } //私有化构造器

    public static ExecutorService getIntance() {
        if (null == executorService) {
            synchronized (QianThread.class) {
                if (null == executorService) {
                    executorService = Executors.newCachedThreadPool();
                }
            }
        }
        return executorService;
    }
}
