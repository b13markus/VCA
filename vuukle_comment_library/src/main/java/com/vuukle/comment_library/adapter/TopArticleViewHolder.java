package com.vuukle.comment_library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.vuukle.comment_library.models.CommentFeedModel;
import com.vuukle.comment_library.models.TopArticleModel;
import com.vuukle.comment_library.network.ApiService;
import com.vuukle.comment_library.network.CancelableCallback;
import com.vuukle.comments.vuuklecommentlibrary.R;

import retrofit2.Call;
import retrofit2.Response;

class TopArticleViewHolder extends RecyclerView.ViewHolder {
    ImageView articleImg;
    TopArticleModel topArticle;

    Context mContext;
    TopArticleCallback articleCallback;

    public TopArticleViewHolder(View itemView, TopArticleCallback callback) {
        super(itemView);
        mContext = itemView.getContext();
        articleCallback = callback;
        articleImg = (ImageView) itemView.findViewById(R.id.article_top_iv);

        View.OnClickListener onClickListener = v -> {
            int i = v.getId();

            if (i == R.id.article_top_iv) {
                loadArticle();

            }
        };
        articleImg.setOnClickListener(onClickListener);
    }

    private void loadArticle() {
        ApiService.getAllComments(0, 100, new CancelableCallback() {
            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
            }

            @Override
            public void onResponse(Call call, Response response) {
                CommentFeedModel feedModel = (CommentFeedModel) response.body();
                feedModel.getComments();
                articleCallback.topArticleShow(topArticle, feedModel);
            }
        },topArticle.getApiKey(),"07115720-6848-11e5-9bc9-002590f371ee",topArticle.getTs(),"00048",topArticle.getHost());
    }
}
