package com.example.recycler.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycler.State;
import com.example.recycler.activity.DetailActivity;
import com.example.recycler.R;
import com.example.recycler.adapter.RecyclerAdapterMain;
import com.example.recycler.entity1.RssItem;
import com.example.recycler.api.ApiXML;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class FagmentTrangChu extends Fragment implements ApiXML.DataApiXML ,RecyclerAdapterMain.ClickListener{
    private RecyclerView recyclerView;
    private ApiXML apiXML;
    private RecyclerAdapterMain recyclerAdapterMain;
    private String link;
    private ArrayList<RssItem> list;
    private GridLayoutManager layoutManager;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<RssItem> listState;
    private RecyclerAdapterMain adapterMain;
    public static String TAG = "FagmentTrangChu";
    public FagmentTrangChu() {
        apiXML = new ApiXML();
        apiXML.setDataApiXML(this);
        list = new ArrayList<>();
        listState = new ArrayList<>();
    }
    public void setLink(String link){
        this.link = link;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            link = getArguments().getString("link");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu,container,false);
        init(view);
        if(link!=null&&savedInstanceState==null){
            apiXML.getDataXML(link);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data", listState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null) {
            listState = (ArrayList<RssItem>) savedInstanceState.getSerializable("data");
            Log.d(TAG, "onViewStateRestored: co du lieu");
            Log.d(TAG, "onViewStateRestored: " + listState.size());
            if (listState.size() == 0) {
                apiXML.getDataXML(link);
            } else{
                setDataRecyclerView(listState);
             }
        }
    }

    private void init(View view){

        recyclerView = view.findViewById(R.id.recyleview_trangchu);
        linearLayoutManager = new LinearLayoutManager(getContext());

//        layoutManager = new GridLayoutManager(getContext(), 2);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                // 5 is the sum of items in one repeated section
//                if(position==0){
//                    return 2;
//                }else {
//                    switch ((position - 1) % 4) {
//                        case 0:
//                        case 1:
//                            return 2;
//                        case 2:
//                        case 3:
//                            return 1;
//                    }
//                }
//                throw new IllegalStateException("internal error");
//            }
//        });
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    @Override
    public void setData(ArrayList<RssItem> listRssItem) {
        setDataRecyclerView(listRssItem);
        listState =listRssItem;
        int i = 0;
        for(RssItem rssItem: listRssItem){
            if(i<5){
                list.add(rssItem);
            }
            i++;
        }
    }
    private void setDataRecyclerView(ArrayList<RssItem> listRssItem){
        try {

            adapterMain = new RecyclerAdapterMain(getContext(), this, listRssItem);
            recyclerView.setAdapter(adapterMain);
        }catch (Exception e){
            Log.d(TAG, "setDataRecyclerView: "+link);
        }



    }

    @Override
    public void clickItem(int position, RssItem rssItem) {
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        String s = rssItem.getLinkImage();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("linkDetail",rssItem.getLinkDetail());
        intent.putExtra("linkImage",s);
        intent.putExtra("date",dateFormat.format(rssItem.getDate()));
        intent.putExtra("title",rssItem.getTitle());
        intent.putExtra("state", State.STATE_INTERNET);
        intent.putExtra("list",list);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }
}
