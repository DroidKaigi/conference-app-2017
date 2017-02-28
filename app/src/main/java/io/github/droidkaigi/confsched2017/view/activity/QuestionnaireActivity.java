package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityQuestionnaireBinding;
import io.github.droidkaigi.confsched2017.view.fragment.QuestionnaireFragment;

public class QuestionnaireActivity extends BaseActivity {

    private static final String TAG = QuestionnaireActivity.class.getSimpleName();

    private ActivityQuestionnaireBinding binding;

    public static Intent createIntent(@NonNull Context context) {
        Intent intent = new Intent(context, QuestionnaireActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questionnaire);
        getComponent().inject(this);

        initBackToolbar(binding.toolbar);

        replaceFragment(QuestionnaireFragment.newInstance(), R.id.content_view);
    }
}
