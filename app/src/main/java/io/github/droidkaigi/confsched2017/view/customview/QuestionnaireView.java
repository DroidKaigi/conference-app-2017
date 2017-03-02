package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewQuestionnaireBinding;

/**
 * Copyright 2017 G-CREATE
 */

public class QuestionnaireView extends CardView {
    protected ViewQuestionnaireBinding binding;
    protected String title;
    protected String description;
    private String value;
    private String otherValue;

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
        a.recycle();

        binding.questionnaireTitle.setText(title);
        binding.questionnaireDescription.setText(description);
    }

    public String getValue() {
        return value != null ? value : "";
    }

}
