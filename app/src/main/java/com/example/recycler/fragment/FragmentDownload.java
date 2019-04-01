package com.example.recycler.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.recycler.R;
import com.example.recycler.RecyclerItemTouchHelper;
import com.example.recycler.State;
import com.example.recycler.activity.DetailActivity;
import com.example.recycler.adapter.HistoryAdapter;

import com.example.recycler.database1.AppDataBase;
import com.example.recycler.entity1.Article;
import com.example.recycler.entity1.Description;

import java.util.ArrayList;
import java.util.List;

public class FragmentDownload extends Fragment implements HistoryAdapter.ClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private RecyclerView recyclerView;
    private List<Article> list;
    private HistoryAdapter adapter;
    private AppDataBase appDataBase;
    private ConstraintLayout constraintLayout;
    private ArrayList<Article> listDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actitity_history,container,false);
        init(view);
        return view;
    }


    private void init(View view) {

        constraintLayout = view.findViewById(R.id.container_history);
        appDataBase = AppDataBase.getAppDatabase(getContext());
        recyclerView = view.findViewById(R.id.recyleview_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        list = appDataBase.articleDao().getAllArticleState(State.STATE_DOWNLOAD);
        adapter = new HistoryAdapter(getContext(), this, list);
        recyclerView.setAdapter(adapter);
        listDelete = new ArrayList<>();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swieped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Article deletedItem:listDelete){
            if(appDataBase.articleDao().getListName(deletedItem.getLinkImage()).size()<2){
                List<Description> listDescriptions = appDataBase.descriptionDao().getListImageDescription(deletedItem.getId());
                for (Description description : listDescriptions) {
                    deleteFIle(description.getContent());

                }
                deleteFIle(deletedItem.getLinkImage());
            }

            appDataBase.descriptionDao().deleteAll(deletedItem.getId());
            appDataBase.articleDao().delete(deletedItem);
        }
    }


    @Override
    public void clickItem(int position, Article article) {

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", article.getId());
        intent.putExtra("state", State.STATE_OFFLINE);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof HistoryAdapter.ViewHolder) {

            String name = list.get(viewHolder.getAdapterPosition()).getTitle().substring(0, 20) + "...";

            final Article deletedItem = list.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(viewHolder.getAdapterPosition());
            listDelete.add(deletedItem);


            Snackbar snackbar = Snackbar
                    .make(constraintLayout, name + " Đã bị xóa!", Snackbar.LENGTH_LONG);
            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    adapter.restoreItem(deletedItem, deletedIndex);
                    listDelete.remove(listDelete.size()-1);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            Log.d("xoa", "da xoa: ");
        }
    }
    private void deleteFIle(String name){
        try {
            getContext().deleteFile(name);
        } catch (Exception e) {
            e.getMessage();
        }
    }


}
