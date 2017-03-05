package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewQuestionnaireBinding;

/**
 * Copyright 2017 G-CREATE
 */

@InverseBindingMethods({
        @InverseBindingMethod(
                type = QuestionnaireView.class,
                attribute = "questionnaireValue",
                method = "getQuestionnaireValue"
        ),
        @InverseBindingMethod(
                type = QuestionnaireView.class,
                attribute = "questionnaireOtherValue",
                method = "getQuestionnaireOtherValue"
        )
})
public class QuestionnaireView extends CardView {
    @StringRes
    protected static final int OTHER_POST_VALUE_RES_ID = R.string.questionnaire_other_field_post_value;
    protected ViewQuestionnaireBinding binding;
    protected String title;
    @Nullable
    protected String description;
    protected String value;
    protected String otherValue;
    protected boolean hasItemOther;
    protected OnQuestionnaireValueChangeListener listener;
    protected OnQuestionnaireOtherValueChangedListener otherListener;

    public QuestionnaireView(Context context) {
        this(context, null);
    }

    public QuestionnaireView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionnaireView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            inflate(context, R.layout.view_questionnaire, this);
            return;
        }

        binding = ViewQuestionnaireBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuestionnaireView);
        title = a.getString(R.styleable.QuestionnaireView_questionnaireTitle);
        description = a.getString(R.styleable.QuestionnaireView_questionnaireDescription);
        hasItemOther = a.getBoolean(R.styleable.QuestionnaireView_hasItemOther, false);
        a.recycle();

        binding.questionnaireTitle.setText(title);
        if (description == null) {
            binding.questionnaireDescription.setVisibility(GONE);
        } else {
            binding.questionnaireDescription.setText(description);
        }
    }

    public String getValue() {
        return value != null ? value : "";
    }

    public void setValue(String value) {
        this.value = value;
        if (listener != null) {
            listener.onQuestionnaireValueChange(this, value);
        }
    }

    public String getOtherValue() {
        return otherValue != null ? otherValue : "";
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
        if (otherListener != null) {
            otherListener.onQuestionnaireOtherValueChanged();
        }
    }

    @InverseBindingAdapter(attribute = "questionnaireValue")
    public static String getQuestionnaireValue(QuestionnaireView view) {
        return view.getValue();
    }

    @BindingAdapter("questionnaireValueAttrChanged")
    public static void setQuestionnaireValueAttrChanged(QuestionnaireView view, InverseBindingListener listener) {
        if (listener == null) {
            view.setListener(null);
        } else {
            view.setListener((v, currentValue) -> listener.onChange());
        }
    }

    @BindingAdapter("questionnaireValue")
    public static void setQuestionnaireValue(QuestionnaireView view, String currentValue) {
        if (!(view.getValue().equals(currentValue))) {
            view.setValue(currentValue);
        }
    }

    @InverseBindingAdapter(attribute = "questionnaireOtherValue")
    public static String getQuestionnaireOtherValue(QuestionnaireView view) {
        return view.getOtherValue();
    }

    @BindingAdapter("questionnaireOtherValueAttrChanged")
    public static void setQuestionnaireOtherValueAttrChanged(QuestionnaireView view, InverseBindingListener listener) {
        if (listener == null) {
            view.setListener(null);
        } else {
            view.setOtherListener(listener::onChange);
        }
    }

    @BindingAdapter("questionnaireOtherValue")
    public static void setQuestionnaireOtherValue(QuestionnaireView view, String otherValue) {
        if (!(view.getOtherValue().equals(otherValue))) {
            view.setOtherValue(otherValue);
        }
    }

    public void setListener(OnQuestionnaireValueChangeListener listener) {
        this.listener = listener;
    }

    public void setOtherListener(OnQuestionnaireOtherValueChangedListener otherListener) {
        this.otherListener = otherListener;
    }

    public interface OnQuestionnaireValueChangeListener {
        void onQuestionnaireValueChange(QuestionnaireView view, String currentValue);
    }

    public interface OnQuestionnaireOtherValueChangedListener {
        void onQuestionnaireOtherValueChanged();
    }
}
