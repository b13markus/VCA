package com.vuukle.comment_library.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.vuukle.comments.vuuklecommentlibrary.R;



public class LoadMoreHolder extends RecyclerView.ViewHolder  {

    public Button loadMoreButton;
    public ProgressBar progressBar;
    public LoadMoreHolder(View itemView) {
        super(itemView);
        loadMoreButton = (Button) itemView.findViewById(R.id.load_more_button);
        progressBar = (ProgressBar) itemView.findViewById(R.id.load_more_progress);
        progressBar.setVisibility(View.INVISIBLE);
        loadMoreButton.setVisibility(View.VISIBLE);
    }
}
