package com.example.recycler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycler.R;
import com.example.recycler.entity1.Article;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Article> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private ClickListener clickListener;
    private DateFormat dateFormat ;
    public HistoryAdapter(Context context, HistoryAdapter.ClickListener clickListener, List<Article> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        this.dateFormat  = new SimpleDateFormat("dd-MM yyyy HH:mm");
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_history,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.date.setText(list.get(i).getDate());
        String link = list.get(i).getLinkImage();
        if(link!=""){
            Bitmap bitmap = loadImageBitmap(context.getApplicationContext(),link);
            Glide.with(context.getApplicationContext()).load(bitmap).into(viewHolder.imageView);
        }

       // Glide.with(context).load(list.get(i).getLinkImage()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout viewBackground, viewForeground;
        TextView title,date;
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
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
    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Article article, int position) {
        list.add(position, article);
        notifyItemInserted(position);
    }
    public interface ClickListener{
        public void clickItem(int position,Article article);
    }

}
