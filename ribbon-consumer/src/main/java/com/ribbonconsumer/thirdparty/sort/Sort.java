package com.ribbonconsumer.thirdparty.sort;


import java.util.Arrays;

/**
 * @author qian.wang
 * @description 排序算法
 * @date 2019/2/20
 */
public class Sort {

    //冒泡排序
    public static int[] orderByBubble(int[] a) {
        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        return a;
    }

    //选择排序
    public static int [] selectSort(int[] array) {
        int tmp = 0;
        int minIndex = 0;
        for (int i = 0; i < array.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[minIndex] > array[j]) {
                    tmp = array[minIndex];
                    array[minIndex] = array[j];
                    array[j] = tmp;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int a[]={1,3,9,5,7,2,5,7,8,3,2};
        long start=System.currentTimeMillis();
//        System.out.println(Arrays.toString(orderByBubble(a)));
        System.out.println(Arrays.toString(selectSort(a)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
//        System.out.println(Arrays.toString(selectSort(a)));
    }


}
