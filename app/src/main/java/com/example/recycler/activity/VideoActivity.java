package com.example.recycler.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.recycler.R;
import com.example.recycler.adapter.VideoAdapter;
import com.example.recycler.api.ApiVideo;
import com.example.recycler.entity1.Video;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity  implements View.OnClickListener, ApiVideo.DataApiVideo, VideoAdapter.ClickListener, View.OnTouchListener {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;
    private ImageButton next;

    private BottomAppBar bottomAppBar;
    private ImageButton btnBack,btnSave,btnComment,btnShare;
    private SlidrInterface slidrInterface;

    private ApiVideo apiVideo;
    private RecyclerView recyclerView;

    private boolean isClick;
    private static final String TAG = "VideoActivity";
    private  float x = 0,y=0,mX,mY;
    private Display display;
    private int width=0;
    private float volumeCurrent =0,volumeY;
    private int volume;
    private TextView tvVolume;
    private AudioManager audioManager;
    private LinearLayout layoutVolume;
    private Video video;
    private int poisition;
    private VideoAdapter videoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        bottomAppBar = findViewById(R.id.bottom_appbar);
        setSupportActionBar(bottomAppBar);
        setButton();


        slidrInterface = Slidr.attach(this);
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        init();
        display = getWindowManager().getDefaultDisplay();
         audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        volumeCurrent = audioManager.getStreamVolume(mExoPlayerView.getPlayer().getAudioStreamType());
//        tvVolume.setText(volumeCurrent+"");

    }

    private void init(){
        Intent intent = getIntent();
        String link = intent.getStringExtra("linkVideo");
        poisition = intent.getIntExtra("position",0);
        setData(link);
        recyclerView = findViewById(R.id.recyleview_video);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        apiVideo = new ApiVideo(this);
        apiVideo.getData("http://ocp-api-v2.gdcvn.com/v1/publishers/get-items?id=120&limit=0&offset=20&publisher_key=zw5yfhcygiruH81M");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }


    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(VideoActivity.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(VideoActivity.this, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }


    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);
        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }
        mExoPlayerView.getPlayer().prepare(mVideoSource);

        mExoPlayerView.getPlayer().setPlayWhenReady(true);


    }


    @Override
    protected void onResume() {

        super.onResume();

    }

    private void setData(String liData){
        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
            mExoPlayerView.setOnTouchListener(this);
            tvVolume = mExoPlayerView.findViewById(R.id.volume);
            layoutVolume = mExoPlayerView.findViewById(R.id.layout_volume);
            initFullscreenDialog();
            initFullscreenButton();

            String streamUrl = liData;
            String userAgent = Util.getUserAgent(VideoActivity.this, getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(VideoActivity.this, null, httpDataSourceFactory);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Uri daUri = Uri.parse(streamUrl);

            mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory, null, null);
        }
        else {
            String streamUrl = liData;
            String userAgent = Util.getUserAgent(VideoActivity.this, getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(VideoActivity.this, null, httpDataSourceFactory);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Uri daUri = Uri.parse(streamUrl);

            mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory, null, null);
        }
        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(VideoActivity.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }
    @Override
    protected void onPause() {

        super.onPause();
        pause();

    }
    private void pause(){
        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    private void setButton(){
        btnBack = findViewById(R.id.btn_back);
        btnComment = findViewById(R.id.btn_comment);
        btnSave = findViewById(R.id.btn_save);
        btnShare = findViewById(R.id.btn_share);

        btnShare.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                break;
        }
    }
    public void lockSlide(View v) {
        slidrInterface.lock();
    }

    public void unlockSlide(View v) {
        slidrInterface.unlock();
    }

    @Override
    public void clickItem(int position, Video video) {
        pause();
        mResumePosition = 0;
        setData(video.getLinkVideo());
        videoAdapter.removeItem(position);
        videoAdapter.restoreItem(this.video,this.poisition);
        this.video = video;
        this.poisition = position;

    }

    @Override
    public void setData(ArrayList<Video> listVideo) {
        if(listVideo.size()>0) {
            video = listVideo.get(poisition);
            listVideo.remove(poisition);
        }
        videoAdapter = new VideoAdapter(getApplicationContext(),this,listVideo);
        recyclerView.setAdapter(videoAdapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()==R.id.exoplayer){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                x = event.getX();
                y = event.getY();

                width = display.getWidth();
               // volumeCurrent =  mExoPlayerView.getPlayer().getVolume();
                volumeCurrent = audioManager.getStreamVolume(mExoPlayerView.getPlayer().getAudioStreamType());
                tvVolume.setText(volumeCurrent+"");
                Log.d(TAG, "onTouch: down"+volumeCurrent);
                if(y>300){
                    volumeY = 2;
                }else {
                    volumeY = (y-50) / (100 - volumeCurrent);
                }
            }
            if(action == MotionEvent.ACTION_UP){
                Log.d(TAG, "onTouch: up");
                if(isClick){
                    mExoPlayerView.showController();
                }else {
                   // mExoPlayerView.getPlayer().setVolume(volume);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutVolume.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                }

            }
            if(action ==MotionEvent.ACTION_MOVE){

                mX = (event.getX()-x);
                mY = (event.getY()-y);
                Log.d(TAG, "onTouch: move" +event.getY());
                if(mX>=10||mX<= -10||mY>=10||mY<= -10){
                    if(isClick) layoutVolume.setVisibility(View.VISIBLE);
                    isClick = false;
                }else {
                    isClick = true;
                }
                if(x<(width/2)){

                }else {
                    volume = (int)( volumeCurrent + (y-event.getY())/volumeY);
                    volume = (volume>100)?100:volume;
                    volume = (volume<0)?0:volume;
                    tvVolume.setText(volume+"");
                   // mExoPlayerView.getPlayer().setVolume(volume);
                    audioManager.setStreamVolume(mExoPlayerView.getPlayer().getAudioStreamType(),volume,0);
                }

               

            }
            return true;
        }
        return true;
    }
}
