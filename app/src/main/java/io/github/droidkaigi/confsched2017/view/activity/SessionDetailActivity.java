package io.github.droidkaigi.confsched2017.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.view.fragment.SessionDetailFragmentCreator;
import timber.log.Timber;

public class SessionDetailActivity extends BaseActivity {

    private static final String EXTRA_SESSION_ID = "session_id";
    private static final String EXTRA_PARENT = "parent";

    private Class parentClass;

    public static Intent createIntent(@NonNull Context context, int sessionId, @Nullable Class<? extends Activity> parentClass) {
        Intent intent = new Intent(context, SessionDetailActivity.class);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        if (parentClass != null) {
            intent.putExtra(EXTRA_PARENT, parentClass.getName());
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_session_detail);
        getComponent().inject(this);

        final int sessionId = getIntent().getIntExtra(EXTRA_SESSION_ID, 0);
        String parentClassName = getIntent().getStringExtra(EXTRA_PARENT);
        try {
            if (TextUtils.isEmpty(parentClassName)) {
                parentClass = MainActivity.class;
            } else {
                parentClass = Class.forName(parentClassName);
            }
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        }
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
        Intent upIntent = new Intent(getApplicationContext(), parentClass);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(upIntent);
        finish();
    }

}
