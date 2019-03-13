package com.example.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.example.recycler.api.Api;
import com.example.recycler.api.ApiXML;

import java.util.ArrayList;

public class Detail extends AppCompatActivity implements Api.ApiData, View.OnClickListener {
    private RecyclerView recyclerView;
    private Api api;
    private BottomAppBar bottomAppBar;
    private ImageButton btnBack,btnSave,btnComment,btnShare;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bottomAppBar = findViewById(R.id.bottom_appbar);
        setSupportActionBar(bottomAppBar);
        setButton();
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
    private void setButton(){
        btnBack = findViewById(R.id.btn_back);
        btnComment = findViewById(R.id.btn_comment);
        btnSave = findViewById(R.id.btn_save);
        btnShare = findViewById(R.id.btn_share);

        btnShare.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }
    @Override
    public void setData(ArrayList<Content> listContent) {
        RecyclerApdapterDetail apdapter = new RecyclerApdapterDetail(getApplicationContext(),listContent);
        recyclerView.setAdapter(apdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }


}
