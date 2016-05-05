package com.example.irfan.hitmovieapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.model.MovieDao;
import com.example.irfan.hitmovieapp.model.VideoDao;
import com.example.irfan.hitmovieapp.util.Constant;
import com.example.irfan.hitmovieapp.view.adapter.VideoAdapter;
import com.example.irfan.hitmovieapp.widget.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.myimg_backdrop)
    ImageView mImgBackdrop;
    @BindView(R.id.myimg_detail_cover)
    ImageView mImgCover;
    @BindView(R.id.mytxt_detail_title)
    CustomTextView mTxtTitle;
    @BindView(R.id.mytxt_detail_release_date)
    CustomTextView mTxtReleaseDate;
    @BindView(R.id.mytxt_detail_vote)
    CustomTextView mTxtVote;
    @BindView(R.id.mytxt_detail_description)
    CustomTextView mTxtDescription;
    @BindView(R.id.mytoolbar_detail_movie)
    Toolbar mToolbar;
    @BindView(R.id.myrec_detail_video)
    RecyclerView mRecVideo;
    @BindView(R.id.myrec_detail_comment)
    RecyclerView mRecComment;
    @BindView(R.id.mycollapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.mynested)
    NestedScrollView mNestedScroll;

    public static final String DATA_MODEL = "model";
    public static final String DATA_POS = "position";

    private MovieDao mMovieItem;
    private List<VideoDao> mVideoItem;
    private VideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        /**
         * Get data model from intent
         */
        mMovieItem = (MovieDao) getIntent().getSerializableExtra(DATA_MODEL);
        initDetail(mMovieItem);

        /**
         * Set smooth when scroll
         */
        mNestedScroll.setSmoothScrollingEnabled(true);
    }

    public void initToolbar(String mTitle){
        if (mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initDetail(MovieDao mItem){
        /**
         * Set toolbar title
         */
        initToolbar(mItem.title);
        mCollapsingToolbar.setTitle(mItem.getTitle());

        Picasso.with(DetailMovieActivity.this).load(Constant.BASE_POSTER_URL + mItem.getPoster_path()).into(mImgCover);
        Picasso.with(DetailMovieActivity.this).load(Constant.BASE_BACKDROP_URL + mItem.getBackdrop_path()).placeholder(R.mipmap.ic_launcher).into(mImgBackdrop);
        mTxtTitle.setText(mItem.getTitle());
        mTxtReleaseDate.setText(mItem.getRelease_date());
        mTxtVote.setText(String.valueOf(mItem.getVote_average()));
        mTxtDescription.setText(mItem.getOverview());

        /**
         * Set trailer video
         */
        initVideo();

        /**
         * Set comment video
         */
        initComment();
    }

    public void initVideo(){
        mVideoItem = new ArrayList<>();
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setAutoMeasureEnabled(true);
        mRecVideo.setLayoutManager(mLayoutManager);
        mRecVideo.setHasFixedSize(true);
        mRecVideo.setNestedScrollingEnabled(false);
        mRecVideo.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new VideoAdapter(DetailMovieActivity.this, mVideoItem);
        mRecVideo.setAdapter(mAdapter);
    }

    public void initComment(){
        mVideoItem = new ArrayList<>();
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));
        mVideoItem.add(new VideoDao("Lorem Ipsum"));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setAutoMeasureEnabled(true);
        mRecComment.setLayoutManager(mLayoutManager);
        mRecVideo.setHasFixedSize(true);
        mRecComment.setNestedScrollingEnabled(false);
        mRecComment.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new VideoAdapter(DetailMovieActivity.this, mVideoItem);
        mRecComment.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
//            case R.id.action_settings:
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
