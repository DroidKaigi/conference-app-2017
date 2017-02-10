package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class Sponsor {

    @Column
    @SerializedName("image_url")
    public String imageUrl;

    @Column
    @SerializedName("site_url")
    public String url;
}
