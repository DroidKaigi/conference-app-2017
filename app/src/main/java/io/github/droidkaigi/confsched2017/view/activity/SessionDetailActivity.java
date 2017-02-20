package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MenuItem;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.view.fragment.SessionDetailFragmentCreator;

public class SessionDetailActivity extends BaseActivity {

    private static final String EXTRA_SESSION_ID = "session_id";
    private static final String EXTRA_PARENT_NAME = "parent_name";

    private String parentName;

    public static Intent createIntent(@NonNull Context context, int sessionId) {
        Intent intent = new Intent(context, SessionDetailActivity.class);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        intent.putExtra(EXTRA_PARENT_NAME, context.getClass().getCanonicalName());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_session_detail);
        getComponent().inject(this);

        final int sessionId = getIntent().getIntExtra(EXTRA_SESSION_ID, 0);
        parentName = getIntent().getStringExtra(EXTRA_PARENT_NAME);
        replaceFragment(SessionDetailFragmentCreator.newBuilder(sessionId).build(), R.id.content_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                upToParentActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void upToParentActivity() {
        Intent upIntent;
        if (TextUtils.equals(this.parentName, MySessionsActivity.class.getCanonicalName())) {
            upIntent = new Intent(getApplicationContext(), MySessionsActivity.class);
        } else if (TextUtils.equals(this.parentName, SearchActivity.class.getCanonicalName())) {
            upIntent = new Intent(getApplicationContext(), SearchActivity.class);
        } else {
            upIntent = new Intent(getApplicationContext(), MainActivity.class);
        }
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(upIntent);
        finish();
    }

}
