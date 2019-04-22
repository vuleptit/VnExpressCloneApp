package com.example.recycler.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycler.FacebookLogin;
import com.example.recycler.R;
import com.example.recycler.fragment.*;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentHome fragmentHome;
    private FragmentDownload fragmentDownload;
    private int check = 0;
    private FragmentManager fragmentManager;
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);//cài đặt layout được hiển thị
        accessToken = AccessToken.getCurrentAccessToken();


//            NavigationView navHeader = (NavigationView) findViewById(R.id.navigation_main_login);
//            View hView = navHeader.getHeaderView(0);
//            TextView userName = (TextView) hView.findViewById(R.id.user_text_login);
//            userName.setText("Lê Hồng Vũ");

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_main_login);
        View headerView = navigationView.getHeaderView(0);
        if(accessToken != null){
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            TextView userName = (TextView) headerView.findViewById(R.id.user_text_login);
            ProfilePictureView uPic = (ProfilePictureView) headerView.findViewById(R.id.profile_image);
//            userPicture.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            Intent intent = getIntent();

            if(intent != null){
                Bundle extras = intent.getExtras();
                if(extras != null){
                    String isLogged = extras.getString("UName", "Nope");
                    if(!isLogged.equals("Nope")){
                        userName.setText(intent.getExtras().getString("UName"));
                        uPic.setProfileId(intent.getExtras().getString("UID"));
                    }
                }else{
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                }
            }
        }else{
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            TextView userName = (TextView) headerView.findViewById(R.id.user_text_login);
            userName.setText("User name");
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_main_login);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        init();

        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(this);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,new FragmentHome()).commit();


//lấy keyhash của ứng dụng
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.recycler",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.navigation_main);
//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.setDrawerIndicatorEnabled(true);
//        drawerToggle.syncState();
//        init();
//
//        if(navigationView!=null){
//            navigationView.setNavigationItemSelectedListener(this);
//        }
//
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
            }
            else {
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
            case R.id.nav_login:
                Intent intent = new Intent(this, FacebookLogin.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                LoginManager.getInstance().logOut();
                Intent intentOut = new Intent(this , MainActivity.class);
                startActivity(intentOut);
                break;
        }
        check = id;
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}


