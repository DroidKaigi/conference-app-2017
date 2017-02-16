package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityMySessionsBinding;
import io.github.droidkaigi.confsched2017.view.fragment.MySessionsFragment;

public class MySessionsActivity extends BaseActivity {

    private ActivityMySessionsBinding binding;

    public static Intent createIntent(Context context) {
        return new Intent(context, MySessionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_sessions);
        getComponent().inject(this);

        initBackToolbar(binding.toolbar);
        replaceFragment(MySessionsFragment.newInstance(), R.id.content_view);
    }
}
