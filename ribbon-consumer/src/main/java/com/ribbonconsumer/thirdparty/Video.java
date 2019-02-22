package com.ribbonconsumer.thirdparty;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;

/**
 * @author qian.wang
 * @description  调用摄像头示例
 * @date 2019/2/13
 */
public class Video {

    public static void main(String[] args){
        Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);

        panel.setFPSDisplayed(true);

        panel.setDisplayDebugInfo(true);

        panel.setImageSizeDisplayed(true);

        panel.setMirrored(true);

        JFrame window = new JFrame("Test webcam panel");

        window.add(panel);

        window.setResizable(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.pack();

        window.setVisible(true);

    }

}
