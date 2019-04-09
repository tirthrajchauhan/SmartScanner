package com.example.tirthraj.smartscanner.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tirthraj.smartscanner.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GenerateQRActivity extends AppCompatActivity {

    private Button btngenerate,btnSave;
    private EditText qrtext;
    private ImageView qrcode;
    BitmapDrawable draw;
    File dir,sdCard;
     Bitmap bm;
    String textqr;
    public static final int RC_STORAGE_PERMISSION = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        btngenerate = findViewById(R.id.btnqrgenerate);
        qrtext = findViewById(R.id.qrtext);
        qrcode = findViewById(R.id.qrcode);


        btnSave = (Button)findViewById(R.id.btn_save);


        btngenerate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             textqr = qrtext.getText().toString();
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


    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int permissionCheck = ContextCompat.checkSelfPermission(GenerateQRActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED && textqr != null && !textqr.isEmpty()) {

                draw = (BitmapDrawable) qrcode.getDrawable();
                bm = draw.getBitmap();


                try {


                    FileOutputStream outStream = null;
                    sdCard = Environment.getExternalStorageDirectory();
                     dir = new File(sdCard.getAbsolutePath() + "/qrCode");
                    dir.mkdirs();
                    String fileName = String.format("%s.jpg", textqr);
                    File outFile = new File(dir, fileName);
                    outStream = new FileOutputStream(outFile);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(dir));
                    sendBroadcast(intent);

//           savePicture(bm, "image_name.jpg");
                    Toast.makeText(getApplicationContext(), "Image saved...", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException ex) {
                    Log.d("exception", "" + ex);
                } catch (IOException ex) {
                    Log.d("exception", "" + ex);

                }
//            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "one" ,"qrcode");
            }

            else {
                //Request camera permission from system
                ActivityCompat.requestPermissions(GenerateQRActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        RC_STORAGE_PERMISSION);
            }


        }
    });




}

//    private void savePicture( Bitmap bm, String imgName)
//    {
//        OutputStream fOut = null;
//        String strDirectory = Environment.getExternalStorageDirectory().toString();
//
//        File f = new File(strDirectory, imgName);
//        try {
//            fOut = new FileOutputStream(f);
//
//            /**Compress image**/
//            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
//            fOut.flush();
//            fOut.close();
//
//            /**Update image to gallery**/
//            MediaStore.Images.Media.insertImage(getContentResolver(),
//                    f.getAbsolutePath(), f.getName(), f.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
