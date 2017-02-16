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
import io.github.droidkaigi.confsched2017.databinding.FragmentLicensesBinding;
import io.github.droidkaigi.confsched2017.viewmodel.LicensesViewModel;

public class LicensesFragment extends BaseFragment {

    public static final String TAG = LicensesFragment.class.getSimpleName();

    @Inject
    LicensesViewModel viewModel;

    private FragmentLicensesBinding binding;

    public static LicensesFragment newInstance() {
        return new LicensesFragment();
    }

    public LicensesFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_licenses, container, false);
        binding = DataBindingUtil.bind(view);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.webView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.webView.destroy();
        viewModel.destroy();
    }
}
