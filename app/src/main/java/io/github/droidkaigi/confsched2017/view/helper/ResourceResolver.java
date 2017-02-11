package io.github.droidkaigi.confsched2017.view.helper;

import android.support.annotation.StringRes;

public interface ResourceResolver {

    String getString(@StringRes int resId);

    String getString(@StringRes int resId, Object... formatArgs);
}
