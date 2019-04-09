package com.example.tirthraj.smartscanner.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tirthraj.smartscanner.R;

import com.example.tirthraj.smartscanner.controller.BarcodeFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateBarcodeActivity extends AppCompatActivity {

    private AppCompatSeekBar widthSeekbar;
    private AppCompatSeekBar heightSeekbar;
    private AppCompatEditText inputTextEdit;
    private ImageView barcodeImage;
    private Spinner bType;

    private String barcodeType;
    private Button generateBarcode,btnSave;
    private String inputString;
    public static final int RC_STORAGE_PERMISSION = 1000;
    BitmapDrawable draw;
    Bitmap bm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_barcode);

        widthSeekbar = findViewById(R.id.wSeekbar);
        heightSeekbar = findViewById(R.id.hSeekbar);
        inputTextEdit = findViewById(R.id.inputText);
        barcodeImage = findViewById(R.id.imageView);

        bType = findViewById(R.id.spinner);
        btnSave = (Button)findViewById(R.id.btn_save);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_values, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bType.setAdapter(adapter);

        bType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                barcodeType = bType.getSelectedItem().toString();
                // Toast.makeText(getApplicationContext(), barcodeType, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        generateBarcode = (Button) findViewById(R.id.button);
        generateBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                inputString = inputTextEdit.getText().toString();
                if (TextUtils.isEmpty(inputString))
                {
                    Toast.makeText(getApplicationContext(),"Please enter data!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if((inputString.length() != 8) && (barcodeType.equals("EAN_8")))
                    {
                        Toast.makeText(getApplicationContext(),"Enter at-least 8 numbers to use EAN_8",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int width = 400 + 200 * widthSeekbar.getProgress();
                        int height = 200 + 200 * heightSeekbar.getProgress();
                        BarcodeGeneratorHelper helper =
                                new BarcodeGeneratorHelper.Builder(inputString, barcodeType)
                                        .size(width,height)
                                        .build();
                        try {
                            Bitmap bitmap = helper.generateBitmap();
                            barcodeImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permissionCheck = ContextCompat.checkSelfPermission(GenerateBarcodeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED ) {

                    draw = (BitmapDrawable) barcodeImage.getDrawable();
                    bm = draw.getBitmap();



                    try {


                        FileOutputStream outStream = null;
                        File sdCard = Environment.getExternalStorageDirectory();
                        File dir = new File(sdCard.getAbsolutePath() + "/barCode");
                        dir.mkdirs();
                        String fileName = String.format("%s.jpg", inputString);
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
                    ActivityCompat.requestPermissions(GenerateBarcodeActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            RC_STORAGE_PERMISSION);
                }


            }
        });

    }
}
