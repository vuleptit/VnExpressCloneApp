package com.example.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycler.R;
import com.example.recycler.entity1.RssItem;
import com.example.recycler.State;
import com.example.recycler.entity1.Video;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerAdapterVideo extends RecyclerView.Adapter {
    private ArrayList<Video> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private ClickListener clickListener;
    DateFormat dateFormat;

    public RecyclerAdapterVideo(Context context, ClickListener clickListener, ArrayList<Video> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case State.STATE_VERTICAL:
                view = layoutInflater.inflate(R.layout.item_vertical, viewGroup, false);
                return new ViewHorderVertical(view);
            case State.STATE_HORIZONTAL:
                view = layoutInflater.inflate(R.layout.item_horizontal, viewGroup, false);
                return new ViewHorderHorizontal(view);
            case State.STATE_HOME:
                view = layoutInflater.inflate(R.layout.item_home, viewGroup, false);
                return new ViewHorderHome(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHorder, int i) {
        if (i == 0) {
            ViewHorderHome viewHorderHome = (ViewHorderHome) viewHorder;
            viewHorderHome.title.setText(list.get(i).getTitle());
            viewHorderHome.date.setText(dateFormat.format(list.get(i).getDate()));
            viewHorderHome.description.setText("");
            Glide.with(context).load(list.get(i).getLinkImage()).into(viewHorderHome.imageView);
        } else {
//            switch ((i-1) % 4) {
//                case 0:
//                case 1:
                    ViewHorderVertical horderVertical = (ViewHorderVertical) viewHorder;
                    horderVertical.title.setText(list.get(i).getTitle());
                    horderVertical.date.setText(dateFormat.format(list.get(i).getDate()));
                    Glide.with(context).load(list.get(i).getLinkImage()).into(horderVertical.imageView);
//                    break;
//                case 2:
//                case 3:
//                    ViewHorderHorizontal horderHorizontal = (ViewHorderHorizontal) viewHorder;
//                    if(list.get(i).getTitle().length()>50){
//                        horderHorizontal.title.setText(list.get(i).getTitle().substring(0,50)+"...");
//                    }
//                    else {
//                        horderHorizontal.title.setText(list.get(i).getTitle());
//                    }
//                    horderHorizontal.date.setText(dateFormat.format(list.get(i).getDate()));
//                    Glide.with(context).load(list.get(i).getLinkImage()).into(horderHorizontal.imageView);
//                    break;

           // }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return State.STATE_HOME;
        }
//        else {
//            switch ((position - 1) % 4) {
//                case 0:
//                case 1:
//                    return State.STATE_VERTICAL;
//                case 2:
//                case 3:
//                    return State.STATE_HORIZONTAL;
//            }
//        }
        return State.STATE_VERTICAL;
    }

    public class ViewHorderHorizontal extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, date;
        ImageView imageView;

        public ViewHorderHorizontal(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_horizontal_item_title);
            date = itemView.findViewById(R.id.tv_horizontal_item_date);
            imageView = itemView.findViewById(R.id.img_horizontal_item_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.clickItem(getAdapterPosition(), list.get(getAdapterPosition()));
        }
    }

    public class ViewHorderHome extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, date, description;
        ImageView imageView,icon;

        public ViewHorderHome(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_home_item_title);
            date = itemView.findViewById(R.id.tv_home_item_date);
            description = itemView.findViewById(R.id.tv_home_item_description);
            imageView = itemView.findViewById(R.id.img_home_item_img);
            icon = itemView.findViewById(R.id.video_icon_home);
            icon.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.clickItem(getAdapterPosition(), list.get(getAdapterPosition()));
        }
    }

    public class ViewHorderVertical extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, date;
        ImageView imageView,icon;

        public ViewHorderVertical(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_vertical_item_title);
            date = itemView.findViewById(R.id.tv_vertical_item_date);
            imageView = itemView.findViewById(R.id.img_vertical_item_img);
            icon = itemView.findViewById(R.id.video_icon);
            icon.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.clickItem(getAdapterPosition(), list.get(getAdapterPosition()));
        }
    }

    public interface ClickListener {
        public void clickItem(int position, Video video);
    }
}
