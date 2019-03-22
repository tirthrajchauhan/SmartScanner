package com.example.tirthraj.smartscanner.controller;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tirthraj.smartscanner.R;

import com.example.tirthraj.smartscanner.controller.BarcodeFormat;

import java.io.IOException;

public class GenerateBarcodeActivity extends AppCompatActivity {

    private AppCompatSeekBar widthSeekbar;
    private AppCompatSeekBar heightSeekbar;
    private AppCompatEditText inputTextEdit;
    private ImageView barcodeImage;
    private Spinner bType;

    private String barcodeType;
    private Button generateBarcode;
    private String inputString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_barcode);

        widthSeekbar = findViewById(R.id.wSeekbar);
        heightSeekbar = findViewById(R.id.hSeekbar);
        inputTextEdit = findViewById(R.id.inputText);
        barcodeImage = findViewById(R.id.imageView);

        bType = findViewById(R.id.spinner);


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
    }
}
