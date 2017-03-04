package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import io.github.droidkaigi.confsched2017.R;

/**
 * Copyright 2017 G-CREATE
 */

public class QuestionnaireRadioView extends QuestionnaireView {

    private RadioGroup radioGroup;

    private EditText otherValueEditText;

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

        parseAttributes(context, attrs);
        initQuestionnaireContainer(context);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuestionnaireRadioView);
        int labelId = a.getResourceId(R.styleable.QuestionnaireRadioView_radioItemsKey, -1);
        int valueId = a.getResourceId(R.styleable.QuestionnaireRadioView_radioItemsValue, -1);
        a.recycle();

        labels = getResources().getStringArray(labelId);
        values = getResources().getStringArray(valueId);
        if (labels.length != values.length) {
            throw new RuntimeException("Keys size and values size are not equal.");
        }
    }

    private void initQuestionnaireContainer(Context context) {
        radioGroup = new RadioGroup(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setLayoutParams(params);

        for (int i = 0; i < labels.length; i++) {
            RadioButton button = new RadioButton(context);
            button.setLayoutParams(params);
            button.setText(labels[i]);
            radioGroup.addView(button);
        }
        if (hasItemOther) {
            addOtherField(context);
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId > values.length) {
                // select other field
                selectOtherField(true);
                setValue(getResources().getString(QuestionnaireView.OTHER_POST_VALUE_RES_ID));
            } else {
                setValue(values[checkedId - 1]);
                setOtherValue("");
                selectOtherField(false);
            }
        });
        binding.questionnaireContainer.addView(radioGroup);
    }

    private void addOtherField(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        RadioButton button = new RadioButton(context);
        button.setLayoutParams(params);
        button.setText(getResources().getString(R.string.questionnaire_other_field_label));
        radioGroup.addView(button);

        otherValueEditText = new EditText(context);
        otherValueEditText.setLayoutParams(params);
        otherValueEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        otherValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setOtherValue(s.toString());
            }
        });
        otherValueEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                radioGroup.check(labels.length + 1);
            }
        });
        radioGroup.addView(otherValueEditText);
    }

    private void selectOtherField(boolean isSelected) {
        if (isSelected) {
            otherValueEditText.setHint(R.string.questionnaire_other_field_hint);
        } else {
            otherValueEditText.setHint("");
            otherValueEditText.setText("");
        }
    }
}
