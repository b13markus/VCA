package com.vuukle.comment_library.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class VuukleLogoHolder extends RecyclerView.ViewHolder  {


    public VuukleLogoHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vuukle.com/"));
            itemView.getContext().startActivity(browserIntent);
        });
    }

}
