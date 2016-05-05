package com.example.irfan.hitmovieapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.model.MovieDao;
import com.example.irfan.hitmovieapp.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by irfan on 05/05/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context mContext;
    private List<MovieDao> mData = new ArrayList<>();
    private MovieDao mItem;

    public MovieAdapter(Context mContext, List<MovieDao> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie, parent, false);
        MyViewHolder mHolder = new MyViewHolder(mView);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        mItem = mData.get(position);
        Log.e("POSTER ", mItem.getPoster_path());
        Picasso.with(holder.mImgMovie.getContext()).load(Constant.BASE_POSTER_URL + mItem.getPoster_path()).into(holder.mImgMovie);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_movie)
        ImageView mImgMovie;

        public View mItemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mItemView=itemView;
        }
    }
}
