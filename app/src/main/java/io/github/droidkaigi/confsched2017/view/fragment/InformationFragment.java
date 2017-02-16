package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.databinding.FragmentInformationBinding;
import io.github.droidkaigi.confsched2017.view.activity.ContributorsActivity;
import io.github.droidkaigi.confsched2017.view.activity.LicensesActivity;
import io.github.droidkaigi.confsched2017.view.activity.SponsorsActivity;
import io.github.droidkaigi.confsched2017.viewmodel.InformationViewModel;

public class InformationFragment extends BaseFragment implements InformationViewModel.Callback {

    public static final String TAG = InformationFragment.class.getSimpleName();

    private FragmentInformationBinding binding;

    @Inject
    InformationViewModel viewModel;

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    public InformationFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformationBinding.inflate(inflater, container, false);

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
        startActivity(SponsorsActivity.createIntent(getActivity()));
    }

    @Override
    public void showQuestionnairePage() {
        // TODO
    }

    @Override
    public void showContributorsPage() {
        startActivity(ContributorsActivity.createIntent(getActivity()));
    }

    @Override
    public void showLicensePage() {
        startActivity(LicensesActivity.createIntent(getActivity()));
    }

    @Override
    public void showDevInfoPage() {
        // TODO
    }
}
