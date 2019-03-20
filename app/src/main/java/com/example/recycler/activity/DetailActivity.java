package com.example.recycler.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.bumptech.glide.Glide;
import com.example.recycler.R;
import com.example.recycler.State;
import com.example.recycler.adapter.RecyclerApdapterDetail;
import com.example.recycler.api.Api;
import com.example.recycler.database1.AppDataBase;
import com.example.recycler.entity1.Article;
import com.example.recycler.entity1.Content;
import com.example.recycler.entity1.Description;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements Api.ApiData, View.OnClickListener {
    private RecyclerView recyclerView;
    private Api api;
    private BottomAppBar bottomAppBar;
    private ImageButton btnBack,btnSave,btnComment,btnShare;
    private SlidrInterface slidrInterface;
    private List<Description> listDescription;
    private ArrayList<Content> listContents;
    private AppDataBase appDataBase;
    private Article article;
    private String linkImage;
    private int state;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bottomAppBar = findViewById(R.id.bottom_appbar);
        setSupportActionBar(bottomAppBar);
        getDataIntent();
        init();
        setButton();
        if(state==State.STATE_INTERNET){
            loadInternet();
        }else {
            Toast.makeText(this,"id: "+id,Toast.LENGTH_LONG).show();
            loadOffline();
        }

        slidrInterface = Slidr.attach(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
    private void getDataIntent(){
        Intent intent = this.getIntent();

        state = intent.getIntExtra("state",State.STATE_INTERNET);
        id = intent.getIntExtra("id",1);

    }
    private void init(){
        appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        recyclerView = findViewById(R.id.recyleview_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);


    }

    public void loadInternet(){
        Intent intent = this.getIntent();
        api = new Api(this);
        api.setApiData(this);
        api.getData(intent.getStringExtra("linkDetail"));
        article = new Article("","","", State.STATE_HISTORY);
        article.setDate(intent.getStringExtra("date"));
        linkImage =intent.getStringExtra("linkImage");
        String s = linkImage.substring(linkImage.lastIndexOf("/ ") + 1);
        article.setLinkImage(s);
        article.setTitle(intent.getStringExtra("title"));
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
        this.listContents = listContent;
        RecyclerApdapterDetail apdapter = new RecyclerApdapterDetail(getApplicationContext(),listContent);
        recyclerView.setAdapter(apdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                break;
            case R.id.btn_save:
                saveData();
                break;
            case R.id.btn_comment:
                loadOffline();
                break;
            case R.id.btn_share:
                Intent intent = new Intent(DetailActivity.this,HistoryActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void lockSlide(View v) {
        slidrInterface.lock();
    }

    public void unlockSlide(View v) {
        slidrInterface.unlock();
    }
    private void loadOffline(){
        appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        List<Description> list = appDataBase.descriptionDao().getAllDescription(id);
        ArrayList<Content> arrayList = new ArrayList<>();
        Log.d("idtest", id+": "+appDataBase.articleDao().getAllArticle().size());
        String link = "";
        String text = "";
        for (Description description : list){
            if(description.getState()!= State.STATE_IMAGE&&description.getState()!= State.STATE_IMAGE_TEXT){
                arrayList.add(new Content(description.getState(),description.getContent()));
            }
            else {
                if(description.getState()== State.STATE_IMAGE){
                    link = description.getContent();
                }
                else {
                    text = description.getContent();

                }
            }
            if(link!=""&&text!=""){
                arrayList.add(new Content(State.STATE_IMAGE,link,text));
                link ="";
                text="";
            }
        }
        setData(arrayList);
    }
    private void saveData(){

        appDataBase.articleDao().insert(article);
        loadImage(linkImage,article.getLinkImage());
        int id = appDataBase.articleDao().getLastArticle().getId();
        String name="";
        String link="";
        for (Content content: listContents){
            if(content.getState()== State.STATE_IMAGE){
                link = content.getLinkImage();
                 name= link.substring(link.lastIndexOf('/') + 1);
                appDataBase.descriptionDao().insert(new Description(name, State.STATE_IMAGE,id));
                appDataBase.descriptionDao().insert(new Description(content.getTextImage(), State.STATE_IMAGE_TEXT,id));
                loadImage(link,name);
            }
            else {
                appDataBase.descriptionDao().insert(new Description(content.getText(),content.getState(),id));
            }

        }
        Toast.makeText(this,"Đã lưu",Toast.LENGTH_LONG).show();

    }
    public void loadImage(String link,final String name){
        AndroidNetworking.get(link).setTag("test")
                .setPriority(Priority.HIGH)
                .build().getAsBitmap(new BitmapRequestListener() {
            @Override
            public void onResponse(Bitmap response) {
                saveImage(getApplicationContext(),response,name);
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

}
