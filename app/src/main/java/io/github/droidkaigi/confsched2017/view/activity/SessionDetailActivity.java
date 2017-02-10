package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.view.fragment.SessionDetailFragmentCreator;

public class SessionDetailActivity extends BaseActivity {

    private static final String EXTRA_SESSION_ID = "session_id";

    public static Intent createIntent(@NonNull Context context, int sessionId) {
        Intent intent = new Intent(context, SessionDetailActivity.class);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_session_detail);
        getComponent().inject(this);

        final int sessionId = getIntent().getIntExtra(EXTRA_SESSION_ID, 0);
        replaceFragment(SessionDetailFragmentCreator.newBuilder(sessionId).build(), R.id.content_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                upToMainActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void upToMainActivity() {
        Intent upIntent = new Intent(getApplicationContext(), MainActivity.class);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(upIntent);
        finish();
    }

}
