package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        SponsorsActivity.start(getActivity());
    }

    @Override
    public void showQuesionnairePage() {
        // TODO
    }

    @Override
    public void showContributorsPage() {
        ContributorsActivity.start(getActivity());
    }

    @Override
    public void showTranslationsPage() {
        Uri uri = Uri.parse("https://droidkaigi2017.oneskyapp.com/collaboration");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getContext().getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    public void showLicensePage() {
        LicensesActivity.start(getActivity());
    }

    @Override
    public void showDevInfoPage() {
        // TODO
    }

    @Override
    public void showTwitter() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/DroidKaigi"));
        if (intent.resolveActivity(getContext().getPackageManager()) == null)
            return;
        startActivity(intent);
    }

    @Override
    public void showFacebook() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/DroidKaigi/"));
        if (intent.resolveActivity(getContext().getPackageManager()) == null)
            return;
        startActivity(intent);
    }

    @Override
    public void showGitHubRepository() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DroidKaigi/conference-app-2017/"));
        if (intent.resolveActivity(getContext().getPackageManager()) == null)
            return;
        startActivity(intent);
    }

    @Override
    public void showDroidKaigiWeb() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://droidkaigi.github.io/2017/"));
        if (intent.resolveActivity(getContext().getPackageManager()) == null)
            return;
        startActivity(intent);
    }

    @Override
    public void showYouTube() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/droidkaigi"));
        if (intent.resolveActivity(getContext().getPackageManager()) == null)
            return;
        startActivity(intent);
    }

}
