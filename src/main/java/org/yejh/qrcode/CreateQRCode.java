package org.yejh.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CreateQRCode {
    public static void main(String[] args) throws Exception {
        int width = 300;
        int height = 300;
        String format = "png";
        String content = "https://github.com";

        // 定义二维码的参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        // 生成二维码
        System.out.println("开始生成二维码。。。");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        Path path = new File("/github.png").toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
        System.out.println("结束生成二维码。。。");
    }
}
