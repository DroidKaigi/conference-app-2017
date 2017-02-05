package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;

import javax.inject.Inject;

public final class SearchViewModel extends BaseObservable implements ViewModel {

    @Inject
    SearchViewModel() {
    }

    @Override
    public void destroy() {
        //
    }

}
