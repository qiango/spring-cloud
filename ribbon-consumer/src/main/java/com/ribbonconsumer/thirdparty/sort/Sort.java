package com.ribbonconsumer.thirdparty.sort;

/**
 * @author qian.wang
 * @description 排序算法
 * @date 2019/2/20
 */
public class Sort {

    //冒泡排序
    public static int[] orderByBubble(int[] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a.length-i-1;j++){
                if(a[j]>a[j+1]){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        return a;
    }


}
