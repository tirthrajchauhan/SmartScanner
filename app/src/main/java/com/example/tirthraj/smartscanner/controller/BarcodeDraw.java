package com.example.tirthraj.smartscanner.controller;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.common.BitMatrix;


public class BarcodeDraw {

    private static int color = Color.BLACK;
    private static int backColor = Color.WHITE;
    private BarcodeDraw() {}

    /**
     * @param bitMatrix
     * @return
     */
    public static Bitmap toBitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] pixels = new int[width * height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y * width + x] = bitMatrix.get(x, y) ? color : backColor;
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }
}
