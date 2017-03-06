package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver;

public final class MapViewModel extends BaseObservable implements ViewModel {

    private final Navigator navigator;

    private final ResourceResolver resourceResolver;

    @Inject
    MapViewModel(Navigator navigator, ResourceResolver resourceResolver) {
        this.navigator = navigator;
        this.resourceResolver = resourceResolver;
    }

    public void onClickRouteMenu() {
        navigator.navigateToWebPage(resourceResolver.getString(R.string.map_route_guide_url));
    }

    @Override
    public void destroy() {
        //
    }

}
