package com.vuukle.comment_library.adapter;


import android.view.View;

public interface ShowHideReplyCallback {

     void onShow(View view, int level );

     void onHide(View view, int level );

}
