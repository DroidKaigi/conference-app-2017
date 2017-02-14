package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityLicensesBinding;
import io.github.droidkaigi.confsched2017.view.fragment.LicensesFragment;

public class LicensesActivity extends BaseActivity {

    private ActivityLicensesBinding binding;

    public static Intent createIntent(Context context) {
        return new Intent(context, LicensesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_licenses);
        getComponent().inject(this);

        initBackToolbar(binding.toolbar);
        replaceFragment(LicensesFragment.newInstance(), R.id.content_view);
    }
}
