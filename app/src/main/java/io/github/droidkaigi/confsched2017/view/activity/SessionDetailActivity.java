package io.github.droidkaigi.confsched2017.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.transition.Transition;
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
        if (TextUtils.isEmpty(parentClassName)) {
            parentClass = MainActivity.class;
        } else {
            try {
                parentClass = Class.forName(parentClassName);
            } catch (ClassNotFoundException e) {
                Timber.e(e);
            }
        }
        replaceFragment(SessionDetailFragmentCreator.newBuilder(sessionId).build(), R.id.content_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onTransitionEnd(Transition transition) {
                    getWindow().getSharedElementEnterTransition().removeListener(this);
                    findViewById(R.id.content_view).setBackground(new ColorDrawable(Color.TRANSPARENT));
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
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

    @Override
    public void onBackPressed() {
        findViewById(R.id.content_view).setBackground(new ColorDrawable(Color.WHITE));
        ActivityCompat.finishAfterTransition(this);
    }
}
