package com.vuukle.comment_library.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.vuukle.comments.vuuklecommentlibrary.R;

public class TopArticleViewHolder extends RecyclerView.ViewHolder {
    ImageView articleImg;

    public TopArticleViewHolder(View itemView) {
        super(itemView);

        articleImg = (ImageView) itemView.findViewById(R.id.article_top_iv);
        articleImg.setOnClickListener(v -> Log.d("Mylog","MMMM"));
    }
}
