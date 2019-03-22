package com.example.tirthraj.smartscanner.controller;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tirthraj.smartscanner.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRActivity extends AppCompatActivity {

    private Button btngenerate;
    private EditText qrtext;
    private ImageView qrcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        btngenerate = findViewById(R.id.btnqrgenerate);
        qrtext = findViewById(R.id.qrtext);
        qrcode = findViewById(R.id.qrcode);

    btngenerate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String textqr = qrtext.getText().toString();
            if(textqr != null && !textqr.isEmpty()){
               try {
                   MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                   BitMatrix bitMatrix = multiFormatWriter.encode(textqr, BarcodeFormat.QR_CODE, 700, 700);
                   BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                   Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                   qrcode.setImageBitmap(bitmap);
               } catch (WriterException e){
                   e.printStackTrace();
               }
            }
        }
    });

    }

}
