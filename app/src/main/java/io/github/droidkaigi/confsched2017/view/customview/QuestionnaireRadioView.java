package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import io.github.droidkaigi.confsched2017.R;

/**
 * Copyright 2017 G-CREATE
 */

public class QuestionnaireRadioView extends QuestionnaireView {
    private RadioGroup radioGroup;
    private String[] labels;
    private String[] values;

    public QuestionnaireRadioView(Context context) {
        this(context, null);
    }

    public QuestionnaireRadioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionnaireRadioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            inflate(context, R.layout.view_questionnaire, this);
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuestionnaireRadioView);
        int labelId = a.getResourceId(R.styleable.QuestionnaireRadioView_radioItemsKey, -1);
        int valueId = a.getResourceId(R.styleable.QuestionnaireRadioView_radioItemsValue, -1);
        a.recycle();

        labels = getResources().getStringArray(labelId);
        values = getResources().getStringArray(valueId);
        if (labels.length != values.length) {
            throw new RuntimeException("Keys size and values size are not equal.");
        }

        radioGroup = new RadioGroup(context, attrs);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < labels.length; i++) {
            RadioButton button = new RadioButton(context, attrs);
            button.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(labels[i]);
            radioGroup.addView(button);
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> setValue(values[checkedId - 1]));
        binding.questionnaireContainer.addView(radioGroup);
    }
}
