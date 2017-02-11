package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewFeedbackRankingBinding;

@InverseBindingMethods({
        @InverseBindingMethod(type = FeedbackRankingView.class,
                attribute = "currentRanking",
                method = "getCurrentRanking")
})
public class FeedbackRankingView extends FrameLayout {

    private static final int DEFAULT_MAX_SIZE = 5;

    private int maxSize;

    private String labelStart;

    private String labelEnd;

    private int currentRanking;

    private ViewFeedbackRankingBinding binding;

    private OnCurrentRankingChangeListener listener;

    public FeedbackRankingView(Context context) {
        this(context, null);
    }

    public FeedbackRankingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeedbackRankingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            inflate(context, R.layout.view_feedback_ranking, this);
            return;
        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_feedback_ranking, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FeedbackRankingView);
        maxSize = a.getInteger(R.styleable.FeedbackRankingView_rankingMaxSize, DEFAULT_MAX_SIZE);
        labelStart = a.getString(R.styleable.FeedbackRankingView_rankingLabelStart);
        labelEnd = a.getString(R.styleable.FeedbackRankingView_rankingLabelEnd);
        a.recycle();

        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(labelStart)) {
            binding.txtLabelStart.setVisibility(View.VISIBLE);
            binding.txtLabelStart.setText(labelStart);
        }

        if (!TextUtils.isEmpty(labelEnd)) {
            binding.txtLabelEnd.setVisibility(View.VISIBLE);
            binding.txtLabelEnd.setText(labelEnd);
        }

        addRankingViews();
    }

    private void addRankingViews() {
        for (int i = 1; i <= maxSize; i++) {
            final int number = i;
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_feedback_ranking_item, binding.rankingContainer, false);
            TextView txtRanking = (TextView) view.findViewById(R.id.txt_ranking);
            txtRanking.setText(String.valueOf(number));
            txtRanking.setOnClickListener(v -> {
                unselectAll();
                v.setSelected(true);
                currentRanking = number;
                if (listener != null) {
                    listener.onCurrentRankingChange(this, currentRanking);
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
            binding.rankingContainer.addView(view, params);
        }
    }

    private void unselectAll() {
        for (int i = 0, size = binding.rankingContainer.getChildCount(); i < size; i++) {
            binding.rankingContainer.getChildAt(i).findViewById(R.id.txt_ranking).setSelected(false);
        }
    }

    public int getCurrentRanking() {
        return currentRanking;
    }

    public void setCurrentRanking(int currentRanking) {
        if (currentRanking <= 0) {
            unselectAll();
        } else if (currentRanking <= binding.rankingContainer.getChildCount()) {
            unselectAll();
            View view = binding.rankingContainer.getChildAt(currentRanking - 1);
            view.setSelected(true);
            this.currentRanking = currentRanking;
        }
    }

    public void setListener(OnCurrentRankingChangeListener listener) {
        this.listener = listener;
    }

    public interface OnCurrentRankingChangeListener {

        void onCurrentRankingChange(FeedbackRankingView view, int currentRanking);
    }

    @InverseBindingAdapter(attribute = "currentRanking")
    public static int getCurrentRanking(FeedbackRankingView view) {
        return view.getCurrentRanking();
    }

    @BindingAdapter("currentRanking")
    public static void setCurrentRanking(FeedbackRankingView view, int currentRanking) {
        if (currentRanking != view.getCurrentRanking()) {
            view.setCurrentRanking(currentRanking);
        }
    }

    @BindingAdapter("currentRankingAttrChanged")
    public static void setCurrentRankingAttrChanged(FeedbackRankingView view, InverseBindingListener listener) {
        if (listener == null) {
            view.setListener(null);
        } else {
            view.setListener((v, currentRanking) -> listener.onChange());
        }
    }

}
