package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import com.android.annotations.Nullable;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class Topic {

    public static final int ID_PRODUCTIVITY_AND_TOOLING = 1;

    public static final int ID_ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY = 2;

    public static final int ID_HARDWARE = 3;

    public static final int ID_UI_AND_DESIGN = 4;

    public static final int ID_QUALITY_AND_SUSTAINABILITY = 5;

    public static final int ID_PLATFORM = 6;

    public static final int ID_OTHER = 7;

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public int id;

    @Column(indexed = true)
    @SerializedName("name")
    public String name;

    @Column
    @Nullable
    @SerializedName("other")
    public String other;
}
