package com.ribbonconsumer.thirdparty.design.factory;

/**
 * @author qian.wang
 * @description
 * @date 2019/2/27
 */
public class AoDiFactory {

    public static Car createAodi(){
        return new AoDi();
    }


    static class BaoMaFactory{

        private static Car createBaoMa(){
            return new BaoMa();
        }

    }

    //
    public static void main(String[] args) {
        Car aodi = AoDiFactory.createAodi();
        Car baoMa = BaoMaFactory.createBaoMa();
        aodi.run();
        baoMa.run();
    }

}
