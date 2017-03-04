package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.util.AttributeSet;

import timber.log.Timber;

/**
 * Copyright 2017 G-CREATE
 */

public class QuestionnaireRankingView extends QuestionnaireView {
    private FeedbackRankingView rankingView;
    private FeedbackRankingView.OnCurrentRankingChangeListener currentRankingChangedListener =
            (view, currentRanking) -> setValue(String.valueOf(currentRanking));

    public QuestionnaireRankingView(Context context) {
        this(context, null);
    }

    public QuestionnaireRankingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionnaireRankingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rankingView = new FeedbackRankingView(context, attrs);
        binding.questionnaireContainer.addView(rankingView);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        rankingView.addListener(currentRankingChangedListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        rankingView.removeListener(currentRankingChangedListener);
    }
}
