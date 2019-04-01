package com.example.recycler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycler.R;
import com.example.recycler.State;
import com.example.recycler.entity1.Video;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    private ArrayList<Video> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private VideoAdapter.ClickListener clickListener;
    DateFormat dateFormat;

    public VideoAdapter(Context context, VideoAdapter.ClickListener clickListener, ArrayList<Video> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_history,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.date.setText(dateFormat.format(list.get(i).getDate()));
        Glide.with(context).load(list.get(i).getLinkImage()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,date;
        ImageView imageView,icon;
        public ViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.tv_vertical_item_title);
            date = itemView.findViewById(R.id.tv_vertical_item_date);
            imageView = itemView.findViewById(R.id.img_vertical_item_img);
            icon = itemView.findViewById(R.id.video_icon);
            icon.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.clickItem(getAdapterPosition(),list.get(getAdapterPosition()));
        }
    }
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Video article, int position) {
        list.add(position, article);
        notifyItemInserted(position);
    }
    public interface ClickListener {
        public void clickItem(int position, Video video);
    }
}
