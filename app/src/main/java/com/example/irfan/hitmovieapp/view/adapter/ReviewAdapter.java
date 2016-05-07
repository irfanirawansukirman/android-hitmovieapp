package com.example.irfan.hitmovieapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irfan.hitmovieapp.R;
import com.example.irfan.hitmovieapp.model.ReviewDao;
import com.example.irfan.hitmovieapp.model.TrailerDao;
import com.example.irfan.hitmovieapp.widget.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irfan on 07/05/16.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{
    private Context mContext;
    private List<ReviewDao.Results> mData = new ArrayList<>();
    private ReviewDao.Results mItem;

    public ReviewAdapter(Context mContext, List<ReviewDao.Results> mData){
        this.mContext=mContext;
        this.mData=mData;
    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent, false);
        MyViewHolder mHolder = new MyViewHolder(mView);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.MyViewHolder holder, int position) {
        mItem = mData.get(position);

        holder.mTxtAuthor.setText(mItem.getAuthor());
        holder.mTxtReview.setText(mItem.getContent());
        holder.mTxtUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mItem.getUrl())));
            }
        });

        if (position == mData.size() - 1){
            holder.mDecoration.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mytxt_detail_author)
        CustomTextView mTxtAuthor;
        @BindView(R.id.mytxt_detail_review)
        CustomTextView mTxtReview;
        @BindView(R.id.mytxt_detail_url)
        CustomTextView mTxtUrl;
        @BindView(R.id.view_review)
        View mDecoration;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
