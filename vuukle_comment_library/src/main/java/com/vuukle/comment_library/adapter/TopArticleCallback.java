package com.vuukle.comment_library.adapter;

import com.vuukle.comment_library.models.CommentFeedModel;
import com.vuukle.comment_library.models.TopArticleModel;

public interface TopArticleCallback {
    void topArticleShow(TopArticleModel articleModel, CommentFeedModel commentFeedModel);
}
