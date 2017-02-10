package io.github.droidkaigi.confsched2017.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSettingsBinding;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;
import io.github.droidkaigi.confsched2017.view.activity.MainActivity;
import io.github.droidkaigi.confsched2017.viewmodel.SettingsViewModel;
import io.reactivex.Observable;
import timber.log.Timber;

public class SettingsFragment extends BaseFragment implements SettingsViewModel.Callback {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    @Inject
    SettingsViewModel viewModel;

    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        viewModel.setCallback(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void changeHeadsUpEnabled(boolean enabled) {
        binding.headsUpSwitchRow.setEnabled(enabled);
    }

    @Override
    public void showLanguagesDialog() {
        List<Locale> locales = LocaleUtil.SUPPORT_LANG;
        List<String> languages = Observable.fromIterable(locales)
                .map(locale -> getString(LocaleUtil.getLanguage(locale)))
                .toList()
                .blockingGet();

        List<String> languageIds = Observable.fromIterable(locales)
                .map(LocaleUtil::getLocaleLanguageId)
                .toList()
                .blockingGet();

        String currentLanguageId = LocaleUtil.getCurrentLanguageId(getActivity());
        Timber.tag(TAG).d("current language_id: " + currentLanguageId);
        Timber.tag(TAG).d("languageIds: " + languageIds.toString());

        int defaultItem = languageIds.indexOf(currentLanguageId);
        Timber.tag(TAG).d("current language_id index: " + defaultItem);

        String[] items = languages.toArray(new String[languages.size()]);
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.settings_language)
                .setSingleChoiceItems(items, defaultItem, (dialog, which) -> {
                    String selectedLanguageId = languageIds.get(which);
                    if (!currentLanguageId.equals(selectedLanguageId)) {
                        Timber.tag(TAG).d("Selected language_id: " + selectedLanguageId);
                        LocaleUtil.setLocale(getActivity(), selectedLanguageId);
                        dialog.dismiss();
                        restart();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void restart() {
        getActivity().finish();
        MainActivity.start(getActivity());
        getActivity().overridePendingTransition(0, 0);
    }

}
