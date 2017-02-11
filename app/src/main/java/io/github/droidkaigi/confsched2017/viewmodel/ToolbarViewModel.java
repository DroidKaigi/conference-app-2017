package io.github.droidkaigi.confsched2017.viewmodel;

import com.android.databinding.library.baseAdapters.BR;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.di.scope.ActivityScope;

@ActivityScope
public class ToolbarViewModel extends BaseObservable implements ViewModel {

    private String toolbarTitle;

    @Inject
    public ToolbarViewModel() {
    }

    @Bindable
    public String getToolbarTitle() {
        return toolbarTitle;
    }

    public void setToolbarTitle(String title) {
        toolbarTitle = title;
        notifyPropertyChanged(BR.toolbarTitle);
    }

    @Override
    public void destroy() {
        // Nothing to do
    }
}
