package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewQuestionnaireBinding;
import timber.log.Timber;

/**
 * Copyright 2017 G-CREATE
 */

@InverseBindingMethods(
        @InverseBindingMethod(
                type = QuestionnaireView.class,
                attribute = "questionnaireValue",
                method = "getQuestionnaireValue"
        )
)
public class QuestionnaireView extends CardView {

    protected ViewQuestionnaireBinding binding;
    protected String title;
    protected String description;
    protected String value;
    protected String otherValue;
    protected boolean hasOtherField;
    protected OnQuestionnaireValueChangeListener listener;

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
        hasOtherField = a.getBoolean(R.styleable.QuestionnaireView_hasOtherField, false);
        a.recycle();

        binding.questionnaireTitle.setText(title);
        binding.questionnaireDescription.setText(description);
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
        return otherValue != null ? value : "";
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }

    @InverseBindingAdapter(attribute = "questionnaireValue")
    public static String getQuestionnaireValue(QuestionnaireView view) {
        Timber.d("getQuestionnaireValue view=%s", view);
        return view.getValue();
    }

    @BindingAdapter("questionnaireValueAttrChanged")
    public static void setQuestionnaireValueAttrChanged(QuestionnaireView view, InverseBindingListener listener) {
        Timber.d("setQuestionnaireValueChanged view=%s, listener=%s", view, listener);
        if (listener == null) {
            view.setListener(null);
        } else {
            view.setListener((v, currentValue) -> listener.onChange());
        }
    }

    @BindingAdapter("questionnaireValue")
    public static void setQuestionnaireValue(QuestionnaireView view, String currentValue) {
        Timber.d("setQuestionnaireValue %s on view=%s", currentValue, view);
        if (!(view.getValue().equals(currentValue))) {
            view.setValue(currentValue);
        }
    }

    public void setListener(OnQuestionnaireValueChangeListener listener) {
        this.listener = listener;
    }

    public interface OnQuestionnaireValueChangeListener {

        void onQuestionnaireValueChange(QuestionnaireView view, String currentValue);
    }
}
