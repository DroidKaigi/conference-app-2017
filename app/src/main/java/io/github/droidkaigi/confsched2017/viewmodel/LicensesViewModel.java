package io.github.droidkaigi.confsched2017.viewmodel;

import android.annotation.TargetApi;
import android.databinding.BaseObservable;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.view.helper.Navigator;

public final class LicensesViewModel extends BaseObservable implements ViewModel {

    private final Navigator navigator;

    private final String licenseFilePath;

    private final WebViewClient webViewClient;

    @Inject
    LicensesViewModel(Navigator navigator) {
        this.navigator = navigator;
        licenseFilePath = "file:///android_asset/licenses.html";
        webViewClient = new LicensesWebViewClient();
    }

    @Override
    public void destroy() {
        // Nothing to do
    }

    public String getLicenseFilePath() {
        return licenseFilePath;
    }

    public WebViewClient getWebViewClient() {
        return webViewClient;
    }

    private boolean shouldOverrideUrlLoading(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.equals(licenseFilePath)) {
            return false;
        } else {
            navigator.navigateToWebPage(url);
            return true;
        }
    }

    private class LicensesWebViewClient extends WebViewClient {
        @Override
        @SuppressWarnings("deprecation")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return LicensesViewModel.this.shouldOverrideUrlLoading(url);
        }

        @Override
        @TargetApi(Build.VERSION_CODES.N)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl().toString());
        }
    }
}
