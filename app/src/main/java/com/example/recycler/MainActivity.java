package com.example.recycler;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.example.recycler.fragment.*;

import com.example.recycler.api.ApiXML;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    public void test(){
        listFragment = new ArrayList<>();
        listTitle = new ArrayList<>();
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/tin-moi-nhat.rss"));
        listTitle.add("Tin moi");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/thoi-su.rss"));
        listTitle.add("Thoi su");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/the-gioi.rss"));
        listTitle.add("The gio");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/kinh-doanh.rss"));
        listTitle.add("kink doanh");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/startup.rss"));
        listTitle.add("startup");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/tin-moi-nhat.rss"));
        listTitle.add("Tin moi");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/tin-moi-nhat.rss"));
        listTitle.add("Tin moi");
        listFragment.add(new FagmentTrangChu("https://vnexpress.net/rss/tin-moi-nhat.rss"));
        listTitle.add("Tin moi");
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,listFragment,listTitle);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
