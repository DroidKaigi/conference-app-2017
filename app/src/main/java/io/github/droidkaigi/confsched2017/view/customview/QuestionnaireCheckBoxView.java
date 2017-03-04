package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import io.github.droidkaigi.confsched2017.R;

/**
 * Copyright 2017 G-CREATE
 */

@InverseBindingMethods(@InverseBindingMethod(
        type = QuestionnaireCheckBoxView.class,
        attribute = "questionnaireMultipleValue",
        method = "getQuestionnaireMultipleValue"
))
public class QuestionnaireCheckBoxView extends QuestionnaireView implements View.OnClickListener {
    private ArrayList<String> checkedList;
    private String[] labels;
    private String[] values;
    private OnCheckedItemChangedListener itemChangedListener;

    public QuestionnaireCheckBoxView(Context context) {
        this(context, null);
    }

    public QuestionnaireCheckBoxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionnaireCheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuestionnaireCheckBoxView);
        int labelId = a.getResourceId(R.styleable.QuestionnaireCheckBoxView_checkItemsLabel, 0);
        int valueId = a.getResourceId(R.styleable.QuestionnaireCheckBoxView_checkItemsPostValueArray, 0);
        a.recycle();

        labels = getResources().getStringArray(labelId);
        values = getResources().getStringArray(valueId);
        if (labels.length != values.length) {
            throw new RuntimeException("Labels length and values length must be equal.");
        }
        checkedList = new ArrayList<>();

        LinearLayout container = new LinearLayout(context);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(params);
        container.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < labels.length; i++) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setId(i);
            checkBox.setLayoutParams(params);
            checkBox.setText(labels[i]);
            checkBox.setOnClickListener(this);
            container.addView(checkBox);
        }
        binding.questionnaireContainer.addView(container);
    }

    public String[] getMultipleValue() {
        String[] strings = new String[checkedList.size()];
        strings = checkedList.toArray(strings);
        return strings;
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof CheckBox)) return;
        CheckBox checkBox = (CheckBox) v;
        int id = v.getId();
        if (checkBox.isChecked()) {
            checkedList.add(values[id]);
        } else {
            checkedList.remove(values[id]);
        }
        if (itemChangedListener != null) {
            itemChangedListener.onCheckedItemChanged();
        }
    }

    @InverseBindingAdapter(attribute = "questionnaireMultipleValue")
    public static String[] getQuestionnaireMultipleValue(QuestionnaireCheckBoxView view) {
        return view.getMultipleValue();
    }

    @BindingAdapter("questionnaireMultipleValueAttrChanged")
    public static void setQuestionnaireMultipleValueAttrChanged(QuestionnaireCheckBoxView view, InverseBindingListener listener) {
        if (listener == null) {
            view.setItemChangedListener(null);
        } else {
            view.setItemChangedListener(listener::onChange);
        }
    }

    @BindingAdapter("questionnaireMultipleValue")
    public static void setQuestionnaireMultipleValue(QuestionnaireCheckBoxView view, String[] values) {
        // no-op
    }

    public interface OnCheckedItemChangedListener {
        void onCheckedItemChanged();
    }

    public void setItemChangedListener(OnCheckedItemChangedListener itemChangedListener) {
        this.itemChangedListener = itemChangedListener;
    }
}
