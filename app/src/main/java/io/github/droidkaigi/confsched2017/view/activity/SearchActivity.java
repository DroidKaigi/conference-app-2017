package io.github.droidkaigi.confsched2017.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivitySearchBinding;
import io.github.droidkaigi.confsched2017.view.fragment.SearchFragment;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, R.anim.activity_fade_exit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        initBackToolbar(binding.toolbar);
        replaceFragment(SearchFragment.newInstance(), R.id.content_view);
    }

    @Override
    public void finish() {
        overridePendingTransition(0, R.anim.activity_fade_exit);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
