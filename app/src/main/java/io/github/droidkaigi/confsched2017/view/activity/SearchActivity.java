package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivitySearchBinding;
import io.github.droidkaigi.confsched2017.view.fragment.SearchFragment;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;

    public static Intent createIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, R.anim.activity_fade_exit);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        initBackToolbar(binding.toolbar);
        replaceFragment(SearchFragment.newInstance(), R.id.content_view);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_fade_exit);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
