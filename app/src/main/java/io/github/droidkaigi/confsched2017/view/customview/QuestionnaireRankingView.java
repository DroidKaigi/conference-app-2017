package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import io.github.droidkaigi.confsched2017.R;
import timber.log.Timber;

/**
 * Copyright 2017 G-CREATE
 */

public class QuestionnaireRankingView extends QuestionnaireView {
    private FeedbackRankingView rankingView;
    private String[] postValues;
    private FeedbackRankingView.OnCurrentRankingChangeListener currentRankingChangedListener = (view, currentRanking) -> {
        // current ranking from FeedbackRankingView is 1 to 5 but postValues index is started from 0
        setValue(postValues[currentRanking - 1]);
    };

    public QuestionnaireRankingView(Context context) {
        this(context, null);
    }

    public QuestionnaireRankingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionnaireRankingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Timber.d("constructor");

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuestionnaireRankingView);
        int valuesId = a.getResourceId(R.styleable.QuestionnaireRankingView_rankingItemsPostValueArray, -1);
        a.recycle();
        if (valuesId == -1) {
            throw new RuntimeException("This view must set `rankingItemsPostValueArray` attribute.");
        }

        postValues = getResources().getStringArray(valuesId);

        rankingView = new FeedbackRankingView(context, attrs);

        binding.questionnaireContainer.addView(rankingView);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.d("attached to window");
        rankingView.addListener(currentRankingChangedListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Timber.d("detached from window");
        rankingView.removeListener(currentRankingChangedListener);
    }
}
