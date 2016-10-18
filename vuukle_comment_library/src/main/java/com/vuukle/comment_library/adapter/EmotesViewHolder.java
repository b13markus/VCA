package com.vuukle.comment_library.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vuukle.comment_library.models.Emotes;
import com.vuukle.comment_library.models.RaitingModel;
import com.vuukle.comment_library.network.ApiService;
import com.vuukle.comment_library.network.CancelableCallback;
import com.vuukle.comment_library.utils.SharedPreferenceUtils;
import com.vuukle.comments.vuuklecommentlibrary.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.vuukle.comment_library.adapter.CommentsAdapter.API_KEY;
import static com.vuukle.comment_library.adapter.CommentsAdapter.ARTICLE_ID;
import static com.vuukle.comment_library.adapter.CommentsAdapter.HOST;
import static com.vuukle.comment_library.adapter.CommentsAdapter.TITLE;
import static com.vuukle.comment_library.adapter.CommentsAdapter.ARTICLE_URL;


public class EmotesViewHolder extends RecyclerView.ViewHolder {


    private TextView happyRatio, happyRatioCount,
            indifferentRatio, indifferentRatioCount,
            amusedRatio, amusedRatioCount,
            excitedRatio, excitedRatioCount,
            angryRatio, angryRatioCount,
            sadRatio, sadRatioCount,
            cardViewsTitle;

    private static final Integer HAPPY_NUMBER = 1;
    private static final Integer INDIFFERENT_NUMBER = 2;
    private static final Integer AMUSED_NUMBER = 3;
    private static final Integer EXCITED_NUMBER = 4;
    private static final Integer ANGRY_NUMBER = 5;
    private static final Integer SAD_NUMBER = 6;
    public Activity mContext;

    private View.OnClickListener onClickListener = this::rateArticle;

    public EmotesViewHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    private void initViews(View view) {
        initCardViews(view);
        initTextViews(view);
    }

    private void initCardViews(View view) {
        cardViewsTitle = (TextView) view.findViewById(R.id.card_views_title);
        view.findViewById(R.id.card_emote_happy).setOnClickListener(onClickListener);
        view.findViewById(R.id.card_emote_indifferent).setOnClickListener(onClickListener);
        view.findViewById(R.id.card_emote_amused).setOnClickListener(onClickListener);
        view.findViewById(R.id.card_emote_excited).setOnClickListener(onClickListener);
        view.findViewById(R.id.card_emote_angry).setOnClickListener(onClickListener);
        view.findViewById(R.id.card_emote_sad).setOnClickListener(onClickListener);

    }

    public void setCardViewsTitle() {
        ArrayList<String> artilesId = SharedPreferenceUtils.getListString(mContext, mContext.getString(R.string.articles_id));
        if (artilesId.contains(ARTICLE_ID)) {
            cardViewsTitle.setText(R.string.thank_you_for_voting);
        } else {
            cardViewsTitle.setText(R.string.what_is_your_reaction);
        }
    }

    private void initTextViews(View view) {
        happyRatio = (TextView) view.findViewById(R.id.happy_ratio);
        happyRatioCount = (TextView) view.findViewById(R.id.happy_ratio_count);
        indifferentRatio = (TextView) view.findViewById(R.id.indifferent_ratio);
        indifferentRatioCount = (TextView) view.findViewById(R.id.indifferent_ratio_count);
        amusedRatio = (TextView) view.findViewById(R.id.amused_ratio);
        amusedRatioCount = (TextView) view.findViewById(R.id.amused_ratio_count);
        excitedRatio = (TextView) view.findViewById(R.id.excited_ratio);
        excitedRatioCount = (TextView) view.findViewById(R.id.excited_ratio_count);
        angryRatio = (TextView) view.findViewById(R.id.angry_ratio);
        angryRatioCount = (TextView) view.findViewById(R.id.angry_ratio_count);
        sadRatio = (TextView) view.findViewById(R.id.sad_ratio);
        sadRatioCount = (TextView) view.findViewById(R.id.sad_ratio_count);

    }

    private void rateArticle(View v) {
        ArrayList<String> artilesId = SharedPreferenceUtils.getListString(mContext, mContext.getString(R.string.articles_id));
        if (artilesId.contains(ARTICLE_ID)) {
            Toast.makeText(mContext, R.string.you_have_already_voted, Toast.LENGTH_SHORT).show();
        } else {
            sendEmoteRate(v, artilesId);
        }
    }

