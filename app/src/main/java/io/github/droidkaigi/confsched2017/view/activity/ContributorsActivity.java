package io.github.droidkaigi.confsched2017.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityContributorsBinding;
import io.github.droidkaigi.confsched2017.view.fragment.ContributorsFragment;

public class ContributorsActivity extends BaseActivity {

    private ActivityContributorsBinding binding;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, ContributorsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contributors);
        getComponent().inject(this);

        initBackToolbar(binding.toolbar);
        replaceFragment(ContributorsFragment.newInstance(), R.id.content_view);
    }
}
