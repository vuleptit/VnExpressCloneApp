package com.example.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerAdapterMain extends RecyclerView.Adapter {
    private ArrayList<RssItem> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private ClickListener clickListener;
    DateFormat dateFormat ;
    public RecyclerAdapterMain(Context context,ClickListener clickListener, ArrayList<RssItem> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        this.dateFormat  = new SimpleDateFormat("dd-MM yyyy HH:mm");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case ContentState.STATE_VERTICAL:
                view = layoutInflater.inflate(R.layout.item_vertical,viewGroup,false);
                return new RecyclerAdapterMain.ViewHorderVertical(view);
            case ContentState.STATE_HORIZONTAL:
                view = layoutInflater.inflate(R.layout.item_horizontal,viewGroup,false);
                return new RecyclerAdapterMain.ViewHorderHorizontal(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHorder, int i) {
        switch (i%4) {
            case 0:
            case 1:
                ViewHorderVertical horderVertical = (ViewHorderVertical) viewHorder;
                horderVertical.title.setText(list.get(i).getTitle());
                horderVertical.date.setText(dateFormat.format(list.get(i).getDate()));
                Glide.with(context).load(list.get(i).getLinkImage()).into(horderVertical.imageView);
                break;
            case 2:
            case 3:
                ViewHorderHorizontal horderHorizontal = (ViewHorderHorizontal) viewHorder;
                horderHorizontal.title.setText(list.get(i).getTitle());
                horderHorizontal.date.setText(dateFormat.format(list.get(i).getDate()));
                Glide.with(context).load(list.get(i).getLinkImage()).into(horderHorizontal.imageView);
                break;

        }
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

    public class ViewHorderHorizontal extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,date;
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
            clickListener.clickItem(getAdapterPosition(),list.get(getAdapterPosition()));
        }
    }

    public class ViewHorderVertical extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,date;
        ImageView imageView;
        public ViewHorderVertical(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_vertical_item_title);
            date = itemView.findViewById(R.id.tv_vertical_item_date);
            imageView = itemView.findViewById(R.id.img_vertical_item_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.clickItem(getAdapterPosition(),list.get(getAdapterPosition()));
        }
    }
    public interface ClickListener{
        public void clickItem(int position,RssItem rssItem);
    }
}
