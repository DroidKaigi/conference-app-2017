package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.Nullable;

@Table
public class Speaker {

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

}
