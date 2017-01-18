package io.github.droidkaigi.confsched2017.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityMainBinding;
import io.github.droidkaigi.confsched2017.view.helper.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getComponent().inject(this);

        initView();
    }

    private void initView() {
        BottomNavigationViewHelper.disableShiftingMode(binding.bottomNav);
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_sessions:
                    // TODO
                    break;
                case R.id.nav_map:
                    // TODO
                    break;
                case R.id.nav_information:
                    // TODO
                    break;
                case R.id.nav_settings:
                    // TODO
                    break;
            }
            return false;
        });
    }
}
