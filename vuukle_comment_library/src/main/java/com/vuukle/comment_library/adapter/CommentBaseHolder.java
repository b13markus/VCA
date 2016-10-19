package com.vuukle.comment_library.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vuukle.comment_library.models.BaseCommentModel;
import com.vuukle.comment_library.models.User;
import com.vuukle.comment_library.network.ApiService;
import com.vuukle.comment_library.network.CancelableCallback;
import com.vuukle.comment_library.utils.SharedPreferenceUtils;
import com.vuukle.comments.vuuklecommentlibrary.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.vuukle.comment_library.adapter.CommentsAdapter.API_KEY;
import static com.vuukle.comment_library.adapter.CommentsAdapter.ARTICLE_ID;
import static com.vuukle.comment_library.adapter.CommentsAdapter.HOST;
import static com.vuukle.comment_library.adapter.CommentsAdapter.SECRET_KEY;


class CommentBaseHolder extends RecyclerView.ViewHolder {

    TextView userName, message, timestamp, upVotes, downVotes, initialImage, userPoints;
    EditText replyUserName, replyEmail, replyMessage;
    TextView replyWelcomeTv;
    Button replyPost;
    private RelativeLayout loadMore;
    ImageView avatar, reportButton, like, dislike, shareTwitter, shareFacebook;
    String commentId;
    LinearLayout showReplyes;
    BaseCommentModel commentModel;
    public Activity context;

    private View.OnClickListener onClickListener = v -> {
        int i = v.getId();


        if (i == R.id.like || i == R.id.dislike) {
            likeOrDislikeComment(v.getId());
            return;
        }

        if (i == R.id.report_menu) {
            showReportMenu(v);
            return;
        }

        if (i == R.id.comment_facebook_share) {
            shareWithFacebook();
            return;
        }

        if (i == R.id.comment_twitter_share) {
            shareWithTwitter();
        }

        if (i == R.id.show_replyes) {
            if (User.isUserLogedIn(context)) {
                replyEmail.setVisibility(View.GONE);
                replyUserName.setVisibility(View.GONE);
                replyWelcomeTv.setVisibility(View.VISIBLE);
                replyWelcomeTv.setText(context.getString(R.string.welcome_user) + User.getUserName(context));
            } else {
                replyEmail.setVisibility(View.VISIBLE);
                replyUserName.setVisibility(View.VISIBLE);
                replyWelcomeTv.setVisibility(View.GONE);
            }
        }
    };

    private void shareWithTwitter() {
        String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                urlEncode(message.getText().toString()),
                urlEncode(CommentsAdapter.ARTICLE_URL));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }

        context.startActivity(intent);
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    private void shareWithFacebook() {
        String urlToShare = CommentsAdapter.ARTICLE_URL;

        try {
            Intent intent1 = new Intent();
            intent1.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("text/plain");
            intent1.putExtra("android.intent.extra.TEXT", urlToShare);
            context.startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through SEND
            Intent intent = new Intent(Intent.ACTION_SEND);
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            context.startActivity(intent);
        }
    }


    private final View.OnClickListener reportMenuListener = this::showReportMenu;

    public CommentBaseHolder(View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.user_name);
        message = (TextView) itemView.findViewById(R.id.message);
        timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        initialImage = (TextView) itemView.findViewById(R.id.picture);
        showReplyes = (LinearLayout) itemView.findViewById(R.id.show_replyes);
        replyUserName = (EditText) itemView.findViewById(R.id.reply_name);
        replyEmail = (EditText) itemView.findViewById(R.id.reply_email);
        replyMessage = (EditText) itemView.findViewById(R.id.reply_message);
        replyPost = (Button) itemView.findViewById(R.id.reply_post);
        loadMore = (RelativeLayout) itemView.findViewById(R.id.load_more);
        upVotes = (TextView) itemView.findViewById(R.id.like_count);
        downVotes = (TextView) itemView.findViewById(R.id.dislike_count);
        avatar = (ImageView) itemView.findViewById(R.id.user_image);
        like = (ImageView) itemView.findViewById(R.id.like);
        userPoints = (TextView) itemView.findViewById(R.id.user_rating);
        dislike = (ImageView) itemView.findViewById(R.id.dislike);
        reportButton = (ImageView) itemView.findViewById(R.id.report_menu);
        shareFacebook = (ImageView) itemView.findViewById(R.id.comment_facebook_share);
        shareFacebook.setOnClickListener(onClickListener);
        shareTwitter = (ImageView) itemView.findViewById(R.id.comment_twitter_share);
        replyWelcomeTv = (TextView) itemView.findViewById(R.id.reply_welcome_tv);
        shareTwitter.setOnClickListener(onClickListener);
        reportButton.setOnClickListener(reportMenuListener);
        like.setOnClickListener(onClickListener);
        dislike.setOnClickListener(onClickListener);
        showReplyes.setOnClickListener(onClickListener);
    }

    public void setLikeOrDislike(final int viewId, final ArrayList<String> likeAndDislikesId) {
        ApiService.setVotes(context, viewId, commentId, new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse: ", "" + response.body());
                addLikeOrDislikeToPreferences(likeAndDislikesId, viewId);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("onFailure:", t.getMessage());
            }
        }, HOST, ARTICLE_ID, API_KEY, SECRET_KEY);
    }

    public void addLikeOrDislikeToPreferences(ArrayList<String> likeAndDislikesId, int viewId) {
        likeAndDislikesId.add(commentId);
        SharedPreferenceUtils.putListString(context, context.getString(R.string.like_and_dislike_id), likeAndDislikesId);
        like.setEnabled(true);
        dislike.setEnabled(true);
        if (viewId == R.id.like) {
            upVotes.setText("" + (commentModel.getUpVotes() + 1));
            upVotes.setVisibility(View.VISIBLE);
        } else {
            downVotes.setText("" + (commentModel.getDownVotes() + 1));
            downVotes.setVisibility(View.VISIBLE);
        }
    }

    public void likeOrDislikeComment(int viewId) {
        like.setEnabled(false);
        dislike.setEnabled(false);
        ArrayList<String> likeAndDislikesId = SharedPreferenceUtils.getListString(context, context.getString(R.string.like_and_dislike_id));
        if (likeAndDislikesId.contains(commentId)) {
            Toast.makeText(context, R.string.you_have_already_voted, Toast.LENGTH_SHORT).show();
            like.setEnabled(true);
            dislike.setEnabled(true);
        } else {
            setLikeOrDislike(viewId, likeAndDislikesId);
        }
    }

    private void showReportMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.report_menu);
        popupMenu.setOnMenuItemClickListener(item -> true);
        popupMenu.setOnDismissListener(menu -> Log.i("dismiss menu", "showPopupMenu: "));
        popupMenu.show();
    }
}
