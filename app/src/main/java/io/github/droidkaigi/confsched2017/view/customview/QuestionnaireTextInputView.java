package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import io.github.droidkaigi.confsched2017.R;

/**
 * Copyright 2017 G-CREATE
 */

public class QuestionnaireTextInputView extends QuestionnaireView {
    private static final int MAX_LINE = 5;
    private EditText editText;

    public QuestionnaireTextInputView(Context context) {
        this(context, null);
    }

    public QuestionnaireTextInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionnaireTextInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            inflate(context, R.layout.view_questionnaire, this);
            return;
        }

        editText = new EditText(context, attrs);
        editText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        editText.setMaxLines(MAX_LINE);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setValue(s.toString());
            }
        });
        binding.questionnaireContainer.addView(editText);
    }
}
