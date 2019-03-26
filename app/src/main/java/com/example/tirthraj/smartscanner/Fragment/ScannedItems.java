package com.example.tirthraj.smartscanner.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.tirthraj.smartscanner.R;
import com.example.tirthraj.smartscanner.controller.MainActivity;
import com.example.tirthraj.smartscanner.controller.ProductAdapter;
import com.example.tirthraj.smartscanner.database.DatabaseHelper;


import java.util.ArrayList;


public class ScannedItems extends Fragment  implements MainActivity.ItemScanned {
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;
    ArrayList<Object> productArrayList;
    private RelativeLayout mainLayout , emptyLayout ;
    DatabaseHelper db ;
    public ScannedItems(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_product_list,container,false);
        mRecyclerView = view.findViewById(R.id.product_list_recycler_view);
        swipeRefresh = view.findViewById(R.id.swipe_refresh_layout);
        mainLayout = view.findViewById(R.id.main_layout);
        emptyLayout = view.findViewById(R.id.empty_layout);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.green),getResources().getColor(R.color.blue),getResources().getColor(R.color.orange));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProductList();
            }
        });


        loadProductList();
        return view;
    }

    private void loadProductList() {
        db= new DatabaseHelper(getContext());
        productArrayList = db.getAllProduct();


        if(!productArrayList.isEmpty()){
            mAdapter = new ProductAdapter(getContext(), productArrayList);
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            swipeRefresh.setRefreshing(false);
            emptyLayout.setVisibility(View.GONE);
        }
        else{
            emptyLayout.setVisibility(View.VISIBLE);
            swipeRefresh.setRefreshing(false);
        }

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loadProductList();
    }

    @Override
    public void itemUpdated() {
        loadProductList();
    }
}
