package com.ribbonconsumer.thirdparty.thread;

public class ThreadTest {

    private static int money = 0;

    private static boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    private static final Object monitor = new Object();

    private static void addMoney(int n) {
        money += n;
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            addMoney(1);
        }

        Thread a = new Thread(() -> {
//            synchronized (monitor) {
            for (int i = 0; i < 1000; i++) {
                addMoney(1);
            }
//            }
        });

        Thread b = new Thread(() -> {
//            synchronized (monitor) {
            for (int i = 0; i < 1000; i++) {
                addMoney(1);
//                }
            }
        });
        a.start();
        b.start();


        a.join();
        b.join();


        System.out.println(money);
    }

}
