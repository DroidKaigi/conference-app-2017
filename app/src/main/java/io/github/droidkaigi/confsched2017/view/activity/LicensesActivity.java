package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityLicensesBinding;

public class LicensesActivity extends BaseActivity {
    private static final String LICENSES_HTML_PATH = "file:///android_asset/licenses.html";

    public static Intent createIntent(Context context) {
        return new Intent(context, LicensesActivity.class);
    }

    private ActivityLicensesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_licenses);
        initBackToolbar(binding.toolbar);
        binding.webView.loadUrl(LICENSES_HTML_PATH);
    }
}
