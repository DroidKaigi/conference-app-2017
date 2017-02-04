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
import io.github.droidkaigi.confsched2017.databinding.FragmentSponsorsBinding;
import io.github.droidkaigi.confsched2017.viewmodel.SponsorsViewModel;

public class SponsorsFragment extends BaseFragment {

    public static final String TAG = SponsorsFragment.class.getSimpleName();

    @Inject
    SponsorsViewModel viewModel;

    private FragmentSponsorsBinding binding;

    public static SponsorsFragment newInstance() {
        return new SponsorsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sponsors, container, false);
        binding = DataBindingUtil.bind(view);
        binding.setViewModel(viewModel);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        //
    }
}
