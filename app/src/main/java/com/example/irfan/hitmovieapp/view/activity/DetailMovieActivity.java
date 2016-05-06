package com.example.irfan.hitmovieapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.controller.MovieService;
import com.example.irfan.hitmovieapp.model.MovieDao;
import com.example.irfan.hitmovieapp.model.TrailerDao;
import com.example.irfan.hitmovieapp.model.VideoDao;
import com.example.irfan.hitmovieapp.util.Constant;
import com.example.irfan.hitmovieapp.view.adapter.VideoAdapter;
import com.example.irfan.hitmovieapp.widget.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @BindView(R.id.rel_container_img)
    RelativeLayout mRelContainer;

    public static final String EXTRA_IMAGE = "DetailActivity:image";
    public static final String EXTRA_BACKDROP = "url_backdrop";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_VOTE = "vote";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String DATA_MODEL = "model";
    public static final String DATA_POS = "position";

    private String mUrlCover, mUrlBackdrop, mTitle, mReleaseDate, mVote, mDescription, mId;
    private MovieDao mMovieItem;
    private List<TrailerDao> mTrailesData = new ArrayList<>();
    private List<TrailerDao.Trailers.Youtube> mDataYoutube = new ArrayList<>();
    private VideoAdapter mAdapter;
    private int mHeight=0;
    private int mResult=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        /**
         * Setting for default backdrop image
         */
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        mHeight = mDisplayMetrics.heightPixels;
        mResult = (int) (mHeight * 0.3165);
        mRelContainer.getLayoutParams().height = mResult;
        mImgBackdrop.setImageResource(R.mipmap.ic_pics);

        /**
         * Get data model from intent
         */
        mUrlCover = getIntent().getStringExtra(EXTRA_IMAGE);
        mUrlBackdrop = getIntent().getStringExtra(EXTRA_BACKDROP);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mReleaseDate = getIntent().getStringExtra(EXTRA_DATE);
        mVote = getIntent().getStringExtra(EXTRA_VOTE);
        mDescription = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        mMovieItem = (MovieDao) getIntent().getSerializableExtra(DATA_MODEL);
        initDetail(mMovieItem, mUrlCover, mUrlBackdrop,
                mTitle, mReleaseDate, mVote, mDescription, String.valueOf(mMovieItem.getId()));

        /**
         * Set smooth when scroll
         */
        mNestedScroll.setSmoothScrollingEnabled(true);
    }

    public void initToolbar(String mTitle){
        if (mToolbar!=null){
            setSupportActionBar(mToolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initDetail(MovieDao mItem, String mUrlCover,
                           String mUrlBackdrop, String mTitle,
                           String mReleaseDate, String mVote,
                           String mDescription, String mId){
        /**
         * Init view
         */
        initToolbar(mItem.title);
        initListWidget();

        /**
         * Set collapsing
         */
        mCollapsingToolbar.setTitle(mItem.getTitle());
        mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        mCollapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        mCollapsingToolbar.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_source_sans_reg)));
        mCollapsingToolbar.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_source_sans_reg)));

        /**
         * Set view items in detail page
         */
        ViewCompat.setTransitionName(mImgCover, EXTRA_IMAGE);
        ViewCompat.setTransitionName(mImgBackdrop, EXTRA_BACKDROP);
        ViewCompat.setTransitionName(mTxtTitle, EXTRA_TITLE);
        ViewCompat.setTransitionName(mTxtReleaseDate, EXTRA_DATE);
        ViewCompat.setTransitionName(mTxtVote, EXTRA_VOTE);
        ViewCompat.setTransitionName(mTxtDescription, EXTRA_DESCRIPTION);
        Picasso.with(DetailMovieActivity.this).load(Constant.BASE_POSTER_URL + mUrlCover).into(mImgCover);
        Picasso.with(DetailMovieActivity.this).load(Constant.BASE_BACKDROP_URL + mUrlBackdrop).into(mImgBackdrop);
        mTxtTitle.setText(mTitle);
        mTxtReleaseDate.setText(mReleaseDate);
        mTxtVote.setText(mVote);
        mTxtDescription.setText(mDescription);

        /**
         * Set trailer video
         */
        initVideo(mId);

        /**
         * Set comment video
         */
//        initComment();
    }

    public void initListWidget(){
        //        mLayoutManager.setAutoMeasureEnabled(true);
        mRecVideo.setLayoutManager(new LinearLayoutManager(DetailMovieActivity.this));
        mRecVideo.setHasFixedSize(true);
        mRecVideo.setNestedScrollingEnabled(false);
        mRecVideo.setItemAnimator(new DefaultItemAnimator());
    }

    public void initVideo(String id){
        Log.e("MY ID ", id);
        Retrofit mRequest = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService mService = mRequest.create(MovieService.class);
        Call<TrailerDao> mCall = mService.callTrailersMovie(id);
        mCall.enqueue(new Callback<TrailerDao>() {
            @Override
            public void onResponse(Call<TrailerDao> call, Response<TrailerDao> response) {
                for (int i=0;i<response.body().getTrailers().getYoutube().size(); i++){
                    Log.e("YOUTUBE ", response.body().getTrailers().getYoutube().get(i).getSource());
                }
                mDataYoutube.clear();
                mDataYoutube.addAll(response.body().getTrailers().getYoutube());
                mAdapter = new VideoAdapter(DetailMovieActivity.this, mDataYoutube);
                mRecVideo.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailerDao> call, Throwable t) {
                Log.e("ERROR EY ", t.getMessage());
            }
        });
    }

    public void initComment(){

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setAutoMeasureEnabled(true);
        mRecComment.setLayoutManager(mLayoutManager);
        mRecVideo.setHasFixedSize(true);
        mRecComment.setNestedScrollingEnabled(false);
        mRecComment.setItemAnimator(new DefaultItemAnimator());
//        mAdapter = new VideoAdapter(DetailMovieActivity.this, mVideoItem);
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
