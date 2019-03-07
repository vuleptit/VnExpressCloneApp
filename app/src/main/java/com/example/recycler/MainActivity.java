package com.example.recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.recycler.api.Api;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> listString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyleview_main);

        ArrayList<Content> list = new ArrayList<>();
        list.add(new Content(ContentState.STATE_TEXT,""));
        list.add(new Content(ContentState.STATE_IMAGE,"",""));
        list.add(new Content(ContentState.STATE_TEXT,""));
        list.add(new Content(ContentState.STATE_TEXT,""));

        listString = new ArrayList<>();
        listString.add("dang nam");
        listString.add("dang nam");
        listString.add("dang nam");
        listString.add("dang nam");
        listString.add("dang nam");
        RecyclerApdapter recyclerApdapter = new RecyclerApdapter(this,list);
        RecyclerAdapterMain adapterMain = new RecyclerAdapterMain(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (i % 2==0)
                return 1;
                else
                return 2;
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 5 is the sum of items in one repeated section
                switch (position % 4) {
                    // first two items span 3 columns each
                    case 0:
                    case 1:
                        return 4;
                    // next 3 items span 2 columns each
                    case 2:
                    case 3:
//                    case 4:
                        return 2;
                }
                throw new IllegalStateException("internal error");
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterMain);
        new Api(this,"").getData();
    }
}
