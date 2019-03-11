package com.example.recycler;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.recycler.api.ApiTheLoai;
import com.example.recycler.fragment.*;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiTheLoai.ApiTheLoaiData{
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ApiTheLoai apiTheLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
        apiTheLoai = new ApiTheLoai(this);
        apiTheLoai.getData("https://vnexpress.net/rss");
    }

    public void test(){
        listFragment = new ArrayList<>();
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void setTheLoaiData(ArrayList<String> listName, ArrayList<String> listLink) {
        for (String link :listLink){
            listFragment.add(new FagmentTrangChu(link));
            Log.d("link", link);
        }
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,listFragment,listName);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
