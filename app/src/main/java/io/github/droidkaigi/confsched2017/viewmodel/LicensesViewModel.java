package io.github.droidkaigi.confsched2017.viewmodel;

import android.annotation.TargetApi;
import android.databinding.BaseObservable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

public final class LicensesViewModel extends BaseObservable implements ViewModel {

    private final String licenseFilePath;

    private final WebViewClient webViewClient;

    private Callback callback;

    @Inject
    LicensesViewModel() {
        licenseFilePath = "file:///android_asset/licenses.html";
        webViewClient = new LicensesWebViewClient();
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Override
    public void destroy() {
        this.callback = null;
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
            callback.showExternalLink(url);
            return true;
        }
    }

    public interface Callback {

        void showExternalLink(String url);
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
