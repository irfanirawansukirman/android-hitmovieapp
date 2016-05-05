package com.example.irfan.hitmovieapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.model.VideoDao;
import com.example.irfan.hitmovieapp.widget.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irfan on 05/05/16.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context mContext;
    private List<VideoDao> mData = new ArrayList<>();
    private VideoDao mItem;

    public VideoAdapter(Context mContext, List<VideoDao> mData){
        this.mContext=mContext;
        this.mData=mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video, parent, false);
        MyViewHolder mHolder = new MyViewHolder(mView);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mItem = mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mytxt_detail_video_title)
        CustomTextView mTxtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
