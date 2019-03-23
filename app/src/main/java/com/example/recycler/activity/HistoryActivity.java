package com.example.recycler.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recycler.R;
import com.example.recycler.RecyclerItemTouchHelper;
import com.example.recycler.State;
import com.example.recycler.adapter.HistoryAdapter;
import com.example.recycler.adapter.PagerAdapter;
import com.example.recycler.api.ApiTheLoai;
import com.example.recycler.database1.AppDataBase;
import com.example.recycler.entity1.Article;
import com.example.recycler.entity1.Description;
import com.example.recycler.fragment.FagmentTrangChu;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.ClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private RecyclerView recyclerView;
    private List<Article> list;
    private HistoryAdapter adapter;
    private AppDataBase appDataBase;
    private ConstraintLayout constraintLayout;
    private ArrayList<Article> listDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitity_history);
        constraintLayout = findViewById(R.id.container_history);
        appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        recyclerView = findViewById(R.id.recyleview_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        list = appDataBase.articleDao().getAllArticle();
        adapter = new HistoryAdapter(getApplicationContext(), this, list);
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
    protected void onStop() {
        super.onStop();
        for (Article deletedItem:listDelete){
            List<Description> listDescriptions = appDataBase.descriptionDao().getListImageDescription(deletedItem.getId());
            for (Description description : listDescriptions) {
                deleteFIle(description.getContent());

            }
            deleteFIle(deletedItem.getLinkImage());
            appDataBase.descriptionDao().deleteAll(deletedItem.getId());
            appDataBase.articleDao().delete(deletedItem);
        }
    }

    @Override
    public void clickItem(int position, Article article) {

        Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
        intent.putExtra("id", article.getId());
        intent.putExtra("state", State.STATE_OFFLINE);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
            getApplicationContext().deleteFile(name);
        } catch (Exception e) {
            e.getMessage();
        }
    }


}
