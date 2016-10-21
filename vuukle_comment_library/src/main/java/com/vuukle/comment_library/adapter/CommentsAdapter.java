package com.vuukle.comment_library.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vuukle.comment_library.models.AdsBannerModel;
import com.vuukle.comment_library.models.ArticleWebViewModel;
import com.vuukle.comment_library.models.BaseCommentModel;
import com.vuukle.comment_library.models.CommentFeedModel;
import com.vuukle.comment_library.models.CommentModel;
import com.vuukle.comment_library.models.EmotesModel;
import com.vuukle.comment_library.models.HeaderModel;
import com.vuukle.comment_library.models.LoadMoreModel;
import com.vuukle.comment_library.models.ReplyModel;
import com.vuukle.comment_library.models.TopArticleModel;
import com.vuukle.comment_library.models.User;
import com.vuukle.comment_library.models.VuukleLogo;
import com.vuukle.comment_library.network.ApiService;
import com.vuukle.comment_library.network.CancelableCallback;
import com.vuukle.comment_library.utils.DateFormat;
import com.vuukle.comments.vuuklecommentlibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.vuukle.comments.vuuklecommentlibrary.R.string.comments;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMOTES = 0;
    private final int HEADER = 1;
    private final int COMMENT = 2;
    private final int REPLY = 3;
    private final int LOAD_MORE = 4;
    private final int REPLY_COUNT_EMPTY = 0;
    private final int VUUKLE_LOGO = 5;
    private final int ADS_BANNER = 6;
    private final int ARTICLE_WEBVIEW = 7;
    private final int TOP_ARTICLE = 8;
    private int ARTICLE_WEBVIEW_POSITION = 0;
    private int BANNER_POSITION = 0;
    static String ARTICLE_ID;
    public static String API_KEY;
    static String SECRET_KEY;
    static String TIME_ZONE;
    public static String HOST;
    static String ARTICLE_URL;
    static String TAGS;
    static String TITLE;
    private static int RESOURCE_ID;
    private static int PAGINATION_FROM;
    private static final String PLACEHOLDER_URL = "http://3aa0b40d2aab024f527d-510de3faeb1a65410c7c889a906ce44e.r42.cf6.rackcdn.com/avatar.png";
    private static int PAGINATION_TO;
    private static int PAGINATION_COUNT;
    private Activity mContext;
    private CommentBaseHolder mHolder;
    private ArrayList<Object> commentsWithReplies;
    private ReplyModel mReply;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static String COUNT_COMMENTS_KEY = "count_comments";
    private SharedPreferences sPrefs;
    private SharedPreferences.Editor mEditor;

    private int currentOpenPosition = -1;
    private boolean mIsEmoteVisible;
    private boolean mAddArticleWebView;
    private int HEADER_POSITION = 0;
    private boolean mAddAdsBanner = false;
    private boolean mAddTopArticle = false;


    private PostCommentCallback postCommentCallback = (name, email, comment) -> {
        addCommentToList(name, comment);
        saveUserToPreferences(email, name);
    };

    private TopArticleCallback topArticleCallback = (TopArticleModel model, CommentFeedModel commentFeedModel) -> {
        commentsWithReplies.clear();
        notifyDataSetChanged();
        ARTICLE_URL = model.getUrl();
        ARTICLE_ID = commentFeedModel.getArticleId();
        addArticleWebView();
        addAdsBanner();
        addEmotes();
        addHeader();
        fillRecyclerView(commentFeedModel, null);
        addTopArticle();
        notifyDataSetChanged();
    };

    public CommentsAdapter(Activity context,
                           int paginationFromCount,
                           int paginationToCount,
                           SwipeRefreshLayout swipeRefreshLayout,
                           String apiKey,
                           String secretKey,
                           String timeZone,
                           String host,
                           String articleUrl,
                           String articleId,
                           String tags,
                           String title,
                           boolean isEmoteVisible,
                           boolean addWebViewArticle,
                           boolean addADSBanner,
                           boolean addTopArticle) {

        mContext = context;
        API_KEY = apiKey;
        SECRET_KEY = secretKey;
        TIME_ZONE = timeZone;
        HOST = host;
        ARTICLE_URL = articleUrl;
        ARTICLE_ID = articleId;
        TAGS = tags;
        TITLE = title;
        commentsWithReplies = new ArrayList<>();
//        initSwipeToRefresh(swipeRefreshLayout);
        PAGINATION_FROM = paginationFromCount;
        PAGINATION_TO = paginationToCount;
        PAGINATION_COUNT = paginationToCount;
        mIsEmoteVisible = isEmoteVisible;
        mAddArticleWebView = addWebViewArticle;
        mAddAdsBanner = addADSBanner;
        mAddTopArticle = addTopArticle;
        addArticleWebView();
        addAdsBanner();
        addEmotes();
        addHeader();
        loadMoreComments(null);
        addTopArticle();


        sPrefs = mContext.getSharedPreferences(mContext.getString(R.string.vuukle), Context.MODE_PRIVATE);
        mEditor = sPrefs.edit();
    }

    private void addArticleWebView() {
        if (mAddArticleWebView) {
            ARTICLE_WEBVIEW_POSITION = 1;
            HEADER_POSITION = 1;
            commentsWithReplies.add(0, new ArticleWebViewModel(ARTICLE_URL));
        }
    }

    private void addEmotes() {
        if (mIsEmoteVisible) {
            commentsWithReplies.add(new EmotesModel());
            HEADER_POSITION = 1 + BANNER_POSITION + ARTICLE_WEBVIEW_POSITION;
        }
    }

    private void addAdsBanner() {
        if (mAddAdsBanner) {
            commentsWithReplies.add(ARTICLE_WEBVIEW_POSITION, new AdsBannerModel());
            BANNER_POSITION = 1;
        }
    }

    private void addHeader() {
        commentsWithReplies.add(new HeaderModel(0));
    }

    private void addTopArticle() {
        if (mAddTopArticle) {
            ApiService.getTopArticle("2e5a47ef-15f6-4eec-a685-65a6d0ed00d0", "indianexpress.com", "", 24, 24, new CancelableCallback() {
                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d("My", t.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) {
                    ArrayList<TopArticleModel> topArticle = (ArrayList<TopArticleModel>) response.body();
                    if (commentsWithReplies.size() > 2) { //TODO if comments load first
                        commentsWithReplies.addAll(commentsWithReplies.size() - 1, topArticle);
                    } else {
                        commentsWithReplies.addAll(getLastCommentPosition() + 1, topArticle);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (commentsWithReplies.get(position) instanceof HeaderModel) {
            return HEADER;

        } else if (commentsWithReplies.get(position) instanceof CommentModel) {
            return COMMENT;

        } else if (commentsWithReplies.get(position) instanceof ReplyModel) {
            return REPLY;

        } else if (commentsWithReplies.get(position) instanceof EmotesModel) {
            return EMOTES;

        } else if (commentsWithReplies.get(position) instanceof LoadMoreModel) {
            return LOAD_MORE;

        } else if (commentsWithReplies.get(position) instanceof VuukleLogo) {
            return VUUKLE_LOGO;

        } else if (commentsWithReplies.get(position) instanceof AdsBannerModel) {
            return ADS_BANNER;

        } else if (commentsWithReplies.get(position) instanceof ArticleWebViewModel) {
            return ARTICLE_WEBVIEW;

        } else if (commentsWithReplies.get(position) instanceof TopArticleModel) {
            return TOP_ARTICLE;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case REPLY:
                return new ReplyViewHolder(inflater.inflate(R.layout.reply_item, parent, false));
            case COMMENT:
                return new CommentsViewHolder(inflater.inflate(R.layout.comment_item, parent, false));
            case HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.post_comment_header, parent, false), postCommentCallback);
            case EMOTES:
                return new EmotesViewHolder(inflater.inflate(R.layout.fragment_emotes_rating, parent, false));
            case LOAD_MORE:
                return createLoadMoreHolder(inflater, parent);
            case VUUKLE_LOGO:
                return new VuukleLogoHolder(inflater.inflate(R.layout.vuukle_logo, parent, false));
            case ADS_BANNER:
                return new AdsBannerHolder(inflater.inflate(R.layout.ads_banner_item, parent, false));
            case ARTICLE_WEBVIEW:
                return new ArticleWebViewHolder(inflater.inflate(R.layout.article_webview, parent, false));
            case TOP_ARTICLE:
                return new TopArticleViewHolder(inflater.inflate(R.layout.top_article_item, parent, false),topArticleCallback );
            default:
                return new CommentsViewHolder(inflater.inflate(R.layout.comment_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case COMMENT:
                fillCommentFields((CommentBaseHolder) holder, position);
                break;
            case REPLY:
                fillReplyFields((CommentBaseHolder) holder, position);
                break;
            case HEADER:
                fillHeaderFields((HeaderViewHolder) holder);
                break;
            case EMOTES:
                initEmotes((EmotesViewHolder) holder);
                break;
            case LOAD_MORE:
                ((LoadMoreHolder) holder).loadMoreButton.setTag(position);
                break;
            case VUUKLE_LOGO:
                break;
            case ADS_BANNER:
                break;
            case ARTICLE_WEBVIEW:
                ((ArticleWebViewHolder) holder).reloadWebView(ARTICLE_URL);
                break;
            case TOP_ARTICLE:
                fillTopArticle((TopArticleViewHolder) holder, position);
                break;
        }
    }

    private void fillTopArticle(TopArticleViewHolder holder, int position) {
        holder.topArticle = (TopArticleModel) commentsWithReplies.get(position);
        Picasso.with(mContext).load(holder.topArticle.getImg()).into(holder.articleImg);
    }

    private LoadMoreHolder createLoadMoreHolder(LayoutInflater inflater, ViewGroup parent) {
        LoadMoreHolder holder = new LoadMoreHolder(inflater.inflate(R.layout.load_more_button, parent, false));
        holder.loadMoreButton.setOnClickListener(v -> {
            loadMoreComments(holder);
            holder.loadMoreButton.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
        });
        return holder;
    }

    private void initEmotes(EmotesViewHolder holder) {
        holder.mContext = mContext;
        holder.setCardViewsTitle();
        holder.getEmotesRating();
    }

    private void fillHeaderFields(HeaderViewHolder holder) {
        setLogOutButton(holder);
        holder.userName.setText(User.getUserName(mContext));
        holder.userEmail.setText(User.getUserEmail(mContext));

        int totalCount = ((HeaderModel) commentsWithReplies.get(HEADER_POSITION)).getCommentCount();
        switch (totalCount) {
            case 0:
                holder.totalCount.setText(mContext.getString(R.string.leave_a_comment));
                break;
            case 1:
                holder.totalCount.setText(mContext.getString(R.string.one_comment));
                break;
            default:
                holder.totalCount.setText(totalCount + " " + mContext.getString(comments));
        }
    }

    private void setLogOutButton(HeaderViewHolder holder) {
        if (User.isUserLogedIn(mContext)) {
            holder.logout.setVisibility(View.VISIBLE);
            holder.welcomeTv.setVisibility(View.VISIBLE);
            holder.userEmail.setVisibility(View.GONE);
            holder.userName.setVisibility(View.GONE);
            holder.welcomeTv.setText(mContext.getString(R.string.welcome_user) + User.getUserName(mContext));
        } else {
            holder.logout.setVisibility(View.GONE);
            holder.welcomeTv.setVisibility(View.GONE);
            holder.userEmail.setVisibility(View.VISIBLE);
            holder.userName.setVisibility(View.VISIBLE);
        }
    }

    private void setLogOutButton(CommentBaseHolder holder) {
        if (User.isUserLogedIn(mContext)) {
            holder.replyEmail.setVisibility(View.GONE);
            holder.replyUserName.setVisibility(View.GONE);
            holder.replyWelcomeTv.setVisibility(View.VISIBLE);
            holder.replyWelcomeTv.setText(mContext.getString(R.string.welcome_user) + User.getUserName(mContext));
        } else {
            holder.replyEmail.setVisibility(View.VISIBLE);
            holder.replyUserName.setVisibility(View.VISIBLE);
            holder.replyWelcomeTv.setVisibility(View.GONE);
        }
    }

    private void initReplyLayout(CommentBaseHolder holder, int position) {
        LinearLayout replyLayout = (LinearLayout) holder.itemView.findViewById(R.id.reply_layout);
        if (currentOpenPosition != position) {
            replyLayout.setVisibility(View.GONE);
        } else {
            replyLayout.setVisibility(View.VISIBLE);
        }
        holder.itemView.findViewById(R.id.reply_text).setOnClickListener(v -> setReplyLayoutVisible(holder, position));
    }

    private void initSwipeToRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        PAGINATION_FROM = REPLY_COUNT_EMPTY;
        PAGINATION_TO = PAGINATION_COUNT;
        mSwipeRefreshLayout = swipeRefreshLayout;
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            cancelAllCalls();
            loadMoreComments(null);
        });
    }

    private void cancelAllCalls() {
        CancelableCallback.cancelAll();
    }

    private void loadImage(CommentModel comment, CommentBaseHolder holder) {
        if (TextUtils.isEmpty(comment.getAvatarUrl()) || comment.getAvatarUrl().equals("null") || comment.getAvatarUrl().equals(PLACEHOLDER_URL)) {
            holder.initialImage.setText(comment.getInitials());
        } else {
            Picasso.with(mContext).load(comment.getAvatarUrl()).into(holder.avatar);
            holder.initialImage.setVisibility(View.INVISIBLE);
        }
    }

    private void fillCommentFields(CommentBaseHolder holder, int position) {
        setLogOutButton(holder);
        CommentModel comment = (CommentModel) commentsWithReplies.get(position);
        setBorderInvisible(holder, position);
        holder.userName.setText(comment.getName());
        holder.message.setText(comment.getComment());
        fillUpVotes(holder, comment);
        fillDownVotes(holder, comment);
        initShowRepliesView(holder, comment, position);
        holder.timestamp.setText(DateFormat.formatDateFromString(comment.getTs()));
        holder.commentId = comment.commentId;
        holder.commentModel = comment;
        holder.userPoints.setText(comment.getUserPoints().toString());
        holder.context = mContext;
        holder.replyWelcomeTv.setText(mContext.getString(R.string.welcome_user)+User.getUserName(mContext));
        initReplyLayout(holder, position);
        loadImage(comment, holder);
    }

    private void fillReplyFields(CommentBaseHolder holder, int position) {
        setLogOutButton(holder);
        ReplyModel reply = (ReplyModel) commentsWithReplies.get(position);
        holder.initialImage.setText(reply.getInitials());
        holder.userName.setText(reply.getName());
        holder.message.setText(reply.getComment());
        fillUpVotes(holder, reply);
        fillDownVotes(holder, reply);
        initShowRepliesView(holder, reply, position);
        holder.timestamp.setText(DateFormat.formatDateFromString(reply.getTs()));
        holder.commentId = reply.getCommentId();
        holder.commentModel = reply;
        holder.userPoints.setText(reply.getUserPoints().toString());
        holder.context = mContext;
        holder.replyWelcomeTv.setText(mContext.getString(R.string.welcome_user)+User.getUserName(mContext));
        initReplyLayout(holder, position);
        loadImage(reply, holder);
    }

    private void fillUpVotes(CommentBaseHolder holder, BaseCommentModel model) {
        if (model.getUpVotes().equals(0)) {
            holder.upVotes.setVisibility(View.GONE);
        } else {
            holder.upVotes.setVisibility(View.VISIBLE);
            holder.upVotes.setText(model.getUpVotes().toString());
        }
    }

    private void fillDownVotes(CommentBaseHolder holder, BaseCommentModel model) {
        if (model.getDownVotes().equals(0)) {
            holder.downVotes.setVisibility(View.GONE);
        } else {
            holder.downVotes.setVisibility(View.VISIBLE);
            holder.downVotes.setText(model.getDownVotes().toString());
        }
    }

    private void initShowRepliesView(CommentBaseHolder holder, BaseCommentModel model, int position) {
        if (model.getRepliesCount().equals(0)) {
            holder.showReplyes.setVisibility(View.GONE);
        } else {
            holder.showReplyes.setVisibility(View.VISIBLE);
            holder.itemView.setTag(new CommentTag(1, false));
            holder.showReplyes.setOnClickListener(v -> {
                showReplies(model, position, v);
            });
            ((TextView) holder.showReplyes.findViewById(R.id.reply_count)).setText(String.format(Locale.US, "%d", model.getRepliesCount()));
        }
    }

    private void showReplies(BaseCommentModel model, int position, View view) {
        view.setEnabled(false);
        if (((BaseCommentModel) commentsWithReplies.get(position)).isReplyShow()) {
            hideReplyes(model, position);
            ((BaseCommentModel) commentsWithReplies.get(position)).setReplyShow(false);
            view.setEnabled(true);
        } else {
            getRepliesForComment(model, view);
            ((BaseCommentModel) commentsWithReplies.get(position)).setReplyShow(true);
        }
    }

    private void hideReplyes(BaseCommentModel model, int currentOpenPosition) {
        int currentLevel = model.getCommentLevel();
        for (int i = currentOpenPosition + 1; i < getItemCount(); ) {
            if (commentsWithReplies.get(i) instanceof BaseCommentModel && ((BaseCommentModel) commentsWithReplies.get(i)).getCommentLevel() > currentLevel) {
                commentsWithReplies.remove(i);
                notifyItemRemoved(i);
            } else {
                notifyDataSetChanged();
                return;
            }
        }
    }

    private void loadImage(ReplyModel reply, CommentBaseHolder holder) {
        if (TextUtils.isEmpty(reply.getAvatarUrl()) || reply.getAvatarUrl().equals("null") || reply.getAvatarUrl().equals(PLACEHOLDER_URL)) {
            holder.initialImage.setText(reply.getInitials());
        } else {
            Picasso.with(mContext).load(reply.getAvatarUrl()).into(holder.avatar);
        }
    }

    private void sendReplyMessage(CommentBaseHolder holder, int position, ReplyModel reply) {
        if (dataIsValid(holder.replyEmail.getText().toString(), holder.replyUserName.getText().toString(), holder.replyMessage.getText().toString())) {
            int nextPosition = position + 1;
            ((BaseCommentModel) commentsWithReplies.get(position)).setRepliesCount(((BaseCommentModel) commentsWithReplies.get(position)).getRepliesCount() + 1);
            ((BaseCommentModel) commentsWithReplies.get(position)).setReplyShow(true);
            reply.setCommentLevel(((BaseCommentModel) commentsWithReplies.get(position)).getCommentLevel() + 1);
            commentsWithReplies.add(nextPosition, reply);
            notifyItemInserted(nextPosition);
            holder.replyMessage.setText(R.string.empty_field);
            holder.itemView.findViewById(R.id.reply_layout).setVisibility(View.GONE);
        } else {
            showError(holder);
        }
    }

    private void addCommentToList(String name, String comment) {
        CommentModel commentModel = new CommentModel(comment, name);
        commentsWithReplies.add(HEADER_POSITION + 1, commentModel);
        notifyItemInserted(HEADER_POSITION + 1);
    }

    private ReplyModel getReply(CommentBaseHolder holder) {
        ReplyModel reply = new ReplyModel();
        reply.setName(holder.replyUserName.getText().toString());
        reply.setEmail(holder.replyEmail.getText().toString());
        reply.setComment(holder.replyMessage.getText().toString());
        reply.setCommentId(holder.commentId);
        return reply;
    }

    private boolean emailIsValid(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void setReplyLayoutVisible(CommentBaseHolder holder, int position) {
        setLogOutButton(holder);
        LinearLayout feedbackLayout = (LinearLayout) holder.itemView.findViewById(R.id.reply_layout);
        if (feedbackLayout.getVisibility() == View.GONE) {
            currentOpenPosition = position;
            setPreviousReplyLayoutInvisible(holder);
            feedbackLayout.setVisibility(View.VISIBLE);
            postReply(holder, position);
        } else {
            feedbackLayout.setVisibility(View.GONE);
            currentOpenPosition = -1;
        }
    }

    private void setBorderInvisible(RecyclerView.ViewHolder holder, int position) {
        ImageView border = (ImageView) holder.itemView.findViewById(R.id.border);
        if (position == 1) {
            border.setVisibility(View.GONE);
        } else {
            border.setVisibility(View.VISIBLE);
        }
    }

    private void postReply(CommentBaseHolder holder, int position) {
        hideKeyboard(holder.replyPost);
        holder.replyPost.setOnClickListener(v -> {
            v.setEnabled(false);
            mReply = getReply(holder);
            postReplyToServer(mReply, holder);
            saveUserToPreferences(mReply.getEmail(), mReply.getName());
            sendReplyMessage(holder, position, mReply);
            currentOpenPosition = -1;
        });
    }

    public void saveUserToPreferences(String email, String name) {
        User.setUser(mContext, name, email);
    }

    private void postReplyToServer(ReplyModel reply, CommentBaseHolder holder) {
        ApiService.postReply(reply.getName(), reply.getEmail(), reply.getComment(), reply.getCommentId(), new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i(TAG, "onResponse: ");
                holder.replyPost.setEnabled(true);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        }, HOST, ARTICLE_ID, API_KEY, SECRET_KEY, RESOURCE_ID);
    }

    private void setPreviousReplyLayoutInvisible(CommentBaseHolder holder) {
        if (mHolder != null) {
            mHolder.itemView.findViewById(R.id.reply_layout).setVisibility(View.GONE);
        }
        mHolder = holder;
    }

    @Override
    public int getItemCount() {
        return commentsWithReplies == null ? 0 : commentsWithReplies.size();
    }


    private boolean dataIsValid(String userEmail, String userName, String commentMessage) {
        return emailIsValid(userEmail) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(commentMessage);
    }

    private void showError(CommentBaseHolder holder) {
        if (!emailIsValid(holder.replyEmail.getText().toString())) {
            holder.replyEmail.setError(mContext.getString(R.string.invalid_email));
        }
        if (TextUtils.isEmpty(holder.replyUserName.getText())) {
            holder.replyUserName.setError(mContext.getString(R.string.field_is_empty));
        }
        if (TextUtils.isEmpty(holder.replyMessage.getText())) {
            holder.replyMessage.setError(mContext.getString(R.string.field_is_empty));
        }
    }

    private void loadMoreComments(LoadMoreHolder holder) {

        ApiService.getAllComments(PAGINATION_FROM, PAGINATION_TO, new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                CommentFeedModel feedModel = (CommentFeedModel) response.body();
                fillRecyclerView(feedModel, holder);
                setCountComments(feedModel.getComments());
//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
//                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, API_KEY, SECRET_KEY, TIME_ZONE, ARTICLE_ID, HOST);
    }

    private void setCountComments(int count) {
        sPrefs.edit()
                .putInt(COUNT_COMMENTS_KEY, count)
                .apply();
    }

    private void fillRecyclerView(CommentFeedModel feedModel, LoadMoreHolder holder) {
        RESOURCE_ID = feedModel.resourceId;
        removeLoadMoreButton(holder);
        int lastCommentPosition = getLastCommentPosition();
        commentsWithReplies.addAll(lastCommentPosition + 1, feedModel.getCommentFeed());
        ((HeaderModel) commentsWithReplies.get(HEADER_POSITION)).setCommentCount(feedModel.comments);
        if (feedModel.getCommentFeed().size() >= PAGINATION_COUNT) {
            commentsWithReplies.add(getLastCommentPosition() + 1, new LoadMoreModel());
        }
        commentsWithReplies.add(new VuukleLogo());
        PAGINATION_FROM = PAGINATION_TO + 1;
        PAGINATION_TO = PAGINATION_FROM + PAGINATION_COUNT;
        notifyDataSetChanged();
    }

    private int getLastCommentPosition() {
        int last = 0;
        for (int i = commentsWithReplies.size() - 1; i > 0; i--) {
            if (commentsWithReplies.get(i) instanceof CommentModel
                    || commentsWithReplies.get(i) instanceof HeaderModel) {
                last = i;
                break;
            }
        }
        return last;
    }

    private void removeLoadMoreButton(LoadMoreHolder holder) {
        if (holder != null) {
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.loadMoreButton.setVisibility(View.VISIBLE);
        }

        if (commentsWithReplies.size() < 2) {
            return;
        }
        if(holder != null) {
            if (commentsWithReplies.get(getLastCommentPosition() + 1) instanceof LoadMoreModel) {
                commentsWithReplies.remove(getLastCommentPosition() + 1);
                notifyItemRemoved(getLastCommentPosition() + 1);
            }
        }
        removeVuukleLogo();
    }

    private void removeVuukleLogo() {
        if (commentsWithReplies.get(commentsWithReplies.size() - 1) instanceof VuukleLogo) {
            commentsWithReplies.remove(commentsWithReplies.size() - 1);
            notifyItemRemoved(commentsWithReplies.size() - 1);
        }
    }

    private void getRepliesForComment(BaseCommentModel model, View view) {
        ApiService.getCommentReplies(model.getCommentId(), model.getParentId(), new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                initUIReplyesForComment(model, response);
                view.setEnabled(true);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                view.setEnabled(true);
            }
        }, HOST, API_KEY, SECRET_KEY, TIME_ZONE, ARTICLE_ID);
    }

    private void initUIReplyesForComment(BaseCommentModel model, Response response) {
        if (response.body() != null && ((ArrayList<ReplyModel>) response.body()).size() > 0) {
            int commentLevel = model.getCommentLevel();
            commentsWithReplies.addAll(commentsWithReplies.indexOf(model) + 1, initReplyLevel((ArrayList<ReplyModel>) response.body(), commentLevel));
            notifyDataSetChanged();
        }
    }

    private ArrayList<ReplyModel> initReplyLevel(ArrayList<ReplyModel> body, int level) {
        for (ReplyModel reply : body) {
            reply.setCommentLevel(level + 1);
        }
        return body;
    }

    private void getRepliesForReply(ReplyModel model) {
        ApiService.getCommentReplies(model.getCommentId(), model.getParentId(), new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                addRepliesToList(response, model);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("", "onFailure: ");
            }
        }, HOST, API_KEY, SECRET_KEY, TIME_ZONE, ARTICLE_ID);
    }

    private void addRepliesToList(Response response, ReplyModel model) {
        List<ReplyModel> replyFeedModel = (ArrayList<ReplyModel>) response.body();
        commentsWithReplies.addAll(commentsWithReplies.indexOf(model) + 1, replyFeedModel);
        notifyItemInserted(commentsWithReplies.indexOf(model) + 1);

        for (ReplyModel replyModel : replyFeedModel) {
            if (!replyModel.getRepliesCount().equals(REPLY_COUNT_EMPTY)) {
                getRepliesForReply(replyModel);
            }
        }
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
