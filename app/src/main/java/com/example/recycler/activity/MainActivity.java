package com.example.recycler.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recycler.R;
import com.example.recycler.fragment.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentHome fragmentHome;
    private FragmentDownload fragmentDownload;
    private int check = 0;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_main);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        init();

        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(this);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,new FragmentHome()).commit();
//        navigationView.getMenu().getItem(0).setChecked(true);
    }


    public void init(){
//        ImageView imageView = findViewById(R.id.image_nav);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.anh1);
//        RoundedBitmapDrawable bitmapDrawableFactory = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
//        bitmapDrawableFactory.setCircular(true);
//        imageView.setImageDrawable(bitmapDrawableFactory);

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(check==0||check==R.id.nav_home){
                super.onBackPressed();
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,new FragmentHome()).commit();
                check =0;
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,new FragmentHome()).commit();
                break;
            case R.id.nav_download:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,new FragmentDownload()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,new FragmentHistory()).commit();
                break;
        }
        check = id;
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
