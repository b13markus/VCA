package com.vuukle.comment_library.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.vuukle.comment_library.models.TopArticleModel;
import com.vuukle.comments.vuuklecommentlibrary.R;

class TopArticleViewHolder extends RecyclerView.ViewHolder {
    ImageView articleImg;
    TopArticleModel topArticle;

    public TopArticleViewHolder(View itemView) {
        super(itemView);

        articleImg = (ImageView) itemView.findViewById(R.id.article_top_iv);

        View.OnClickListener onClickListener = v -> {
            int i = v.getId();

            if (i == R.id.article_top_iv) {
                Log.d("Myyy", topArticle.getImg());
            }
        };
        articleImg.setOnClickListener(onClickListener);
    }
}
