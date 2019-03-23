package com.example.recycler.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycler.R;
import com.example.recycler.adapter.PagerAdapter;
import com.example.recycler.api.ApiTheLoai;

import java.util.ArrayList;

public class FragmentHome extends Fragment  implements ApiTheLoai.ApiTheLoaiData{
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ApiTheLoai apiTheLoai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        init(view);
        return view;
    }
    private void init(View view){
        listFragment = new ArrayList<>();
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tablayout);
        fragmentManager = getChildFragmentManager();
        apiTheLoai = new ApiTheLoai(this);
        apiTheLoai.theloai();

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
