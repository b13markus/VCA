package com.vuukle.comment_library.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.vuukle.comment_library.models.User;
import com.vuukle.comment_library.network.ApiService;
import com.vuukle.comment_library.network.CancelableCallback;
import com.vuukle.comments.vuuklecommentlibrary.R;

import retrofit2.Call;
import retrofit2.Response;

import static com.vuukle.comment_library.adapter.CommentsAdapter.API_KEY;
import static com.vuukle.comment_library.adapter.CommentsAdapter.ARTICLE_ID;
import static com.vuukle.comment_library.adapter.CommentsAdapter.HOST;
import static com.vuukle.comment_library.adapter.CommentsAdapter.SECRET_KEY;
import static com.vuukle.comment_library.adapter.CommentsAdapter.TAGS;
import static com.vuukle.comment_library.adapter.CommentsAdapter.TIME_ZONE;
import static com.vuukle.comment_library.adapter.CommentsAdapter.TITLE;
import static com.vuukle.comment_library.adapter.CommentsAdapter.ARTICLE_URL;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    EditText writeComment, userName, userEmail;
    TextView totalCount, welcomeTv;
    Button postComment, logout;
    PostCommentCallback mPostCommentCallback;
    Context mContext;

    public HeaderViewHolder(View itemView, PostCommentCallback postCommentCallback) {
        super(itemView);
        mContext = itemView.getContext();
        mPostCommentCallback = postCommentCallback;
        writeComment = ((EditText) itemView.findViewById(R.id.header_write_a_comment));
        writeComment.setScroller(new Scroller(itemView.getContext()));
        writeComment.setVerticalScrollBarEnabled(true);
        writeComment.setMovementMethod(new ScrollingMovementMethod());
        userName = ((EditText) itemView.findViewById(R.id.header_user_name));
        userEmail = ((EditText) itemView.findViewById(R.id.header_user_email));
        totalCount = ((TextView) itemView.findViewById(R.id.total_comment));
        postComment = ((Button) itemView.findViewById(R.id.header_post_comment));
        logout = (Button) itemView.findViewById(R.id.logout);
        welcomeTv = (TextView) itemView.findViewById(R.id.welcome_tv);
        logout.setOnClickListener(v -> logout(v));
        postComment.setOnClickListener(v -> {
            v.setEnabled(false);
            postComment(userEmail.getText().toString(), userName.getText().toString(), writeComment.getText().toString(), v);
            writeComment.setText("");
        });
    }

    private void logout(View v) {
        v.setVisibility(View.GONE);
        userName.setVisibility(View.VISIBLE);
        userEmail.setVisibility(View.VISIBLE);
        userName.setText("");
        userEmail.setText("");
        welcomeTv.setVisibility(View.GONE);
        User.setUser((Activity) mContext, "", "");
    }

    private void postComment(String email, String name, String comment, View v) {
        if (dataIsValid(email, name, comment)) {
            hideKeyboard(v);
            ApiService.postComment(name, email, comment, new CancelableCallback() {
                @Override
                public void onResponse(Call call, Response response) {
                    mPostCommentCallback.onCommentPost(name, email, comment);

                    writeComment.setText(R.string.empty_field);
                    v.setEnabled(true);
                    logout.setVisibility(View.VISIBLE);
                    userName.setVisibility(View.GONE);
                    userEmail.setVisibility(View.GONE);
                    welcomeTv.setVisibility(View.VISIBLE);
                    welcomeTv.setText(mContext.getString(R.string.welcome_user) + User.getUserName((Activity) mContext));
                    Toast.makeText(mContext, mContext.getString(R.string.comment_posted), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();
                    v.setEnabled(true);
                }
            }, API_KEY, SECRET_KEY, TIME_ZONE, HOST, ARTICLE_URL, ARTICLE_ID, TAGS, TITLE);
        } else {
            showError();
            v.setEnabled(true);
        }
    }

    private void showError() {
        if (!emailIsValid(userEmail.getText().toString())) {
            userEmail.setError(mContext.getString(R.string.invalid_email));
        }
        if (TextUtils.isEmpty(userName.getText())) {
            userName.setError(mContext.getString(R.string.field_is_empty));
        }
        if (TextUtils.isEmpty(writeComment.getText())) {
            writeComment.setError(mContext.getString(R.string.field_is_empty));
        }
    }

    private boolean emailIsValid(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private boolean dataIsValid(String userEmail, String userName, String commentMessage) {
        return emailIsValid(userEmail) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(commentMessage);
    }
}