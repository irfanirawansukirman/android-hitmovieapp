package com.example.irfan.hitmovieapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.model.TrailerDao;
import com.example.irfan.hitmovieapp.model.VideoDao;
import com.example.irfan.hitmovieapp.util.Constant;
import com.example.irfan.hitmovieapp.widget.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irfan on 05/05/16.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context mContext;
    private List<TrailerDao.Trailers.Youtube> mData = new ArrayList<>();
    private TrailerDao.Trailers.Youtube mItem;

    public VideoAdapter(Context mContext, List<TrailerDao.Trailers.Youtube> mData){
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

        Picasso.with(holder.mImgTrailer.getContext()).load(Constant.BASE_YOUTUBE_IMAGE_URL + mItem.getSource() + Constant.BASE_YOUTUBE_QUALITY).into(holder.mImgTrailer);
        holder.mTxtTitle.setText(mItem.getName());
        holder.mLinItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BASE_YOUTUBE_VIDEO_URL + mItem.getSource())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mytxt_detail_video_title)
        CustomTextView mTxtTitle;
        @BindView(R.id.img_detail_trailer)
        ImageView mImgTrailer;
        @BindView(R.id.lin_row_video)
        LinearLayout mLinItemContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
