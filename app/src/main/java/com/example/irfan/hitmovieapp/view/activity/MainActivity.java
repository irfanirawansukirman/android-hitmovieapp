package com.example.irfan.hitmovieapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

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

    private MovieAdapter mAdapter;
    private List<MovieDao> mData = new ArrayList<>();
    private MovieDao mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
                mItem = mData.get(position);
                Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.DATA_POS, position);
                intent.putExtra(DetailMovieActivity.DATA_MODEL, mItem);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
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
                    mRecyclerView.setVisibility(View.VISIBLE);
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
