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
import io.github.droidkaigi.confsched2017.databinding.FragmentInformationBinding;
import io.github.droidkaigi.confsched2017.viewmodel.InformationViewModel;

public class InformationFragment extends BaseFragment implements InformationViewModel.Callback {

    public static final String TAG = InformationFragment.class.getSimpleName();

    private FragmentInformationBinding binding;

    @Inject
    InformationViewModel viewModel;

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        binding = DataBindingUtil.bind(view);

        viewModel.setCallback(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    @Override
    public void showSponsorsPage() {
        // TODO
    }

    @Override
    public void showQuesionnairePage() {
        // TODO
    }

    @Override
    public void showContributorsPage() {
        // TODO
    }

    @Override
    public void showLicencePage() {
        // TODO
    }

    @Override
    public void showDevInfoPage() {
        // TODO
    }

    @Override
    public void showTwitter() {
        // TODO
    }

    @Override
    public void showFacebook() {
        // TODO
    }

    @Override
    public void showGitHubRepository() {
        // TODO
    }

    @Override
    public void showDroidKaigiWeb() {
        // TODO
    }

    @Override
    public void showYouTube() {
        // TODO
    }

}
