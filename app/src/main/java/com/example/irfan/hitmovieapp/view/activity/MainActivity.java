package com.example.irfan.hitmovieapp.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.controller.MovieService;
import com.example.irfan.hitmovieapp.model.BaseDao;
import com.example.irfan.hitmovieapp.model.MovieDao;
import com.example.irfan.hitmovieapp.util.Constant;
import com.example.irfan.hitmovieapp.util.RecyclerViewItemClickListener;
import com.example.irfan.hitmovieapp.view.adapter.MovieAdapter;
import com.lusfold.spinnerloading.SpinnerLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.myrec)
    RecyclerView mRecyclerView;
    @BindView(R.id.myprogress)
    SpinnerLoading mProgress;
    @BindView(R.id.fl_progress)
    FrameLayout mFrmContainer;
    @BindView(R.id.mytoolbar_home)
    Toolbar mToolbar;
    @BindView(R.id.lin_header)
    LinearLayout mLinearHeader;
    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_main)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;
    private MovieAdapter mAdapter;
    private List<MovieDao> mData = new ArrayList<>();
    private MovieDao mItem;
    private int mHeight =0;
    private int mResult=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**
         * Setting for navigation header
         */
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        mHeight = mDisplayMetrics.heightPixels;
        mResult = (int) (mHeight * 0.3);
        mLinearHeader.getLayoutParams().height = mResult;

        /**
         * init toolbar
         */
        initToolbar();

        /**
         * get list the movie data
         */
        loadData();

        /**
         * config for circle progress
         */
        mProgress.setPaintMode(1);
        mProgress.setCircleRadius(8);
        mProgress.setItemCount(8);

        /**
         * listener for get data in recyclerview
         */
        mRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(MainActivity.this, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String mUrlCover, mUrlBackdrop, mTitle, mReleaseDate, mVote, mDescription;
                mItem = mData.get(position);
                mUrlCover = mItem.getPoster_path();
                mUrlBackdrop = mItem.getBackdrop_path();
                mTitle = mItem.getTitle();
                mReleaseDate = mItem.getRelease_date();
                mVote = String.valueOf(mItem.getVote_average());
                mDescription = mItem.getOverview();

                /**
                 * intent the data
                 */
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        view, DetailMovieActivity.EXTRA_IMAGE);
                Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.DATA_POS, position);
                intent.putExtra(DetailMovieActivity.DATA_MODEL, mItem);
                intent.putExtra(DetailMovieActivity.EXTRA_IMAGE, mUrlCover);
                intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP, mUrlBackdrop);
                intent.putExtra(DetailMovieActivity.EXTRA_TITLE, mTitle);
                intent.putExtra(DetailMovieActivity.EXTRA_DATE, mReleaseDate);
                intent.putExtra(DetailMovieActivity.EXTRA_VOTE, mVote);
                intent.putExtra(DetailMovieActivity.EXTRA_DESCRIPTION, mDescription);
                ActivityCompat.startActivity(MainActivity.this, intent, option.toBundle());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    public void initToolbar(){
        if (mToolbar!=null){
            setSupportActionBar(mToolbar);

            /**
             * Navigation default
             */
//            mToolbar.setNavigationIcon(R.mipmap.ic_menu);
//            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDrawerLayout.openDrawer(GravityCompat.START);
//                }
//            });

            /**
             * Navigation with animation
             */
            mDrawerToggle = setupDrawerToggle();
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    public void loadData(){
        try{
            Retrofit mRequest = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieService mService = mRequest.create(MovieService.class);
            Call<BaseDao<List<MovieDao>>> mCall = mService.callMoviePopular();
            mCall.enqueue(new Callback<BaseDao<List<MovieDao>>>() {
                @Override
                public void onResponse(Call<BaseDao<List<MovieDao>>> call, Response<BaseDao<List<MovieDao>>> response) {
                    mData.clear();
                    mData.addAll(response.body().getResults());
                    initList(mData);
                    mFrmContainer.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<BaseDao<List<MovieDao>>> call, Throwable t) {
                    Log.e("ERROR EY ", t.getMessage());
                }
            });
        } catch (Exception e){
            e.getMessage();
        }
    }

    public void loadDataVote(){
        try{
            Retrofit mRequest = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieService mService = mRequest.create(MovieService.class);
            Call<BaseDao<List<MovieDao>>> mCall = mService.callMovieAverage();
            mCall.enqueue(new Callback<BaseDao<List<MovieDao>>>() {
                @Override
                public void onResponse(Call<BaseDao<List<MovieDao>>> call, Response<BaseDao<List<MovieDao>>> response) {
                    mData.clear();
                    mData.addAll(response.body().getResults());
                    initList(mData);
                    mFrmContainer.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<BaseDao<List<MovieDao>>> call, Throwable t) {
                    Log.e("ERROR EY ", t.getMessage());
                }
            });
        } catch (Exception e){
            e.getMessage();
        }
    }

    public void initList(List<MovieDao> mData){
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mAdapter = new MovieAdapter(MainActivity.this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_popular:
                mFrmContainer.setVisibility(View.VISIBLE);
                loadData();
                return true;
            case R.id.action_vote:
                mFrmContainer.setVisibility(View.VISIBLE);
                loadDataVote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
