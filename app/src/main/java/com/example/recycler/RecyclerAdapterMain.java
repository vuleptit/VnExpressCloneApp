package com.example.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerAdapterMain extends RecyclerView.Adapter {
    private ArrayList<Content> list;
    private LayoutInflater layoutInflater;

    public RecyclerAdapterMain(Context context, ArrayList<Content> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case ContentState.STATE_VERTICAL:
                view = layoutInflater.inflate(R.layout.item_1,viewGroup,false);
                return new RecyclerAdapterMain.ViewHorderVertical(view);
            case ContentState.STATE_HORIZONTAL:
                view = layoutInflater.inflate(R.layout.item_2,viewGroup,false);
                return new RecyclerAdapterMain.ViewHorderHorizontal(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHorder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position%4) {
            case 0:
            case 1:
                return ContentState.STATE_VERTICAL;
            case 2:
            case 3:
                return ContentState.STATE_HORIZONTAL;
        }
        return ContentState.STATE_VERTICAL;
    }

    public class ViewHorderHorizontal extends RecyclerView.ViewHolder {

        public ViewHorderHorizontal(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ViewHorderVertical extends RecyclerView.ViewHolder {

        public ViewHorderVertical(@NonNull View itemView) {
            super(itemView);
        }
    }
}
