package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivitySponsorsBinding;
import io.github.droidkaigi.confsched2017.view.fragment.SponsorsFragment;

public class SponsorsActivity extends BaseActivity {

    private ActivitySponsorsBinding binding;

    public static Intent createIntent(Context context) {
        return new Intent(context, SponsorsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sponsors);
        getComponent().inject(this);

        initBackToolbar(binding.toolbar);
        replaceFragment(SponsorsFragment.newInstance(), R.id.content_view);
    }
}