    private void sendEmoteRate(View v, ArrayList<String> artilesId) {
        v.setClickable(false);
        ApiService.setEmoteRating(HOST, ARTICLE_ID, API_KEY, ARTICLE_URL, TITLE, "null", setEmoteInt(v.getId()), new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                addArticleIdToPreferences(artilesId);

                getEmotesRating();
                cardViewsTitle.setText(R.string.thank_you_for_voting);
                v.setClickable(true);
            }
        });
    }

    private void setEmotesRating(Response response) {
        Emotes emotes = ((RaitingModel) response.body()).getEmotes();
        happyRatio.setText(parseToPercentage(emotes, emotes.getFirst()));
        happyRatioCount.setText(emotes.getFifth() + "");
        indifferentRatio.setText(parseToPercentage(emotes, emotes.getSecond()));
        indifferentRatioCount.setText(emotes.getSecond() + "");
        amusedRatio.setText(parseToPercentage(emotes, emotes.getThird()));
        amusedRatioCount.setText(emotes.getThird() + "");
        excitedRatio.setText(parseToPercentage(emotes, emotes.getFourth()));
        excitedRatioCount.setText(emotes.getFourth() + "");
        angryRatio.setText(parseToPercentage(emotes, emotes.getFifth()));
        angryRatioCount.setText(emotes.getFifth() + "");
        sadRatio.setText(parseToPercentage(emotes, emotes.getSixth()));
        sadRatioCount.setText(emotes.getSixth() + "");
        highlightEmotion();
    }

    private String parseToPercentage(Emotes emotes, Integer emoteRating) {
        float emotesSum = emotes.getFirst() + emotes.getSecond() + emotes.getThird() + emotes.getFourth() + emotes.getFifth() + emotes.getSixth();
        return Math.round(emoteRating * 100 / emotesSum) + "%";
    }

    private void highlightEmotion() {
        final int HAPPY_NUMBER = 1;
        final int INDIFFERENT_NUMBER = 2;
        final int AMUSED_NUMBER = 3;
        final int EXCITED_NUMBER = 4;
        final int ANGRY_NUMBER = 5;
        final int SAD_NUMBER = 6;
        switch (getEmoteNumberFromPreferences()) {
            case HAPPY_NUMBER:
                happyRatio.setTextColor(mContext.getResources().getColor(R.color.happy));
                happyRatio.setTextSize(20);
                break;
            case INDIFFERENT_NUMBER:
                indifferentRatio.setTextColor(mContext.getResources().getColor(R.color.indifferent));
                indifferentRatio.setTextSize(20);
                break;
            case AMUSED_NUMBER:
                amusedRatio.setTextColor(mContext.getResources().getColor(R.color.amused));
                amusedRatio.setTextSize(20);
                break;
            case EXCITED_NUMBER:
                excitedRatio.setTextColor(mContext.getResources().getColor(R.color.excited));
                excitedRatio.setTextSize(20);
                break;
            case ANGRY_NUMBER:
                angryRatio.setTextColor(mContext.getResources().getColor(R.color.angry));
                angryRatio.setTextSize(20);
                break;
            case SAD_NUMBER:
                sadRatio.setTextColor(mContext.getResources().getColor(R.color.sad));
                sadRatio.setTextSize(20);
                break;
        }
    }

    public void getEmotesRating() {
        ApiService.getEmoteRating(HOST, ARTICLE_ID, API_KEY, new CancelableCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                setEmotesRating(response);
            }
        });
    }

    private Integer setEmoteInt(int emoteCardId) {
        Integer emoteNumber = 0;
        if (emoteCardId == R.id.card_emote_happy) {
            emoteNumber = HAPPY_NUMBER;
        }
        if (emoteCardId == R.id.card_emote_indifferent) {
            emoteNumber = INDIFFERENT_NUMBER;
        }
        if (emoteCardId == R.id.card_emote_amused) {
            emoteNumber = AMUSED_NUMBER;
        }
        if (emoteCardId == R.id.card_emote_excited) {
            emoteNumber = EXCITED_NUMBER;
        }
        if (emoteCardId == R.id.card_emote_angry) {
            emoteNumber = ANGRY_NUMBER;
        }
        if (emoteCardId == R.id.card_emote_sad) {
            emoteNumber = SAD_NUMBER;
        }
        setEmoteNumberToPreferences(emoteNumber);
        return emoteNumber;
    }

    private void addArticleIdToPreferences(ArrayList<String> artilesId) {
        artilesId.add(ARTICLE_ID);
        SharedPreferenceUtils.putListString(mContext, mContext.getString(R.string.articles_id), artilesId);
    }

    public void setEmoteNumberToPreferences(int emoteNumber) {
        SharedPreferences preferences = (mContext).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(mContext.getString(R.string.emote_number), emoteNumber);
        editor.apply();
    }

    public int getEmoteNumberFromPreferences() {
        int defaultValue = 0;
        SharedPreferences preferences = (mContext).getPreferences(Context.MODE_PRIVATE);
        return preferences.getInt(mContext.getString(R.string.emote_number), defaultValue);
    }
}
