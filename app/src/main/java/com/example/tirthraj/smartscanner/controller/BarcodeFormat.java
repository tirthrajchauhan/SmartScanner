package com.example.tirthraj.smartscanner.controller;

import java.util.HashMap;

/**
 * {@link com.google.zxing.BarcodeFormat BarcodeFormat}
 */

public class BarcodeFormat {

    public static final String CODABAR = "";
    public static final String CODE_39 = "";
    public static final String CODE_128 = "";
    public static final String EAN_8 = "";

    private static final HashMap<String, com.google.zxing.BarcodeFormat> hashMap;

    static
    {
        hashMap = new HashMap<String, com.google.zxing.BarcodeFormat>();
        hashMap.put("CODE_39",com.google.zxing.BarcodeFormat.CODE_39);
        hashMap.put("CODE_93",com.google.zxing.BarcodeFormat.CODE_93);
        hashMap.put("CODE_128",com.google.zxing.BarcodeFormat.CODE_128);
        hashMap.put("EAN_8",com.google.zxing.BarcodeFormat.EAN_8);
        hashMap.put("EAN_13",com.google.zxing.BarcodeFormat.EAN_13);
    }

    public static com.google.zxing.BarcodeFormat getZxingFormat(String type){
        return hashMap.get(type);
    }
}
