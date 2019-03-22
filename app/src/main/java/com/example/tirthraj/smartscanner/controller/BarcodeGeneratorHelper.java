package com.example.tirthraj.smartscanner.controller;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class BarcodeGeneratorHelper {

    private String inputString;
    private int barcodeWidth;
    private int barcodeHeight;

    /**
     * {@link com.example.tirthraj.smartscanner.controller.BarcodeFormat BarcodeFormat}
     */
    private String barcodeFormat;
    private Map<EncodeHintType,Object> hints;

    private BarcodeGeneratorHelper(Builder builder){
        inputString = builder.inputString;
        barcodeWidth = builder.width;
        barcodeHeight = builder.height;
        barcodeFormat = builder.format;

        hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hints.put(EncodeHintType.MARGIN,2);
    }

    public Bitmap generateBitmap() throws IOException {
        BitMatrix bitMatrix = generateBitMatrix();
        return bitMatrix == null ? null : BarcodeDraw.toBitmap(bitMatrix);
    }

    public BitMatrix generateBitMatrix() throws IOException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = multiFormatWriter.encode(inputString, BarcodeFormat.getZxingFormat(barcodeFormat), barcodeWidth, barcodeHeight,hints);
        } catch (WriterException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return bitMatrix;
    }

    public static class Builder{

        private static final int WIDTH_DEFAULT = 708;
        private static final int HEIGHT_DEFAULT = 354;

        private String inputString;
        private int width;
        private int height;
        private String format;

        /**
         * @param content
         * @param format {@link com.example.tirthraj.smartscanner.controller.BarcodeFormat BarcodeFormat}
         */
        public Builder(String content, String format) {
            this.inputString = content;
            this.format = format;
            this.width = WIDTH_DEFAULT;
            this.height = HEIGHT_DEFAULT;
        }

        /**
         * @param width
         * @param height
         */
        public Builder size(int width,int height){
            this.width = width;
            this.height = height;
            return Builder.this;
        }

        public BarcodeGeneratorHelper build(){
            return new BarcodeGeneratorHelper(this);
        }

    }


}
