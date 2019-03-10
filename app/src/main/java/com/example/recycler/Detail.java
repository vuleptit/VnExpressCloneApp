package com.example.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.recycler.api.Api;
import com.example.recycler.api.ApiXML;

import java.util.ArrayList;

public class Detail extends AppCompatActivity implements Api.ApiData{
    RecyclerView recyclerView;
    Api api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }
    private void init(){
        Intent intent = this.getIntent();
        recyclerView = findViewById(R.id.recyleview_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        api = new Api(this);
        api.setApiData(this);
        api.getData(intent.getStringExtra("linkDetail"));
        Log.d("dangnam", intent.getStringExtra("linkDetail"));
    }

    @Override
    public void setData(ArrayList<Content> listContent) {
        RecyclerApdapterDetail apdapter = new RecyclerApdapterDetail(getApplicationContext(),listContent);
        recyclerView.setAdapter(apdapter);
    }
}
