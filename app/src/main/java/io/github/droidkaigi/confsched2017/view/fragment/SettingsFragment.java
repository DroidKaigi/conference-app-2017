package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSettingsBinding;
import io.github.droidkaigi.confsched2017.viewmodel.SettingsViewModel;

public class SettingsFragment extends BaseFragment implements SettingsViewModel.Callback {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    @Inject
    SettingsViewModel viewModel;

    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        binding = DataBindingUtil.bind(view);

        viewModel.setCallback(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void changeHeadsUpEnabled(boolean enabled) {
        binding.headsUpSwitchRow.setEnabled(enabled);
    }
}
