package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSettingsBinding;

public class SettingsFragment extends BaseFragment {

    public static final String TAG = SettingsFragment.class.getSimpleName();

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

        initView();
        return binding.getRoot();
    }

    private void initView() {
        //
    }
}
