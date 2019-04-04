package com.example.recycler.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.example.recycler.R;
import com.example.recycler.adapter.PagerAdapter;
import com.example.recycler.api.ApiTheLoai;

import java.net.InetAddress;
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
        if(!isConnectedToNetwork(getContext())){
            Toast.makeText(getContext(), " không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
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
            Bundle bundle = new Bundle();
            bundle.putString("link",link);
            FagmentTrangChu fagmentTrangChu = new FagmentTrangChu();
            fagmentTrangChu.setArguments(bundle);
            listFragment.add(fagmentTrangChu);
            Log.d("link", link);
        }
        Bundle bundle = new Bundle();
        bundle.putString("link","http://ocp-api-v2.gdcvn.com/v1/publishers/get-items?id=120&limit=0&offset=20&publisher_key=zw5yfhcygiruH81M");
        FagmentVideo fagmentVideo = new FagmentVideo();
        fagmentVideo.setArguments(bundle);

        listFragment.add(fagmentVideo);

        listName.add("video");
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,listFragment,listName);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }
}
