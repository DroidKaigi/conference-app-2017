package io.github.droidkaigi.confsched2017.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contributors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_repository:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DroidKaigi/conference-app-2017"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
