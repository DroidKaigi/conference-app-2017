package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.Nullable;

import io.github.droidkaigi.confsched2017.BuildConfig;
import timber.log.Timber;

@Table
public class Speaker {

    private static final String TAG = Speaker.class.getSimpleName();

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public int id;

    @Column(indexed = true)
    @SerializedName("name")
    public String name;

    @Column
    @Nullable
    @SerializedName("image_url")
    public String imageUrl;

    @Column
    @Nullable
    @SerializedName("twitter_name")
    public String twitterName;

    @Column
    @Nullable
    @SerializedName("github_name")
    public String githubName;

    @Nullable
    public String getAdjustedImageUrl() {
        if (imageUrl == null) {
            return null;
        }

        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
            return imageUrl;
        } else if (imageUrl.startsWith("/")) {
            return BuildConfig.STATIC_ROOT + imageUrl;
        } else {
            Timber.tag(TAG).e("Invalid image url: ", imageUrl);
            return null;
        }
    }
}
