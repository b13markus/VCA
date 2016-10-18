package com.vuukle.comment_library.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.vuukle.comment_library.models.TopArticleModel;
import com.vuukle.comments.vuuklecommentlibrary.R;

import java.util.ArrayList;

public class TopArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TopArticleModel> imagesUrl;

    public TopArticleAdapter(Activity context, ArrayList<TopArticleModel> images) {
        this.mContext = context;
        this.imagesUrl = images;
    }

    private void addTopArticle(TopArticleModel url) {
        imagesUrl.add(url);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_article_item,parent,false);
        return  new TopArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        fillTopArticle((TopArticleViewHolder) holder,position);
    }

    @Override
    public int getItemCount() {
        return imagesUrl == null ? 0 : imagesUrl.size();
    }

    private void fillTopArticle (TopArticleViewHolder holder, int position) {
        Picasso.with(mContext).load(imagesUrl.get(position).getImg()).into(holder.articleImg);
    }
}
