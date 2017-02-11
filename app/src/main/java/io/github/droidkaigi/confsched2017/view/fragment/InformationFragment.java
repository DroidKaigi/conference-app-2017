package io.github.droidkaigi.confsched2017.view.fragment;

import com.annimon.stream.Optional;

import android.content.Context;
import android.content.Intent;
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
import io.github.droidkaigi.confsched2017.view.helper.IntentHelper;
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
    public void showTranslationsPage() {
        showHtmlUrl("https://droidkaigi2017.oneskyapp.com/collaboration");
    }

    @Override
    public void showLicensePage() {
        startActivity(LicensesActivity.createIntent(getActivity()));
    }

    @Override
    public void showDevInfoPage() {
        // TODO
    }

    @Override
    public void showTwitter() {
        showHtmlUrl("https://twitter.com/DroidKaigi");
    }

    @Override
    public void showFacebook() {
        showHtmlUrl("https://www.facebook.com/DroidKaigi/");
    }

    @Override
    public void showGitHubRepository() {
        showHtmlUrl("https://github.com/DroidKaigi/conference-app-2017/");
    }

    @Override
    public void showDroidKaigiWeb() {
        showHtmlUrl("https://droidkaigi.github.io/2017/");
    }

    @Override
    public void showYouTube() {
        showHtmlUrl("https://www.youtube.com/droidkaigi");
    }

    public void showHtmlUrl(String htmlUrl) {
        Optional<Intent> intentOptional = IntentHelper.buildActionViewIntent(getContext(), htmlUrl);
        intentOptional.ifPresent(this::startActivity);
    }
}
