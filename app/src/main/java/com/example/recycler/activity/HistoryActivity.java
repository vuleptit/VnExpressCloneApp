package com.example.recycler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.recycler.R;
import com.example.recycler.adapter.HistoryAdapter;
import com.example.recycler.adapter.PagerAdapter;
import com.example.recycler.api.ApiTheLoai;
import com.example.recycler.database1.AppDataBase;
import com.example.recycler.entity1.Article;
import com.example.recycler.fragment.FagmentTrangChu;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity   extends AppCompatActivity implements HistoryAdapter.ClickListener{
    private RecyclerView recyclerView;
    private List<Article> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitity_history);
        recyclerView = findViewById(R.id.recyleview_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = AppDataBase.getAppDatabase(getApplicationContext()).articleDao().getAllArticle();
        HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(),this,list);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void clickItem(int position, Article article) {

        Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
        intent.putExtra("id",article.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }
}
