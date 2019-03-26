package com.example.tirthraj.smartscanner.controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tirthraj.smartscanner.R;
import com.example.tirthraj.smartscanner.model.Product;
import com.example.tirthraj.smartscanner.utils.Clipboard;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private ArrayList<Object> productArrayList;
    private static final int PRODUCT_ITEM_VIEW_TYPE = 0 ;
    public ProductAdapter(Context context, ArrayList<Object> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case PRODUCT_ITEM_VIEW_TYPE :
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_barcode,parent,false);
                return new ProductViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case PRODUCT_ITEM_VIEW_TYPE:
            default :
                ProductViewHolder productViewHolder = (ProductViewHolder)holder;
                setProductView(productViewHolder , position);
                break ;


        }
    }

    private void setProductView(ProductViewHolder holder, final  int position) {
        final Product product = (Product)productArrayList.get(position);
        holder.txtScanResult.setText(product.getProductBarcodeNo());
        holder.txtScanTime.setText(product.getScanDate()+" "+product.getScanTime());
        holder.txtScanNo.setText(String.valueOf(position+1));

        if(position%2==0){
            holder.layoutRightButtons.setBackgroundColor(context.getResources().getColor(R.color.card_right_blue));
        }
        if(position%3==0){
            holder.layoutRightButtons.setBackgroundColor(context.getResources().getColor(R.color.card_right_purple));
        }


        holder.layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("product_id",product.getProductBarcodeNo());
                context.startActivity(intent);
            }
        });
        holder.layoutCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clipboard clipBoard = new Clipboard();
                clipBoard.copyToClipboard(context,product.getProductBarcodeNo());
                Snackbar.make(v,"Copied To Clipboard",Snackbar.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public int getItemViewType(int position) {

            return  PRODUCT_ITEM_VIEW_TYPE;


    }



    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRightButtons ;
        private RelativeLayout layoutCopy , layoutSearch ;
        private TextView txtScanResult , txtScanNo , txtScanTime ;
        private Button btnShare ;

        public ProductViewHolder(View itemView) {
            super(itemView);
            layoutRightButtons = itemView.findViewById(R.id.layout_right_buttons);
            layoutCopy = itemView.findViewById(R.id.layout_copy);
            layoutSearch = itemView.findViewById(R.id.layout_search);
            txtScanNo = itemView.findViewById(R.id.txt_scan_no);
            txtScanResult = itemView.findViewById(R.id.txt_scan_result);
            txtScanTime = itemView.findViewById(R.id.txt_date_time);

        }
    }


}

