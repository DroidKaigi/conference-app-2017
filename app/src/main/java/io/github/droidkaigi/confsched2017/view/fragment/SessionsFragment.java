package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSessionsBinding;

public class SessionsFragment extends BaseFragment {

    public static final String TAG = SessionsFragment.class.getSimpleName();

    private FragmentSessionsBinding binding;

    public static SessionsFragment newInstance() {
        return new SessionsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sessions, container, false);
        binding = DataBindingUtil.bind(view);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        //
    }
}
