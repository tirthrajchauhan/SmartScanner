package com.example.tirthraj.smartscanner.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tirthraj.smartscanner.R;


public class BarcodeFragment extends Fragment   {
    private static final String TAG= "BarcodeFragment";
    private ScanRequest scanRequest ;

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
