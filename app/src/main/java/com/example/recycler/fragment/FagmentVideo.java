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

import com.example.recycler.R;
import com.example.recycler.State;
import com.example.recycler.activity.DetailActivity;
import com.example.recycler.activity.VideoActivity;
import com.example.recycler.adapter.RecyclerAdapterMain;
import com.example.recycler.adapter.RecyclerAdapterVideo;
import com.example.recycler.api.ApiVideo;
import com.example.recycler.api.ApiXML;
import com.example.recycler.entity1.RssItem;
import com.example.recycler.entity1.Video;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FagmentVideo extends Fragment implements ApiVideo.DataApiVideo,RecyclerAdapterVideo.ClickListener{
    private RecyclerView recyclerView;
    private ApiVideo apiVideo;
    private RecyclerAdapterMain recyclerAdapterMain;
    private String link;
    private ArrayList<RssItem> list;
    private ArrayList<Video> listState;
    private LinearLayoutManager linearLayoutManager;
    RecyclerAdapterVideo adapterVideo;
    private static final String TAG = "FagmentVideo";
    public FagmentVideo() {

        apiVideo = new ApiVideo(this);
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
            apiVideo.getData(link);
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
            listState = (ArrayList<Video>) savedInstanceState.getSerializable("data");
            Log.d(TAG, "onViewStateRestored: co du lieu");
            Log.d(TAG, "onViewStateRestored: " + listState.size());
            if (listState.size() == 0) {
                apiVideo.getData(link);
            } else{
                setDataRecyclerView(listState);
            }
        }
    }
    private void init(View view){

        recyclerView = view.findViewById(R.id.recyleview_trangchu);
        linearLayoutManager = new LinearLayoutManager(getContext());

//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
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
        adapterVideo = new RecyclerAdapterVideo(getContext(), this, new ArrayList<Video>());
        recyclerView.setAdapter(adapterVideo);
    }
    private void setDataRecyclerView(ArrayList<Video> listVideo){
        try {
            adapterVideo.update(listVideo);

        }catch (Exception e){
            Log.d(TAG, "setDataRecyclerView: "+link);
        }



    }
    @Override
    public void setData(ArrayList<Video> listVideo) {
        setDataRecyclerView(listVideo);
        this.listState = listVideo;
    }

    @Override
    public void clickItem(int position, Video video) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("linkVideo",video.getLinkVideo());
        intent.putExtra("position",position);
        startActivity(intent  );
    }
}
