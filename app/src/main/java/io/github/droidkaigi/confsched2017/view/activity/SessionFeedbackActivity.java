package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivitySessionFeedbackBinding;
import io.github.droidkaigi.confsched2017.view.fragment.SessionFeedbackFragmentCreator;

public class SessionFeedbackActivity extends BaseActivity {

    private static final String TAG = SessionFeedbackActivity.class.getSimpleName();

    private static final String EXTRA_SESSION_ID = "session_id";

    private ActivitySessionFeedbackBinding binding;

    public static Intent createIntent(@NonNull Context context, int sessionId) {
        Intent intent = new Intent(context, SessionFeedbackActivity.class);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_session_feedback);
        getComponent().inject(this);

        initBackToolbar(binding.toolbar);

        final int sessionId = getIntent().getIntExtra(EXTRA_SESSION_ID, 0);
        replaceFragment(SessionFeedbackFragmentCreator.newBuilder(sessionId).build(), R.id.content_view);
    }
}
