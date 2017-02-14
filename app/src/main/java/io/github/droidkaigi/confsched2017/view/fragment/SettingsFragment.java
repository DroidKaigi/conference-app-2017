package io.github.droidkaigi.confsched2017.view.fragment;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSettingsBinding;
import io.github.droidkaigi.confsched2017.service.DebugOverlayService;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;
import io.github.droidkaigi.confsched2017.util.SettingsUtil;
import io.github.droidkaigi.confsched2017.view.activity.MainActivity;
import io.github.droidkaigi.confsched2017.viewmodel.SettingsViewModel;
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
        List<String> languages = Stream.of(locales)
                .map(locale -> LocaleUtil.getDisplayLanguage(getContext(), locale))
                .collect(Collectors.toList());

        List<String> languageIds = Stream.of(locales)
                .map(LocaleUtil::getLocaleLanguageId)
                .collect(Collectors.toList());

        String currentLanguageId = LocaleUtil.getCurrentLanguageId(getActivity());
        Timber.tag(TAG).d("current language_id: %s", currentLanguageId);
        Timber.tag(TAG).d("languageIds: %s", languageIds.toString());

        int defaultItem = languageIds.indexOf(currentLanguageId);
        Timber.tag(TAG).d("current language_id index: %s", defaultItem);

        String[] items = languages.toArray(new String[languages.size()]);
        new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setTitle(R.string.settings_language)
                .setSingleChoiceItems(items, defaultItem, (dialog, which) -> {
                    String selectedLanguageId = languageIds.get(which);
                    if (!currentLanguageId.equals(selectedLanguageId)) {
                        Timber.tag(TAG).d("Selected language_id: %s", selectedLanguageId);
                        LocaleUtil.setLocale(getActivity(), selectedLanguageId);
                        dialog.dismiss();
                        restart();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public void debugOverlayViewEnabled(boolean enabled) {
        if (isDetached())
            return;
        if (enabled && !SettingsUtil.canDrawOverlays(getContext())) {
            Timber.tag(TAG).d("not allowed to draw views on overlay");
            binding.debugOverlayViewSwitchRow.setChecked(false);
            Toast.makeText(getContext(), R.string.settings_debug_overlay_view_toast, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
            return;
        }
        if (enabled) {
            getContext().startService(new Intent(getContext(), DebugOverlayService.class));
        } else {
            getContext().stopService(new Intent(getContext(), DebugOverlayService.class));
        }
    }

    private void restart() {
        getActivity().finish();
        startActivity(MainActivity.createIntent(getActivity()));
        getActivity().overridePendingTransition(0, 0);
    }

}
