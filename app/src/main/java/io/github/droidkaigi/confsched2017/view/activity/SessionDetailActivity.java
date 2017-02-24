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
    private static final String EXTRA_PARENT = "parent";

    public static final int PARENT_MAIN = 0;
    public static final int PARENT_MY_SESSIONS = 1;
    public static final int PARENT_SEARCH = 2;

    private int parent;

    public static Intent createIntent(@NonNull Context context, int sessionId, int parent) {
        Intent intent = new Intent(context, SessionDetailActivity.class);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        intent.putExtra(EXTRA_PARENT, parent);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_session_detail);
        getComponent().inject(this);

        final int sessionId = getIntent().getIntExtra(EXTRA_SESSION_ID, 0);
        parent = getIntent().getIntExtra(EXTRA_PARENT, PARENT_MAIN);
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
        switch (parent) {
            case PARENT_MY_SESSIONS:
                upIntent = new Intent(getApplicationContext(), MySessionsActivity.class);
                break;
            case PARENT_SEARCH:
                upIntent = new Intent(getApplicationContext(), SearchActivity.class);
                break;
            default:
                upIntent = new Intent(getApplicationContext(), MainActivity.class);
                break;
        }
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(upIntent);
        finish();
    }

}
