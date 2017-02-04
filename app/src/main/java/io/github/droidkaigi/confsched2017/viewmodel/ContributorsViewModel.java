package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Contributor;

public final class ContributorsViewModel extends BaseObservable implements ViewModel {

    private Callback callback;

    @Inject
    ContributorsViewModel() {
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void onClickContributor(Contributor contributor);
    }
}
