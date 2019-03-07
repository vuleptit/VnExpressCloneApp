package com.example.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerApdapter extends RecyclerView.Adapter {
    private ArrayList<Content> list;
    private LayoutInflater layoutInflater;

    public RecyclerApdapter(Context context, ArrayList<Content> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case ContentState.STATE_TEXT:
                view = layoutInflater.inflate(R.layout.item_text,viewGroup,false);
                return new ViewHorderText(view);
            case ContentState.STATE_IMAGE:
                view = layoutInflater.inflate(R.layout.item_image,viewGroup,false);
                return new ViewHoderImage(view);
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
        switch (list.get(position).getState()) {
            case ContentState.STATE_TEXT:
                return ContentState.STATE_TEXT;
            case ContentState.STATE_IMAGE:
                return ContentState.STATE_IMAGE;
        }
        return ContentState.STATE_TEXT;
    }

    public class ViewHorderText extends RecyclerView.ViewHolder {

        public ViewHorderText(@NonNull View itemView) {
            super(itemView);

        }
    }

    public class ViewHoderImage extends RecyclerView.ViewHolder {


        public ViewHoderImage(@NonNull View itemView) {
            super(itemView);
        }
    }

}