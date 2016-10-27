package com.vuukle.comment_library.adapter;

import com.vuukle.comment_library.models.ReplyModel;

public interface PostReplyCallback {
    void onReplyPost(ReplyModel replyModel, int position);
}
