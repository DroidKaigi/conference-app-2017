package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityContributorsBinding;
import io.github.droidkaigi.confsched2017.view.fragment.ContributorsFragment;
import io.github.droidkaigi.confsched2017.viewmodel.ToolbarViewModel;

public class ContributorsActivity extends BaseActivity {

    private ActivityContributorsBinding binding;

    @Inject
    ToolbarViewModel viewModel;

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
}
