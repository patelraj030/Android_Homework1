package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rkjc.news_app_2.model.NewsItemResponse;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    private Context mContext;
    private ArrayList<NewsItemResponse.NewsItem> mNewsItemArrayList;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItemResponse.NewsItem> newsItemArrayList) {
        mContext = context;
        mNewsItemArrayList = newsItemArrayList;
    }


    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsItemViewHolder holder, int position) {
        final NewsItemResponse.NewsItem loNewsItem = mNewsItemArrayList.get(position);
        holder.mTvDate.setText("Date: " + loNewsItem.getPublishedAt());
        holder.mTvDescription.setText("Description: " + loNewsItem.getDescription());
        holder.mTvTitle.setText("Title: " + loNewsItem.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = loNewsItem.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsItemArrayList.size();
    }

    public static class NewsItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        TextView mTvDescription;
        TextView mTvDate;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.title);
            mTvDescription = itemView.findViewById(R.id.description);
            mTvDate = itemView.findViewById(R.id.date);
        }
    }
}
