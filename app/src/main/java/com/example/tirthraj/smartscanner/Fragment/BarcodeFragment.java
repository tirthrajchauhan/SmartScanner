package com.example.tirthraj.smartscanner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tirthraj.smartscanner.R;
import com.example.tirthraj.smartscanner.controller.WebViewActivity;


public class BarcodeFragment extends Fragment   {
    private static final String TAG= "BarcodeFragment";
    private ScanRequest scanRequest ;
    private String manual;
    EditText manualSearch;

    public BarcodeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_barcode, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnScan = view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanRequest.scanBarcode();

            }
        });
        manualSearch = view.findViewById(R.id.manualSearch);
        Button btnManual = view.findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manual = manualSearch.getText().toString();
                // Toast.makeText(getContext(), "Saved"+manual, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("product_id", manual);
                startActivity(intent);


            }
        });

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            scanRequest = (ScanRequest) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement retryConnectionListener");
        }

    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnScan :
//                // Pass the click event to activity to start the scanner .
//                scanRequest.scanBarcode();
//                break ;
//        }
//    }


    public interface  ScanRequest{
        void scanBarcode();
    }

}
