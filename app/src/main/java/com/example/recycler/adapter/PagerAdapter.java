package com.example.recycler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;

    public PagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragment, ArrayList<String> listTitle) {
        super(fm);
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
         return listFragment.get(position)       ;
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
    @Override
    public CharSequence getPageTitle(int position)    {
        return listTitle.get(position);
    }
}
