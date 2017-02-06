package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.droidkaigi.confsched2017.databinding.FragmentMapBinding;

public class MapFragment extends BaseFragment {

    public static final String TAG = MapFragment.class.getSimpleName();

    private FragmentMapBinding binding;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        //
    }
}
