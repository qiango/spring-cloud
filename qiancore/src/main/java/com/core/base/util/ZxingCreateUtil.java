package com.core.base.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ZxingCreateUtil {


    //生成二维码
    public static String createEncode(String code) {
        int width = 360; // 图像宽度
        int height = 360; // 图像高度
        String format = "png";// 图像类型
        String key = "encode" + UnixUtil.getCustomRandomString() + ".png";
        String fileName = FileUtil.setFileName(FileUtil.FILE_LONG_PATH, key);
        String filePath = "本地文件路径" + fileName;
        if (FileUtil.validateFile(filePath)) {
            FileUtil.delFile(filePath);
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        InputStream in = null;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code,
                    BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
            in = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QiniuHttpUtil.getInstance().uploadFile("", key, in);
        return key;
    }


    //生成条形码
    public static String createBarcode(String code) {
        int width = 720; // 图像宽度
        int height = 156; // 图像高度
        String format = "png";// 图像类型
        String key = "barcode" + UnixUtil.getCustomRandomString() + ".png";
        String fileName = FileUtil.setFileName(FileUtil.FILE_LONG_PATH, key);
        String filePath = "本地路径" + fileName;
        if (FileUtil.validateFile(filePath)) {
            FileUtil.delFile(filePath);
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        MultiFormatWriter writer = new MultiFormatWriter();
        InputStream in = null;
        try {
            BitMatrix bitMatrix = writer.encode(code, BarcodeFormat.CODE_128, width, height, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
            in = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QiniuHttpUtil.getInstance().uploadFile("", key, in);
        return key;
    }

    public static void main(String[] args) {
        createBarcode("12345");
    }

}
