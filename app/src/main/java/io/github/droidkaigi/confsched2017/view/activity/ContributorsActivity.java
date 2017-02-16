package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityContributorsBinding;
import io.github.droidkaigi.confsched2017.view.fragment.ContributorsFragment;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;
import io.github.droidkaigi.confsched2017.viewmodel.ToolbarViewModel;

public class ContributorsActivity extends BaseActivity {

    private ActivityContributorsBinding binding;

    @Inject
    ToolbarViewModel viewModel;

    @Inject
    Navigator navigator;

    public static Intent createIntent(Context context) {
        return new Intent(context, ContributorsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contributors);
        binding.setViewModel(viewModel);

        initBackToolbar(binding.toolbar);
        viewModel.setToolbarTitle(getString(R.string.contributors));
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
                navigator.navigateToWebPage("https://github.com/DroidKaigi/conference-app-2017");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
